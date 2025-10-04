package ru.minecraft.forfun.forfun.packets

import net.minecraft.network.FriendlyByteBuf
import net.minecraftforge.network.NetworkEvent
import java.util.function.Supplier

class RotatePlayerPacket(private var deltaYaw: Float) {

    // Конструктор для десериализации
    constructor(buf: FriendlyByteBuf) : this(buf.readFloat())

    // Сериализация пакета
    fun encode(buf: FriendlyByteBuf) {
        buf.writeFloat(deltaYaw)
    }

    // Обработка пакета
    fun handle(context: Supplier<NetworkEvent.Context>) {
        context.get().enqueueWork {
            val player = context.get().sender
            player?.let {
                it.yRot += deltaYaw
                it.yRot %= 360f
                it.yRotO = it.yRot
            }
        }
        context.get().packetHandled = true
    }
}
//<SimpleParticleType>(ParticleRegisrty.BOLT.get(), event.target.x, event.target.y+0.5, event.target.z, 50, 0.1, 0.0, 0.1, 0.2)

class MovePlayer(private var x: Double, private var y: Double, private var z: Double) {

    // Конструктор для десериализации
    constructor(buf: FriendlyByteBuf) : this(
        buf.readDouble(),
        buf.readDouble(),
        buf.readDouble())


    // Сериализация пакета
    fun encode(buf: FriendlyByteBuf) {
        buf.writeDouble(x)
        buf.writeDouble(y)
        buf.writeDouble(z)
    }

    // Обработка пакета
    fun handle(context: Supplier<NetworkEvent.Context>) {
        context.get().enqueueWork {
            val player = context.get().sender
            player?.let {
                it.teleportTo(x,y,z)
            }
        }
        context.get().packetHandled = true
    }
}



//class SwitchCamera(val entityId: Int) {
//    companion object {
//        fun encode(packet: SwitchCamera, buffer: FriendlyByteBuf) {
//            buffer.writeVarInt(packet.entityId)
//        }
//
//        fun decode(buffer: FriendlyByteBuf): SwitchCamera {
//            return SwitchCamera(buffer.readVarInt())
//        }
//
//        fun handle(packet: SwitchCamera, context: Supplier<NetworkEvent.Context>) {
//            context.get().enqueueWork {
//                val mc = Minecraft.getInstance()
//                val entity = mc.level?.getEntity(packet.entityId)
//                val isCamera = entity is DroneEntity
//
//                if (isCamera || entity is Player) {
//                    mc.setCameraEntity(entity)
//
//
//                    // Дополнительные настройки для режима камеры дрона
//                    if (isCamera) {
//                        mc.player?.apply {
//                            xxa = 0.0f
//                            zza = 0.0f
//                            setJumping(false)
//                        }
//
//                        // Можно добавить визуальные эффекты
//                    }
//
//                    mc.levelRenderer.allChanged()
//                }
//            }
//            context.get().packetHandled = true
//        }
//    }
//}
//class DroneCameraRequestPacket(val frequency: Int) {
//    companion object {
//        fun encode(packet: DroneCameraRequestPacket, buffer: FriendlyByteBuf) {
//            buffer.writeInt(packet.frequency)
//        }
//
//        fun decode(buffer: FriendlyByteBuf): DroneCameraRequestPacket {
//            return DroneCameraRequestPacket(buffer.readInt())
//        }
//
//        fun handle(packet: DroneCameraRequestPacket, context: Supplier<NetworkEvent.Context>) {
//            context.get().enqueueWork {
//                val player = context.get().sender ?: return@enqueueWork
//
//                // Проверяем, не пытается ли игрок выйти из режима камеры
//                if (packet.frequency == 0) {
//                    if (player.camera is DroneEntity) {
//                        (player.camera as DroneEntity).stopViewing(player)
//                    }
//                    return@enqueueWork
//                }
//
//                // Поиск дрона с указанной частотой
//                val drone = findDroneByFrequency(player, packet.frequency)
//
//                drone?.let {
//                    if (player.camera == it) {
//                        // Если уже смотрим от этого дрона, выходим из режима
//                        it.stopViewing(player)
//                    } else {
//                        // Переключаемся на камеру дрона
//                        it.startViewing(player)
//                    }
//                } ?: run {
//                    player.sendSystemMessage(Component.literal("Дрон с частотой ${packet.frequency} не найден"))
//                }
//            }
//            context.get().packetHandled = true
//        }
//
//        private fun findDroneByFrequency(player: ServerPlayer, frequency: Int): DroneEntity? {
//            val level = player.level()
//            val playerUUID = player.uuid.toString()
//            // Используем ограниченный AABB для поиска вокруг игрока
//            val searchArea = AABB(
//                player.x - 100.0, player.y - 100.0, player.z - 100.0,
//                player.x + 100.0, player.y + 100.0, player.z + 100.0
//            )
//            return level.getEntitiesOfClass(DroneEntity::class.java, searchArea)
//                .firstOrNull { it.getOwnerUUID() == playerUUID && it.getFrequency() == frequency }
//        }
//    }
//}