package ru.minecraft.forfun.forfun.registry

import net.minecraft.resources.ResourceLocation
import net.minecraft.sounds.SoundEvent
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import net.minecraftforge.registries.RegistryObject
import ru.minecraft.forfun.forfun.Forfun

object SoundRegistry {
    val SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Forfun.ID)

    val BOLT: RegistryObject<SoundEvent> = SOUND_EVENTS.register("bolt") {
        SoundEvent.createVariableRangeEvent(ResourceLocation(Forfun.ID, "bolt"))
    }
    fun register(MOD_BUS: IEventBus){
        SOUND_EVENTS.register(MOD_BUS)
    }
}