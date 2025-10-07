package ru.minecraft.forfun.forfun.mixin;


import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundSystemChatPacket;
import net.minecraft.network.PacketSendListener;
import net.minecraft.network.protocol.game.ClientboundTakeItemEntityPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraftforge.server.ServerLifecycleHooks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.minecraft.forfun.forfun.renderer.player.EgorLoh;

import java.util.ArrayList;
import java.util.List;

@Mixin(ServerGamePacketListenerImpl.class)
public class ServerGamePacketListenerImplMixin {

    @Shadow
    public ServerPlayer player;
    @Inject(method = "send(Lnet/minecraft/network/protocol/Packet;)V", at = @At("HEAD"), cancellable = true)
    private void forfun$onSendPacket(Packet<?> packet, CallbackInfo callbackInfo) {
        ServerPlayer receivingPlayer = this.player;
        Level level = receivingPlayer.level();

        // Обработка анимации поднимания предметов
        if (packet instanceof ClientboundTakeItemEntityPacket pickupPacket) {
            Entity pickingUpEntity = level.getEntity(pickupPacket.getPlayerId());

            if (pickingUpEntity instanceof ServerPlayer pickingUpPlayer) {
                // Если игрок, который поднимает предмет, невидим для получателя - отменяем пакет
                if (EgorLoh.INSTANCE.isVanished(pickingUpPlayer) &&
                        !EgorLoh.INSTANCE.canSeeVanished(receivingPlayer, pickingUpPlayer)) {
                    callbackInfo.cancel();
                }
            }
        }
    }
    @Inject(method = "send(Lnet/minecraft/network/protocol/Packet;Lnet/minecraft/network/PacketSendListener;)V", at = @At("HEAD"), cancellable = true)
    private void forfun$onSendPacket(Packet<?> packet, PacketSendListener listener, CallbackInfo callbackInfo) {
        if (packet instanceof ClientboundSystemChatPacket chatPacket &&
                chatPacket.content() instanceof MutableComponent component &&
                component.getContents() instanceof TranslatableContents content) {

            String key = content.getKey();

            // Скрываем только сообщения об ачивках
            if (key.startsWith("chat.type.advancement.")) {
                // Получаем список невидимых игроков
                List<ServerPlayer> vanishedPlayers = new ArrayList<>();
                MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
                if (server != null) {
                    vanishedPlayers = server.getPlayerList().getPlayers().stream()
                            .filter(p -> EgorLoh.INSTANCE.isVanished(p) && !EgorLoh.INSTANCE.canSeeVanished(player, p))
                            .toList();
                }

                // Проверяем, упоминается ли в сообщении невидимый игрок
                for (Object arg : content.getArgs()) {
                    if (arg instanceof Component componentArg) {
                        String potentialPlayerName = componentArg.getString();

                        for (ServerPlayer vanishedPlayer : vanishedPlayers) {
                            if (vanishedPlayer.getDisplayName().getString().equals(potentialPlayerName)) {
                                callbackInfo.cancel();
                                return;
                            }
                        }
                    }
                }
            }
        }
    }
}