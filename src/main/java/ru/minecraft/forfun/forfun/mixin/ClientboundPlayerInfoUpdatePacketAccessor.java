package ru.minecraft.forfun.forfun.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket;
import java.util.List;

@Mixin(ClientboundPlayerInfoUpdatePacket.class)
public interface ClientboundPlayerInfoUpdatePacketAccessor {
    @Accessor("entries")
    void setEntries(List<ClientboundPlayerInfoUpdatePacket.Entry> newEntries);
}