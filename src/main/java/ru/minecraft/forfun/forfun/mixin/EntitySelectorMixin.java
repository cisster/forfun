package ru.minecraft.forfun.forfun.mixin;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.selector.EntitySelector;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.At.Shift;
import ru.minecraft.forfun.forfun.renderer.player.EgorLoh;

import java.util.List;
import java.util.stream.Collectors;

@Mixin(EntitySelector.class)
public class EntitySelectorMixin {

    @ModifyVariable(method = "findSingleEntity", at = @At(value = "INVOKE", target = "Ljava/util/List;isEmpty()Z", shift = Shift.BEFORE))
    private List<? extends Entity> forfun$modifyEntityList(List<? extends Entity> originalList, CommandSourceStack source) {
        // Фильтруем только если источник - игрок (не консоль/командный блок)
        if (source.getEntity() instanceof ServerPlayer sourcePlayer) {
            originalList = originalList.stream()
                    .filter(entity -> {
                        if (entity instanceof ServerPlayer targetPlayer) {
                            // Используем ваш EgorLoh для проверки видимости
                            return EgorLoh.INSTANCE.canSeeVanished(sourcePlayer, targetPlayer);
                        }
                        return true; // Не игроки не фильтруются
                    })
                    .collect(Collectors.toList());
        }
        return originalList;
    }

    // Предотвращает выбор невидимых игроков через команды для игроков, которые не должны их видеть (4/4)
    @ModifyVariable(method = "findSinglePlayer", at = @At(value = "INVOKE", target = "Ljava/util/List;size()I", shift = Shift.BEFORE))
    private List<ServerPlayer> forfun$modifyPlayerList(List<ServerPlayer> originalList, CommandSourceStack source) {
        // Фильтруем только если источник - игрок (не консоль/командный блок)
        if (source.getEntity() instanceof ServerPlayer sourcePlayer) {
            originalList = originalList.stream()
                    .filter(targetPlayer ->
                            EgorLoh.INSTANCE.canSeeVanished(sourcePlayer, targetPlayer)
                    )
                    .collect(Collectors.toList());
        }
        return originalList;
    }
}