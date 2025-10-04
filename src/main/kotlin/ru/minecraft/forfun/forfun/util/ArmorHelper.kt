package ru.minecraft.forfun.forfun.util

import net.minecraft.client.Minecraft
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import ru.minecraft.forfun.forfun.registry.ItemsRegistry

object ArmorHelper {
    fun changeIntToArmor(armorStack: ItemStack, item: Item, armorTag: String, integer: Int){
        if (armorStack.`is`(item)){
            val currentBolt = getArmorDataInt(armorStack, armorTag)
            setArmorDataInt(armorStack, armorTag, currentBolt + integer)
        }
    }
    fun setArmorDataInt(armorStack: ItemStack,armorTag: String, charge: Int) {
        val tag = armorStack.getOrCreateTag() // Получаем или создаем тег
        tag.putInt(armorTag, charge) // Записываем значение
    }
    // Пример функции для чтения данных из брони
    fun getArmorDataInt(armorStack: ItemStack, armorTag: String): Int {
        val tag = armorStack.orCreateTag // Используем свойство Kotlin
        return tag.getInt(armorTag) // Читаем значение
    }
    fun checkFirstArmorInt(armorStack: ItemStack, armorTag: String){
        if (getArmorDataInt(armorStack, armorTag) == null){
            reset(armorStack, armorTag)
        }
    }
    fun reset(armorStack: ItemStack, armorTag: String){
        setArmorDataInt(armorStack, armorTag, 0)
    }
    fun checkHelmet(): Boolean {
        val player = Minecraft.getInstance().player
        if (player != null) {
            val helmet: ItemStack = player.inventory.getArmor(3)
            return helmet.`is`(ItemsRegistry.CIVIL_DEFENCE_HELMET.get())
        }
        return false
    }
}