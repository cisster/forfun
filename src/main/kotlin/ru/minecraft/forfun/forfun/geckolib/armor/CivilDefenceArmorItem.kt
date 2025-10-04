package ru.minecraft.forfun.forfun.geckolib.armor

import net.minecraft.client.model.HumanoidModel
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.ArmorItem
import net.minecraft.world.item.ArmorMaterial
import net.minecraft.world.item.ItemStack
import net.minecraftforge.client.extensions.common.IClientItemExtensions
import ru.minecraft.forfun.forfun.renderer.civil_defence_armor.CivilDefenceArmorRenderer
import software.bernie.geckolib.animatable.GeoItem
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache
import software.bernie.geckolib.core.animation.*
import software.bernie.geckolib.core.`object`.PlayState
import software.bernie.geckolib.util.GeckoLibUtil
import java.util.function.Consumer

class CivilDefenceArmorItem(
    armorMaterial: ArmorMaterial,
    type: Type,
    properties: Properties
) : ArmorItem(armorMaterial, type, properties), GeoItem {
    private val cache: AnimatableInstanceCache = GeckoLibUtil.createInstanceCache(this)

    override fun initializeClient(consumer: Consumer<IClientItemExtensions>) {
        consumer.accept(object : IClientItemExtensions {
            private var renderer: CivilDefenceArmorRenderer? = null

            override fun getHumanoidArmorModel(
                livingEntity: LivingEntity,
                itemStack: ItemStack,
                equipmentSlot: EquipmentSlot,
                original: HumanoidModel<*>
            ): HumanoidModel<*> {
                if (this.renderer == null) {
                    this.renderer = CivilDefenceArmorRenderer()
                }

                // This prepares our GeoArmorRenderer for the current render frame.
                this.renderer?.prepForRender(livingEntity, itemStack, equipmentSlot, original)

                return this.renderer as HumanoidModel<*>
            }
        })
    }
    override fun registerControllers(controllers: AnimatableManager.ControllerRegistrar) {
        controllers.add(
            AnimationController(this, "controller", 20, this::handleAnimations)
        )
        // For this example, we only want the animation to play if the entity is wearing all pieces of the armor
        // Let's collect the armor pieces the entity is currently wearing

    }
    private fun handleAnimations(state: AnimationState<CivilDefenceArmorItem>): PlayState {

        // For now, just play idle animation
        state.controller.setAnimation(RawAnimation.begin().then("idle", Animation.LoopType.LOOP))
        return PlayState.CONTINUE
    }
    override fun getAnimatableInstanceCache(): AnimatableInstanceCache {
        return this.cache
    }
}