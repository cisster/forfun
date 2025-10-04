package ru.minecraft.forfun.forfun.registry

import net.minecraft.core.particles.ParticleType
import net.minecraft.core.particles.SimpleParticleType
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import net.minecraftforge.registries.RegistryObject
import ru.minecraft.forfun.forfun.Forfun


object ParticleRegisrty {
    val PARTICLE: DeferredRegister<ParticleType<*>> = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Forfun.ID)

    val BOLT: RegistryObject<SimpleParticleType> = PARTICLE.register("bolt") { SimpleParticleType(false) }

    fun register(MOD_BUS: IEventBus){
        PARTICLE.register(MOD_BUS)
    }
}