package ru.minecraft.forfun.forfun.renderer.drone

import net.minecraft.client.renderer.entity.EntityRendererProvider
import ru.minecraft.forfun.forfun.geckolib.entity.DroneEntity
import software.bernie.geckolib.renderer.GeoEntityRenderer

class DroneRenderer(context: EntityRendererProvider.Context) :
    GeoEntityRenderer<DroneEntity>(context, DroneModel)