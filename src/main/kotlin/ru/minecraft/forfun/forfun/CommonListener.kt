package ru.minecraft.forfun.forfun


import net.minecraft.world.entity.FlyingMob
import net.minecraft.world.entity.ai.attributes.AttributeSupplier
import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraftforge.client.event.EntityRenderersEvent
import net.minecraftforge.event.entity.EntityAttributeCreationEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import ru.minecraft.forfun.forfun.registry.EntityRegistry
import ru.minecraft.forfun.forfun.renderer.drone.DroneRenderer


@Mod.EventBusSubscriber(modid = Forfun.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
object CommonListener {
    @SubscribeEvent
    fun registerEntityAttributes(event: EntityAttributeCreationEvent) {
        val genericAttribs: AttributeSupplier.Builder = FlyingMob.createMobAttributes()
            .add(Attributes.ARMOR, 10.0)
            .add(Attributes.MAX_HEALTH, 20.0)

        event.put(EntityRegistry.DRONE.get(), genericAttribs.build())
    }
    @SubscribeEvent
    fun registerRenderers(event: EntityRenderersEvent.RegisterRenderers) {
        event.registerEntityRenderer(EntityRegistry.DRONE.get(), ::DroneRenderer)
    }
}