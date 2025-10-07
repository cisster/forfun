package ru.minecraft.forfun.forfun.util

import net.minecraft.network.protocol.status.ServerStatus
import net.minecraft.server.level.ServerPlayer



object FieldHolder {
    var joiningPlayer: ServerPlayer? = null
    //The player that is currently in the process of leaving the server. Required due to leaving players getting removed from the regular player list too early in some occasions
    var leavingPlayer: ServerPlayer? = null
    //The server status that is used when the config option hidePlayersFromPlayerLists is turned on and that accounts for vanished players
    var vanishedServerStatus: ServerStatus? = null
}