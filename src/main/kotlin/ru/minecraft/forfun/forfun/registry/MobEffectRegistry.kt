package ru.minecraft.forfun.forfun.registry

import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import ru.minecraft.forfun.forfun.Forfun
import ru.minecraft.forfun.forfun.effects.DisapearEffect
import ru.minecraft.forfun.forfun.registry.ItemsRegistry.ITEMS

object MobEffectRegistry {
    val MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Forfun.ID)

    val DISAPEAR = MOB_EFFECTS.register("disapear"){
        DisapearEffect()
    }


    fun register(MOD_BUS: IEventBus){
        MOB_EFFECTS.register(MOD_BUS)
    }
}