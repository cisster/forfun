package ru.minecraft.forfun.forfun.event.events

import net.minecraft.resources.ResourceLocation
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.event.TickEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import ru.minecraft.forfun.forfun.Forfun
import ru.minecraft.forfun.forfun.gui.gui


@Mod.EventBusSubscriber(modid = Forfun.ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = [Dist.CLIENT])
object onTickEvent{
    var everyTick: Int = 0
    var frame: Int = 0
    @SubscribeEvent
    fun onTick(event: TickEvent.ClientTickEvent) {
        everyTick++
        if (everyTick == 10 && frame != 6) {
            everyTick = 0
            frame++
        } else if (frame == 6) {
            frame = 0
        }
    }
    fun renderLightning(frame: Int): ResourceLocation{
        return when (frame) {
            1 -> gui.LIGHTNING_ANIMATION_1
            2 -> gui.LIGHTNING_ANIMATION_2
            3 -> gui.LIGHTNING_ANIMATION_3
            4 -> gui.LIGHTNING_ANIMATION_4
            5 -> gui.LIGHTNING_ANIMATION_5
            else -> gui.LIGHTNING_ANIMATION_6
        }
    }
}