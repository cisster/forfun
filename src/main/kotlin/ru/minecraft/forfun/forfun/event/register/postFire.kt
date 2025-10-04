package ru.minecraft.forfun.forfun.event.register


import net.minecraft.network.chat.Component
import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.event.entity.player.AttackEntityEvent
import net.minecraftforge.event.entity.player.CriticalHitEvent
import net.minecraftforge.eventbus.api.Event
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import ru.minecraft.forfun.forfun.Forfun
import ru.minecraft.forfun.forfun.event.custom.BoltHitEvent


@Mod.EventBusSubscriber(modid = Forfun.ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
object postFire {
    @SubscribeEvent
    fun onAttack(event: AttackEntityEvent){
        val player = event.entity
        val target = event.target
        val level = player.level()
        val attackDamage: Float = player.getAttribute(Attributes.ATTACK_DAMAGE)!!.value.toFloat()
        if (level.isClientSide) {
            return
        }
        val boltHitEvent = BoltHitEvent(player, 1.5F, target) // Default Value

        if (!MinecraftForge.EVENT_BUS.post(boltHitEvent)) {
            if (boltHitEvent.result == Event.Result.ALLOW){
                event.isCanceled = true
                target.hurt(player.damageSources().playerAttack(player), boltHitEvent.damageModifier * attackDamage)
            }
        }
    }
}