package ru.minecraft.forfun.forfun

import net.minecraft.server.level.ServerPlayer
import net.minecraftforge.event.RegisterCommandsEvent
import net.minecraftforge.event.entity.living.LivingChangeTargetEvent
import net.minecraftforge.event.entity.player.AttackEntityEvent
import net.minecraftforge.event.entity.player.PlayerEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import ru.minecraft.forfun.forfun.renderer.player.EgorLoh


@Mod.EventBusSubscriber(modid = Forfun.ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
object ForgeListener {
    @SubscribeEvent
    fun registerCommands(event: RegisterCommandsEvent) {
        event.dispatcher.register(ModCommands.vanish)
    }

    @SubscribeEvent
    fun onJoin(event: PlayerEvent.PlayerLoggedInEvent) {
        EgorLoh.firstJoin(event.entity as ServerPlayer)

    }

    @SubscribeEvent
    fun onPlayerClone(event: PlayerEvent.Clone) {
        val originalPlayer = event.original
        val newPlayer = event.entity

        val persistentData = originalPlayer.persistentData
        if (persistentData.contains("is_vanished")) {
            newPlayer.persistentData.putBoolean("is_vanished", persistentData.getBoolean("is_vanished"))
        }
    }

    @SubscribeEvent
    fun onChangeTarget(event: LivingChangeTargetEvent) {
        val newTarget = event.newTarget
        if (newTarget is ServerPlayer) {
            if (EgorLoh.isVanished(newTarget)) {
                event.isCanceled = true
            }
        }
    }
    @SubscribeEvent
    fun onAttack(event: AttackEntityEvent){
        val entity = event.entity
        if (entity is ServerPlayer) {
            if (EgorLoh.isVanished(entity)) {
                event.isCanceled = true
            }
        }
    }
}
