package ru.minecraft.forfun.forfun.event.events

import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerLevel
import net.minecraft.sounds.SoundSource
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraftforge.event.entity.player.CriticalHitEvent
import net.minecraftforge.eventbus.api.Event
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import ru.minecraft.forfun.forfun.Forfun
import ru.minecraft.forfun.forfun.event.custom.BoltHitEvent
import ru.minecraft.forfun.forfun.registry.ItemsRegistry
import ru.minecraft.forfun.forfun.registry.ParticleRegisrty
import ru.minecraft.forfun.forfun.registry.SoundRegistry
import ru.minecraft.forfun.forfun.util.ArmorHelper

@Mod.EventBusSubscriber(modid = Forfun.ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
object CivilDefenceEvent{

    @SubscribeEvent
    fun onAttack(event: BoltHitEvent) {
        val player = event.entity
        val target = event.target
        val level: Level = player.level()

        // Basic null safety check
        if (player == null) return

        val boots: ItemStack = player.inventory.getArmor(0)
        val leggings: ItemStack = player.inventory.getArmor(1)
        val chestplate: ItemStack = player.inventory.getArmor(2)
        val helmet: ItemStack = player.inventory.getArmor(3)

        // Check for full armor set first
        val hasFullSet = boots.`is`(ItemsRegistry.CIVIL_DEFENCE_BOOTS.get()) &&
                leggings.`is`(ItemsRegistry.CIVIL_DEFENCE_LEGGINGS.get()) &&
                chestplate.`is`(ItemsRegistry.CIVIL_DEFENCE_CHESTPLATE.get()) &&
                helmet.`is`(ItemsRegistry.CIVIL_DEFENCE_HELMET.get())

        if (!hasFullSet) {
            return // Exit if not wearing the full set
        }

        ArmorHelper.checkFirstArmorInt(helmet, "Bolt")
        val currentBolt = ArmorHelper.getArmorDataInt(helmet, "Bolt")

        // Condition 1: Full set and 3 bolt charges - apply bonus damage
        if (currentBolt == 3) {
            event.damageModifier = 3.0F
            event.result = Event.Result.ALLOW
            // Spawn particles on the server
            if (level is ServerLevel) {
                level.sendParticles(
                    ParticleRegisrty.BOLT.get(),
                    target.x, target.y + 0.1, target.z,
                    10, // count
                    1.0, 1.0, 1.0, // xd, yd, zd (offsets)
                    0.1 // speed
                )
                level.playSound(null, target.blockPosition(), SoundRegistry.BOLT.get(), SoundSource.PLAYERS, 1.0f, 1.0f)
            }
            ArmorHelper.reset(helmet, "Bolt")
        }
        // Condition 2: Full set but not enough charges - deny the crit but allow normal hit
        else {
            event.result = Event.Result.DENY
        }
    }
    @SubscribeEvent
    fun onCrit(event: CriticalHitEvent) {
        val player = event.entity
        if (player != null) {
            val boots: ItemStack = player.inventory.getArmor(0)
            val leggings: ItemStack = player.inventory.getArmor(1)
            val chestplate: ItemStack = player.inventory.getArmor(2)
            val helmet: ItemStack = player.inventory.getArmor(3)
            val bolt = ArmorHelper.getArmorDataInt(helmet, "Bolt")
            if (
                boots.`is`
                    (ItemsRegistry.CIVIL_DEFENCE_BOOTS.get()) &&
                leggings.`is`
                    (ItemsRegistry.CIVIL_DEFENCE_LEGGINGS.get()) &&
                chestplate.`is`
                    (ItemsRegistry.CIVIL_DEFENCE_CHESTPLATE.get()) &&
                helmet.`is`
                    (ItemsRegistry.CIVIL_DEFENCE_HELMET.get()) &&
                event.damageModifier == 1.5F
            ) {
                ArmorHelper.changeIntToArmor(helmet, ItemsRegistry.CIVIL_DEFENCE_HELMET.get(), "Bolt", 1)
                event.result = Event.Result.DENY
            }
        }
    }
}

