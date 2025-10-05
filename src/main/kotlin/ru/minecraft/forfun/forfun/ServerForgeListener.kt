package ru.minecraft.forfun.forfun

import net.minecraftforge.event.TickEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import ru.minecraft.forfun.forfun.util.autoupdater.AutoUpdater

object ServerForgeListener {
    var Checked = false
    @SubscribeEvent
    fun onServerTick(event: TickEvent.ServerTickEvent) {
        if (!AutoUpdater.checkShowScreen() && !Checked) {
            AutoUpdater.downloadUpdate()
            Checked = true
        }
    }
}