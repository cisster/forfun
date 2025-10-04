package ru.minecraft.forfun.forfun.event.events

import net.minecraft.client.Minecraft
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.client.event.RenderGuiEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import ru.minecraft.forfun.forfun.Forfun
import ru.minecraft.forfun.forfun.gui.gui

@Mod.EventBusSubscriber(modid = Forfun.ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = [Dist.CLIENT])
object ClientEvents {
    @SubscribeEvent
    fun onRenderGui(event: RenderGuiEvent){
        val guiGraphics = event.guiGraphics
        val minecraft = Minecraft.getInstance()
        gui.renderCivilDefence(guiGraphics, minecraft)
    }
}