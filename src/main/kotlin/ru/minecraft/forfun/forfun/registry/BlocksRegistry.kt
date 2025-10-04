package ru.minecraft.forfun.forfun.registry

import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import net.minecraftforge.registries.RegistryObject
import ru.minecraft.forfun.forfun.Forfun
import java.util.function.Supplier

object BlocksRegistry {
    val BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Forfun.ID)

    val COMBINE_STEEL_BLOCK = registerBlock("combine_steel_block") {
        Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK))
    }

    private fun <T : Block> registerBlock(
        name: String,
        blockSupplier: Supplier<T>
    ): RegistryObject<T> {
        val toReturn = BLOCKS.register(name, blockSupplier)
        registerBlockItem(name, toReturn)
        return toReturn
    }

    private fun <T : Block> registerBlock(
        name: String,
        blockSupplier: () -> T
    ): RegistryObject<T> {
        return registerBlock(name, Supplier(blockSupplier))
    }

    private fun <T : Block> registerBlockItem(
        name: String,
        block: RegistryObject<T>
    ): RegistryObject<Item> {
        return ItemsRegistry.ITEMS.register(name) {
            BlockItem(block.get(), Item.Properties())
        }
    }
}