package ru.minecraft.forfun.forfun.effects

import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.effect.MobEffect
import net.minecraft.world.effect.MobEffectCategory
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import ru.minecraft.forfun.forfun.renderer.player.EgorLoh
import software.bernie.geckolib.core.`object`.Color

class DisapearEffect() : MobEffect(MobEffectCategory.NEUTRAL, 0xFFFFFF) {


    override fun applyEffectTick(entity: LivingEntity, amplifier: Int) {
        if (entity is Player) {
            EgorLoh.vanishPlayer(entity as ServerPlayer)
        }
        EgorLoh.vanishEntity(entity)
    }
}