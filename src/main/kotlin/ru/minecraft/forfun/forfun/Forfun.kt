package ru.minecraft.forfun.forfun

import net.minecraft.client.Minecraft
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent
import net.minecraftforge.fml.loading.FMLPaths
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import ru.minecraft.forfun.forfun.ClientForgeListener.minecraft
import ru.minecraft.forfun.forfun.packets.PacketHandler
import ru.minecraft.forfun.forfun.registry.*
import ru.minecraft.forfun.forfun.util.autoupdater.AutoUpdater
import thedarkcolour.kotlinforforge.forge.MOD_BUS
import thedarkcolour.kotlinforforge.forge.runForDist


@Mod(Forfun.ID)
object Forfun {
    const val ID = "forfun"

    // the logger for our mod
    val LOGGER: Logger = LogManager.getLogger(ID)

    init {
        LOGGER.log(Level.INFO, "4242424242424242424242")

        // Register the KDeferredRegister to the mod-specific event bus
        ItemsRegistry.ITEMS.register(MOD_BUS)
        BlocksRegistry.BLOCKS.register(MOD_BUS)
        EntityRegistry.ENTITIES.register(MOD_BUS)
        ParticleRegisrty.PARTICLE.register(MOD_BUS)
        SoundRegistry.SOUND_EVENTS.register(MOD_BUS)
        MobEffectRegistry.MOB_EFFECTS.register(MOD_BUS)
        PotionsRegistry.POTIONS.register(MOD_BUS)



        MOD_BUS.addListener(::onCommonSetup)

        val obj = runForDist(
            clientTarget = {
                MOD_BUS.addListener(::onClientSetup)
                Minecraft.getInstance()
            },
            serverTarget = {
                MOD_BUS.addListener(::onServerSetup)
                "test"
            })

        println(obj)
    }


    /**
     * This is used for initializing client specific
     * things such as renderers and keymaps
     *
     *
     */

    private fun onCommonSetup(event: FMLCommonSetupEvent) {
        LOGGER.log(Level.INFO, "Common setup...4242424")

        event.enqueueWork {
            PacketHandler.registerPackets()
        }
    }

    private fun onClientSetup(event: FMLClientSetupEvent) {
        LOGGER.log(Level.INFO, "Initializing client...4242424")
        AutoUpdater.firstCreation()
    }

    /**
     * Fired on the global Forge bus.
     */
    private fun onServerSetup(event: FMLDedicatedServerSetupEvent) {
        LOGGER.log(Level.INFO, "Server starting...4242424")
    }
}