package ru.minecraft.forfun.forfun.mixin;

import net.minecraft.server.level.ChunkMap;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import ru.minecraft.forfun.forfun.renderer.player.EgorLoh;


@Mixin(targets = "net.minecraft.server.level.ChunkMap$TrackedEntity")
public class ChunkMapTrackedEntityMixin {

    @Shadow @Final Entity entity;

    @Inject(method = "updatePlayer", at = @At("HEAD"), cancellable = true)
    private void forfun$onUpdatePlayer(ServerPlayer otherPlayer, CallbackInfo info) {
        if (this.entity instanceof ServerPlayer targetPlayer) {
            if (!EgorLoh.INSTANCE.canSeeVanished(otherPlayer, targetPlayer)) {
                info.cancel();
            }
        }
    }
}