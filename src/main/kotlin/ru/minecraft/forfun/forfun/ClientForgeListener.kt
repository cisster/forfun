package ru.minecraft.forfun.forfun

import net.minecraft.ChatFormatting
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screens.AlertScreen
import net.minecraft.client.gui.screens.ConfirmScreen
import net.minecraft.client.gui.screens.TitleScreen
import net.minecraft.client.player.LocalPlayer
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.Style
import net.minecraft.network.chat.TextColor
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.client.event.ClientPlayerNetworkEvent
import net.minecraftforge.event.TickEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import ru.minecraft.forfun.forfun.renderer.player.EgorLoh
import ru.minecraft.forfun.forfun.screens.updateScreen
import ru.minecraft.forfun.forfun.util.autoupdater.AutoUpdater


@Mod.EventBusSubscriber(modid = Forfun.ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = [Dist.CLIENT])
object ClientForgeListener {
    val minecraft: Minecraft = Minecraft.getInstance()
    val player: LocalPlayer? = minecraft.player
    var overlay: Boolean = true
    private val disable_overlay = KeyRegistry.OFF_OVERLAY
    private val test_button = KeyRegistry.TEST_BUTTON
    var hasShownScreen = false

    @SubscribeEvent
    fun onClientTick(event: TickEvent.ClientTickEvent) {
        if (event.phase == TickEvent.Phase.END) {
            if (disable_overlay.get().consumeClick()) {
                overlay = !overlay
            }
            if (test_button.get().consumeClick()) {

            }
        }
    }

    @SubscribeEvent
    fun onStart(event: TickEvent.ClientTickEvent) {
        val mc = Minecraft.getInstance()
        if (mc.screen is TitleScreen && !hasShownScreen && AutoUpdater.checkShowScreen()) {
            mc.tell {
                mc.setScreen(
                    updateScreen(
                        { accepted ->
                            if (accepted) {
                                AutoUpdater.getUpdated()
                                mc.setScreen(null)
                            } else {
                                // Действие при отказе, например, ничего не делать или записать в лог
                                mc.setScreen(null)
                            }
                        },
                        Component.literal("Установить обновление мода"),
                        Component.literal("Мод для приколов - ${AutoUpdater.checkUpdate()}").withStyle(Style.EMPTY.withColor(
                            TextColor.fromLegacyFormat(ChatFormatting.GREEN)))
                    )
                )
            }
        }
        hasShownScreen = true

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