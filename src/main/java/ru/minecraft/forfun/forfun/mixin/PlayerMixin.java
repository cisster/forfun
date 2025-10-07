package ru.minecraft.forfun.forfun.mixin;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.minecraft.forfun.forfun.renderer.player.EgorLoh;

@Mixin(Player.class)
public class PlayerMixin {

    @Inject(method = "getDisplayName", at = @At("HEAD"), cancellable = true)
    private void onGetDisplayName(CallbackInfoReturnable<Component> cir) {
        Player player = (Player) (Object) this;

        if (EgorLoh.INSTANCE.isVanished(player)) {
            Component originalName = Component.literal(player.getGameProfile().getName());
            Component specialName = originalName.copy().withStyle(ChatFormatting.OBFUSCATED);
            cir.setReturnValue(specialName);
        }
    }
    @Inject(method = "getName", at = @At("HEAD"), cancellable = true)
    private void onGetName(CallbackInfoReturnable<Component> cir) {
        Player player = (Player) (Object) this;

        if (EgorLoh.INSTANCE.isVanished(player)) {
            Component specialName = Component.literal(player.getGameProfile().getName()).withStyle(ChatFormatting.OBFUSCATED);
            cir.setReturnValue(specialName);
        }
    }
}