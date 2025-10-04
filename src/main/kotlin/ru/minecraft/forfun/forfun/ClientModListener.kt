package ru.minecraft.forfun.forfun

import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.client.event.RegisterParticleProvidersEvent
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import ru.minecraft.forfun.forfun.particles.BoltParticleProvider
import ru.minecraft.forfun.forfun.registry.ParticleRegisrty

@Mod.EventBusSubscriber(modid = Forfun.ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = [Dist.CLIENT])
object ClientModListener {
    @SubscribeEvent
    fun onRegisterParticleProviders(event: RegisterParticleProvidersEvent) {
        event.registerSpriteSet(ParticleRegisrty.BOLT.get(), ::BoltParticleProvider)
    }
}