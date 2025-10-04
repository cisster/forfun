package ru.minecraft.forfun.forfun.registry

import net.minecraft.world.item.ArmorItem
import net.minecraft.world.item.ArmorMaterials
import net.minecraft.world.item.Item
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import net.minecraftforge.registries.RegistryObject
import ru.minecraft.forfun.forfun.Forfun
import ru.minecraft.forfun.forfun.geckolib.armor.CivilDefenceArmorItem

object ItemsRegistry {
    val ITEMS: DeferredRegister<Item> = DeferredRegister.create(ForgeRegistries.ITEMS, Forfun.ID)

    val COMBINE_STEEL: RegistryObject<Item> = ITEMS.register("combine_steel") { Item(Item.Properties()) }
    val COMBINE_STEEL_PLATE: RegistryObject<Item> = ITEMS.register("combine_steel_plate") { Item(Item.Properties()) }

    val CIVIL_DEFENCE_BOOTS: RegistryObject<Item> = ITEMS.register("civil_defence_boots", ) {
        CivilDefenceArmorItem(
            ArmorMaterials.DIAMOND,
            ArmorItem.Type.BOOTS,
            Item.Properties()
        )
    }
    val CIVIL_DEFENCE_LEGGINGS: RegistryObject<Item> = ITEMS.register("civil_defence_leggings", ) {
        CivilDefenceArmorItem(
            ArmorMaterials.DIAMOND,
            ArmorItem.Type.LEGGINGS,
            Item.Properties()
        )
    }
    val CIVIL_DEFENCE_CHESTPLATE: RegistryObject<Item> = ITEMS.register("civil_defence_chestplate", ) {
        CivilDefenceArmorItem(
            ArmorMaterials.DIAMOND,
            ArmorItem.Type.CHESTPLATE,
            Item.Properties()
        )
    }
    val CIVIL_DEFENCE_HELMET: RegistryObject<Item> = ITEMS.register("civil_defence_helmet", ) {
        CivilDefenceArmorItem(
            ArmorMaterials.DIAMOND  ,
            ArmorItem.Type.HELMET,
            Item.Properties()
        )
    }

    fun register(MOD_BUS: IEventBus){
        ITEMS.register(MOD_BUS)
    }
}