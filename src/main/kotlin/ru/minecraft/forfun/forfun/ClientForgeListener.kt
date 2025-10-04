package ru.minecraft.forfun.forfun

import net.minecraft.client.Minecraft
import net.minecraft.client.player.LocalPlayer
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.event.TickEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod

@Mod.EventBusSubscriber(modid = Forfun.ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = [Dist.CLIENT])
object ClientForgeListener {
    val minecraft: Minecraft = Minecraft.getInstance()
    val player: LocalPlayer? = minecraft.player
    var overlay: Boolean = true
    private val disable_overlay = KeyRegistry.OFF_OVERLAY


    @SubscribeEvent
    fun onClientTick(event: TickEvent.ClientTickEvent) {
        if (event.phase == TickEvent.Phase.END) {
            if (disable_overlay.get().consumeClick()) {
                overlay = !overlay
            }
        }
    }
}




//private val frequencyToKey = mapOf(
//          1 to KeyRegistry.OFF_OVERLAY,
//        2 to KeyRegistry.DRONE_2,
//        3 to KeyRegistry.DRONE_3,
//        4 to KeyRegistry.DRONE_4,
//        5 to KeyRegistry.DRONE_5
//        )
//    @SubscribeEvent
//    fun onClientTick(event: TickEvent.ClientTickEvent) {
//            if (event.phase == TickEvent.Phase.END) {
////            frequencyToKey.forEach { (frequency, key) ->
////                if (key.get().consumeClick()){
////                    PacketHandler.CHANNEL.sendToServer(
////                        DroneCameraRequestPacket(frequency)
////                    )
////                }
////            }
////        }
////    }