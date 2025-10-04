package ru.minecraft.forfun.forfun.renderer.civil_defence_armor

import net.minecraft.resources.ResourceLocation
import ru.minecraft.forfun.forfun.Forfun
import ru.minecraft.forfun.forfun.geckolib.armor.CivilDefenceArmorItem
import software.bernie.geckolib.model.GeoModel

object CivilDefenceArmorModel: GeoModel<CivilDefenceArmorItem>() {
    override fun getModelResource(animatable: CivilDefenceArmorItem?): ResourceLocation? {
        return ResourceLocation(Forfun.ID, "geo/civil_defence_armor.geo.json")
    }

    override fun getTextureResource(animatable: CivilDefenceArmorItem?): ResourceLocation? {
        return ResourceLocation(Forfun.ID, "textures/armor/civil_defence_armor.png")
    }

    override fun getAnimationResource(animatable: CivilDefenceArmorItem?): ResourceLocation? {
        return ResourceLocation(Forfun.ID, "animations/item/armor/civil_defence_armor.animation.json")
    }

}