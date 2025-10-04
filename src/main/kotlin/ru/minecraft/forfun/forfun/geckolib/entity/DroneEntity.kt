package ru.minecraft.forfun.forfun.geckolib.entity


import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.FlyingMob
import net.minecraft.world.entity.ai.goal.FloatGoal
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level
import software.bernie.geckolib.animatable.GeoEntity
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache
import software.bernie.geckolib.core.animation.*
import software.bernie.geckolib.core.`object`.PlayState
import software.bernie.geckolib.util.GeckoLibUtil
import java.lang.ref.WeakReference

class DroneEntity(
    type: EntityType<out DroneEntity>,
    level: Level
) : FlyingMob(type, level), GeoEntity {

    private var ownerUUID: String? = null
    private var id: Int? = null
    private var viewer: WeakReference<Player>? = null

    companion object {
        protected val FLY_ANIM: RawAnimation = RawAnimation.begin().thenPlay("animation.fly")
        protected val STOP_ANIM: RawAnimation = RawAnimation.begin().thenPlay("animation.stop")
    }


    private val geoCache: AnimatableInstanceCache = GeckoLibUtil.createInstanceCache(this)

    override fun registerGoals() {
        this.goalSelector.addGoal(1, LookAtPlayerGoal(this, Player::class.java, 8.0F))
        this.goalSelector.addGoal(2, RandomLookAroundGoal(this))
        this.goalSelector.addGoal(3, FloatGoal(this))
    }

    override fun registerControllers(controllers: AnimatableManager.ControllerRegistrar) {
        controllers.add(AnimationController(this, "animation.idle", 5, this::idleAnimController))
        controllers.add(AnimationController(this, "animation.fly", 5, this::flyAnimController))
    }

    private fun idleAnimController(state: AnimationState<DroneEntity>): PlayState {
        state.controller.setAnimation(RawAnimation.begin().then("animation.idle", Animation.LoopType.LOOP))
        return PlayState.CONTINUE
    }

    protected fun flyAnimController(event: AnimationState<DroneEntity>): PlayState {
        return if (event.isMoving) {
            event.setAndContinue(FLY_ANIM)
        } else {
            event.setAndContinue(STOP_ANIM)
        }
    }

    override fun getAnimatableInstanceCache(): AnimatableInstanceCache {
        return geoCache
    }
}
