package ru.minecraft.forfun.forfun.geckolib.armor

import  net.minecraft.sounds.SoundEvent
import net.minecraft.sounds.SoundEvents
import net.minecraft.util.StringRepresentable
import net.minecraft.world.item.ArmorItem
import net.minecraft.world.item.ArmorMaterial
import net.minecraft.world.item.crafting.Ingredient
import ru.minecraft.forfun.forfun.registry.ItemsRegistry
import java.util.function.Supplier

enum class  ModArmorMaterials(
    private val getName: String,
    private val durabilityMultiplier: Int,
    private val protectionAmounts: Map<ArmorItem.Type, Int>,
    private val enchantmentValue: Int,
    private val equipSound: SoundEvent,
    private val toughness: Float,
    private val knockbackResistance: Float,
    private val repairIngredient: Supplier<Ingredient>
) : StringRepresentable, ArmorMaterial {
    CIVIL_DEFENCE(
        "civil_defence",
        37,
        mapOf(
            ArmorItem.Type.BOOTS to 1,
            ArmorItem.Type.LEGGINGS to 3,
            ArmorItem.Type.CHESTPLATE to 4,
            ArmorItem.Type.HELMET to 3
        ),
        15,
        SoundEvents.ARMOR_EQUIP_NETHERITE,
        3.0f,
        1f,
        Supplier { Ingredient.of(ItemsRegistry.COMBINE_STEEL.get()) }
    );


    private val durabilityForType: Map<ArmorItem.Type, Int> = mapOf(
        ArmorItem.Type.BOOTS to 13,
        ArmorItem.Type.LEGGINGS to 15,
        ArmorItem.Type.CHESTPLATE to 16,
        ArmorItem.Type.HELMET to 11
    )

    override fun getDurabilityForType(type: ArmorItem.Type): Int {
        return durabilityForType[type]!! * durabilityMultiplier
    }

    override fun getDefenseForType(type: ArmorItem.Type): Int {
        return protectionAmounts[type] ?: 0
    }

    override fun getEnchantmentValue(): Int {
        return enchantmentValue
    }

    override fun getEquipSound(): SoundEvent {
        return equipSound
    }

    override fun getRepairIngredient(): Ingredient {
        return repairIngredient.get()
    }

    override fun getSerializedName(): String {
        return this.serializedName
    }
    override fun getName(): String {
        return name
    }
    override fun getToughness(): Float {
        return toughness
    }

    override fun getKnockbackResistance(): Float {
        return knockbackResistance
    }

    companion object {
        val CODEC: StringRepresentable.EnumCodec<ModArmorMaterials> = StringRepresentable.fromEnum(ModArmorMaterials::values)
    }
}