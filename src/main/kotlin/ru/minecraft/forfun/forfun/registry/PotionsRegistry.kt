package ru.minecraft.forfun.forfun.registry

import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.item.alchemy.Potion
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import ru.minecraft.forfun.forfun.Forfun
import ru.minecraft.forfun.forfun.effects.DisapearEffect

object PotionsRegistry {
    val POTIONS = DeferredRegister.create(ForgeRegistries.POTIONS, Forfun.ID)

    val DISAPEAR = POTIONS.register("disapear_potion"){
        Potion(MobEffectInstance(MobEffectRegistry.DISAPEAR.get(), 40000, 0))
    }
}