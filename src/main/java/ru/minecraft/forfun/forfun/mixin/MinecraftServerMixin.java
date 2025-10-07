package ru.minecraft.forfun.forfun.mixin;

import com.mojang.authlib.GameProfile;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.Util;
import net.minecraft.network.protocol.status.ServerStatus;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.minecraft.forfun.forfun.renderer.player.EgorLoh;
import ru.minecraft.forfun.forfun.util.FieldHolder;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Mixin(MinecraftServer.class)
public abstract class MinecraftServerMixin {

    @Shadow
    @Final
    private RandomSource random;

    @Shadow
    private ServerStatus status;

    @Shadow
    public abstract PlayerList getPlayerList();

    @Shadow
    public abstract boolean hidesOnlinePlayers();

    @Inject(method = {"runServer", "tickServer"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/server/MinecraftServer;resetStatusCache(Lnet/minecraft/network/protocol/status/ServerStatus;)V"))
    private void forfun$onBuildServerStatus(CallbackInfo callbackInfo) {
        PlayerList list = getPlayerList();

        // Фильтруем игроков: оставляем только тех, у кого нет тега is_vanished
        List<ServerPlayer> unvanishedPlayers = list.getPlayers().stream()
                .filter(player -> !EgorLoh.INSTANCE.isVanished(player))
                .toList();

        int unvanishedPlayerCount = unvanishedPlayers.size();
        int maxPlayers = list.getMaxPlayers();
        ServerStatus mainServerStatus = status;
        ServerStatus.Players vanishedPlayerStatus;

        if (hidesOnlinePlayers()) {
            vanishedPlayerStatus = new ServerStatus.Players(maxPlayers, unvanishedPlayerCount, List.of());
        } else {
            int playerSampleSize = Math.min(unvanishedPlayers.size(), 12);
            ObjectArrayList<GameProfile> displayedPlayers = new ObjectArrayList<>(playerSampleSize);
            int offset = Mth.nextInt(random, 0, unvanishedPlayerCount - playerSampleSize);

            for (int l = 0; l < playerSampleSize; ++l) {
                ServerPlayer player = unvanishedPlayers.get(offset + l);
                displayedPlayers.add(player.allowsListing() ? player.getGameProfile() : MinecraftServer.ANONYMOUS_PLAYER_PROFILE);
            }

            Util.shuffle(displayedPlayers, random);
            vanishedPlayerStatus = new ServerStatus.Players(maxPlayers, unvanishedPlayerCount, displayedPlayers);
        }

        if (mainServerStatus != null){
        FieldHolder.INSTANCE.setVanishedServerStatus(
                new ServerStatus(
                        mainServerStatus.description(),
                        Optional.of(vanishedPlayerStatus),
                        mainServerStatus.version(),
                        mainServerStatus.favicon(),
                        mainServerStatus.enforcesSecureChat(),
                        mainServerStatus.forgeData()
                )
            );
        }
        else
            FieldHolder.INSTANCE.setVanishedServerStatus(null);
    }
}

