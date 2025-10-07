package ru.minecraft.forfun.forfun.renderer.player

import it.unimi.dsi.fastutil.ints.Int2ObjectMap
import net.minecraft.nbt.CompoundTag
import net.minecraft.network.protocol.game.ClientboundPlayerInfoRemovePacket
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket
import net.minecraft.network.protocol.game.ClientboundRemoveEntitiesPacket
import net.minecraft.server.level.ServerLevel
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.LivingEntity
import ru.minecraft.forfun.forfun.mixin.ChunkMapAccessor

object EgorLoh{
    fun vanishPlayer(player: ServerPlayer){
        val persistentData = player.persistentData
        val wasVanished = persistentData.getBoolean("is_vanished")
        val newVanishedStatus = !wasVanished

        persistentData.putBoolean("is_vanished", newVanishedStatus)
        sendPacketsOnVanish(player, player.serverLevel(), newVanishedStatus)
    }
    fun isVanished(playerToCheck: Entity): Boolean{
        if (playerToCheck == null){
            return false
        }
        val persistentData: CompoundTag = playerToCheck.persistentData
        return persistentData.getBoolean("is_vanished")
    }
    fun canSeeVanished(receiver: Entity, targetPlayer: Entity): Boolean {
        // 1. If the target player is NOT vanished, they should ALWAYS be visible.
        if (!isVanished(targetPlayer)) {
            return true
        }

        // 2. At this point, the target player IS vanished.
        // Add logic here for players who can see vanished players (e.g., admins).
        // For example: if (receiver is ServerPlayer && receiver.hasPermissions(2)) return true

        // 3. By default, do not let the receiver see the vanished target.
        return false
    }
    fun firstJoin(player: ServerPlayer){
        val persistentData: CompoundTag = player.persistentData

        sendPacketsOnVanish(player, player.serverLevel(), !isVanished(player))
        if (!persistentData.contains("is_vanished")){
            persistentData.putBoolean("is_vanished", false)
        }
    }
    fun sendPacketsOnVanish(changingPlayer: ServerPlayer, world: ServerLevel, vanishes: Boolean) {
        val playerList = world.server.playerList.players
        val chunkProvider = changingPlayer.serverLevel().chunkSource

        for (otherPlayer in playerList) {
            if (otherPlayer == changingPlayer) continue // Пропускаем самого игрока

            val otherPlayerVanished = EgorLoh.isVanished(otherPlayer)
            val otherAllowedToSeeChanging = canSeeVanished(otherPlayer, changingPlayer)
            val changingAllowedToSeeOther = canSeeVanished(changingPlayer, otherPlayer)


            // Обновляем таб-лист для otherPlayer
            otherPlayer.connection.send(
                if (otherAllowedToSeeChanging)
                    ClientboundPlayerInfoUpdatePacket.createPlayerInitializing(listOf(changingPlayer))
                else
                    ClientboundPlayerInfoRemovePacket(listOf(changingPlayer.uuid))
            )

            // Обновляем таб-лист для changingPlayer (если otherPlayer тоже невидим)
            if (otherPlayerVanished) {
                changingPlayer.connection.send(
                    if (changingAllowedToSeeOther)
                        ClientboundPlayerInfoUpdatePacket.createPlayerInitializing(listOf(otherPlayer))
                    else
                        ClientboundPlayerInfoRemovePacket(listOf(otherPlayer.uuid))
                )
            }

            // Управление entity-отображением в мире
            if (true) { // Замените на свою конфигурацию
                when {
                    // Если игрок становится невидимым и otherPlayer не должен его видеть
                    vanishes && !otherAllowedToSeeChanging ->
                        otherPlayer.connection.send(ClientboundRemoveEntitiesPacket(changingPlayer.id))

                    // Если игрок становится видимым и не должен видеть других невидимых
                    !vanishes && !changingAllowedToSeeOther ->
                        changingPlayer.connection.send(ClientboundRemoveEntitiesPacket(otherPlayer.id))
                }
            }
        }
        val chunkMapAccessor = chunkProvider.chunkMap as ChunkMapAccessor
        val entityMap = chunkMapAccessor.entityMap as Int2ObjectMap<*>
        // Принудительно обновляем трекинг сущности
        if (entityMap.containsKey(changingPlayer.id)) {
            entityMap.remove(changingPlayer.id)
            chunkProvider.addEntity(changingPlayer)
        }

        // Обновляем интерфей
        changingPlayer.refreshTabListName()
    }
    fun vanishEntity(entity: LivingEntity){
        entity.isInvisible = true
    }
}