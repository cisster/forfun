package ru.minecraft.forfun.forfun.event.custom

import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.player.Player
import net.minecraftforge.event.entity.player.PlayerEvent
import net.minecraftforge.eventbus.api.Event


@Event.HasResult
class BoltHitEvent(
    player: Player,
    var damageModifier: Float,
    val target: Entity
): PlayerEvent(player) {

    fun gTarget(): Entity {
        return target
    }

    /**
     * This sets the damage multiplier for the hit.
     * If you set it to 0, then the particles are still generated but damage is not done.
     */
    fun sTarget(mod: Float) {
        this.damageModifier = mod
    }

    /**
     * The damage modifier for the hit.<br>
     * This is by default 1.5F for critical hits and 1F for normal hits.
     */
    fun gDamageModifier(): Float {
        return this.damageModifier
    }
}
