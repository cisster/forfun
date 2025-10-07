package ru.minecraft.forfun.forfun.mixin;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import com.mojang.authlib.GameProfile;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.OutgoingChatMessage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.minecraft.forfun.forfun.renderer.player.EgorLoh;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin extends Player {

    @Shadow @Final
    public MinecraftServer server;

    public ServerPlayerMixin(Level world, BlockPos pos, float angle, GameProfile gameProfile) {
        super(world, pos, angle, gameProfile);
    }

    @Inject(method = "sendChatMessage", at = @At("HEAD"), cancellable = true)
    public void forfun$onSendChatMessage(OutgoingChatMessage message, boolean filter, net.minecraft.network.chat.ChatType.Bound chatType, CallbackInfo callback) {
        if (message instanceof OutgoingChatMessage.Player playerChatMessage) {
            Player sender = this.server.getPlayerList().getPlayer(playerChatMessage.message().link().sender());

            if (sender == null) return;

            if (EgorLoh.INSTANCE.isVanished(sender) && !EgorLoh.INSTANCE.canSeeVanished(this, sender)) {
                callback.cancel();
            }
        }
    }
}