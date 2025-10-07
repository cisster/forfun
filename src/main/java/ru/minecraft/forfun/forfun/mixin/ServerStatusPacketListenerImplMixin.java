package ru.minecraft.forfun.forfun.mixin;

import net.minecraft.network.protocol.status.ClientboundStatusResponsePacket;
import net.minecraft.network.protocol.status.ServerStatus;
import net.minecraft.server.network.ServerStatusPacketListenerImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import ru.minecraft.forfun.forfun.util.FieldHolder;

@Mixin(ServerStatusPacketListenerImpl.class)
public class ServerStatusPacketListenerImplMixin {
    //Updates the player list sent to clients on the Multiplayer screen to only display and count unvanished players
    @Redirect(method = "handleStatusRequest", at = @At(value = "NEW", target = "(Lnet/minecraft/network/protocol/status/ServerStatus;Ljava/lang/String;)Lnet/minecraft/network/protocol/status/ClientboundStatusResponsePacket;"))
    public ClientboundStatusResponsePacket forfun$constructSServerInfoPacket(ServerStatus status, String cachedStatus) {
        if (FieldHolder.INSTANCE.getVanishedServerStatus() != null) {
            status = FieldHolder.INSTANCE.getVanishedServerStatus();
            cachedStatus = null;
        }
        return new ClientboundStatusResponsePacket(status, cachedStatus);
    }
}

