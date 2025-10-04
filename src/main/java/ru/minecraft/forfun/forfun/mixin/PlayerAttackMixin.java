//package ru.minecraft.forfun.forfun.mixin;
//
//import net.minecraft.client.Minecraft;
//import net.minecraft.network.chat.Component;
//import net.minecraft.world.entity.Entity;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.item.ItemStack;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Inject;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//import ru.minecraft.forfun.forfun.registry.ItemsRegistry;
//
//@Mixin(Player.class)
//abstract public class PlayerAttackMixin {
//
//    @Inject(method = "attack", at = @At("HEAD"))
//    private void onPlayerAttack(Entity pTarget, CallbackInfo ci) {
//        Player player = Minecraft.getInstance().player;
//        if (!net.minecraftforge.common.ForgeHooks.onPlayerAttackTarget(player, pTarget)) return;
//        if (pTarget.isAttackable()) {
//            if (!pTarget.skipAttackInteraction(player)) {
//                ItemStack boots = player.getInventory().getArmor(0);
//                ItemStack leggings = player.getInventory().getArmor(1);
//                ItemStack chestplate = player.getInventory().getArmor(2);
//                ItemStack helmet = player.getInventory().getArmor(3);
//                if (boots.is(ItemsRegistry.INSTANCE.getCIVIL_DEFENCE_BOOTS().get()) && leggings.is
//                (ItemsRegistry.INSTANCE.getCIVIL_DEFENCE_LEGGINGS().get()) && chestplate.is
//                (ItemsRegistry.INSTANCE.getCIVIL_DEFENCE_CHESTPLATE().get()) && helmet.is
//                (ItemsRegistry.INSTANCE.getCIVIL_DEFENCE_HELMET().get())
//                ){
//                    player.sendSystemMessage(Component.literal("2"));
//                }
//            }
//        }
//    }
//}