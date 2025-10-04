package ru.minecraft.forfun.forfun.renderer.drone

import net.minecraft.resources.ResourceLocation
import ru.minecraft.forfun.forfun.Forfun
import ru.minecraft.forfun.forfun.geckolib.entity.DroneEntity
import software.bernie.geckolib.model.GeoModel

object DroneModel: GeoModel<DroneEntity>() {
    override fun getModelResource(animatable: DroneEntity?): ResourceLocation? {
        return ResourceLocation(Forfun.ID, "geo/drone.geo.json")
    }

    override fun getTextureResource(animatable: DroneEntity?): ResourceLocation? {
        return ResourceLocation(Forfun.ID, "textures/entity/drone.png")
    }

    override fun getAnimationResource(animatable: DroneEntity?): ResourceLocation? {
        return ResourceLocation(Forfun.ID, "animations/entity/drone.animation.json")
    }

}