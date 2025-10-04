package ru.minecraft.forfun.forfun.packets

import net.minecraft.resources.ResourceLocation
import net.minecraftforge.network.NetworkRegistry
import net.minecraftforge.network.simple.SimpleChannel
import ru.minecraft.forfun.forfun.Forfun

object PacketHandler {
    private const val PROTOCOL_VERSION = "1"
    val CHANNEL: SimpleChannel = NetworkRegistry.newSimpleChannel(
        ResourceLocation(Forfun.ID, "main"),
        { PROTOCOL_VERSION },
        PROTOCOL_VERSION::equals,
        PROTOCOL_VERSION::equals
    )

    private var packetId = 0

    fun registerPackets() {
        CHANNEL.registerMessage(
            packetId++,
            RotatePlayerPacket::class.java,
            RotatePlayerPacket::encode,
            ::RotatePlayerPacket,
            RotatePlayerPacket::handle
        )
        CHANNEL.registerMessage(
            packetId++,
            MovePlayer::class.java,
            MovePlayer::encode,
            ::MovePlayer,
            MovePlayer::handle
        )
    }
    }