package ru.minecraft.forfun.forfun

import net.minecraft.commands.Commands
import net.minecraft.commands.arguments.EntityArgument
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.chat.Component
import ru.minecraft.forfun.forfun.renderer.player.EgorLoh

object ModCommands {
    val vanish = Commands.literal("vanish").requires {
        source ->
        source.hasPermission(2)
    }.then(
        Commands.argument("player", EntityArgument.player()).executes { context ->
            val serverPlayer = EntityArgument.getPlayer(context, "player")
            val persistentData = serverPlayer.persistentData

            // Работа с PersistentData (предпочтительный способ в новых версиях)
            if (persistentData.contains("is_vanished")) {
                EgorLoh.vanishPlayer(serverPlayer)
                context.source.sendSuccess(
                    {Component.literal("Игроку ${serverPlayer.scoreboardName} выдан тег is_vanished")},
                    true
                )
            } else {
                context.source.sendFailure(Component.literal("У игрока уже есть этот тег"))
            }
            1 // Возвращаем результат выполнения
        }
    )
}