package ru.minecraft.forfun.forfun

import com.mojang.blaze3d.platform.InputConstants
import net.minecraft.client.KeyMapping
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.client.event.RegisterKeyMappingsEvent
import net.minecraftforge.client.settings.KeyConflictContext
import net.minecraftforge.client.settings.KeyModifier
import net.minecraftforge.common.util.Lazy
import net.minecraftforge.eventbus.api.SubscribeEvent
import net.minecraftforge.fml.common.Mod
import org.lwjgl.glfw.GLFW


@Mod.EventBusSubscriber(modid = Forfun.ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = [Dist.CLIENT])
object KeyRegistry {
    val OFF_OVERLAY: Lazy<KeyMapping> = Lazy.of {
        KeyMapping(
            "key.forfun.disable_overlay",
            KeyConflictContext.UNIVERSAL,
            KeyModifier.CONTROL,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_R,
            "key.categories.forfun"
        )
    }


@SubscribeEvent
fun registerBindings(event: RegisterKeyMappingsEvent) {
        event.register(OFF_OVERLAY.get())
    }
}



//    val DRONE_2: Lazy<KeyMapping> = Lazy.of {
//        KeyMapping(
//            "key.forfun.drone_2",
//            KeyConflictContext.UNIVERSAL,
//            KeyModifier.CONTROL,
//            InputConstants.Type.KEYSYM,
//            GLFW.GLFW_KEY_2,
//            "key.categories.forfun"
//        )
//    }
//    val DRONE_3: Lazy<KeyMapping> = Lazy.of {
//        KeyMapping(
//            "key.forfun.drone_3",
//            KeyConflictContext.UNIVERSAL,
//            KeyModifier.CONTROL,
//            InputConstants.Type.KEYSYM,
//            GLFW.GLFW_KEY_3,
//            "key.categories.forfun"
//        )
//    }
//    val DRONE_4: Lazy<KeyMapping> = Lazy.of {
//        KeyMapping(
//            "key.forfun.drone_4",
//            KeyConflictContext.UNIVERSAL,
//            KeyModifier.CONTROL,
//            InputConstants.Type.KEYSYM,
//            GLFW.GLFW_KEY_4,
//            "key.categories.forfun"
//        )
//    }
//    val DRONE_5: Lazy<KeyMapping> = Lazy.of {
//        KeyMapping(
//            "key.forfun.drone_5",
//            KeyConflictContext.UNIVERSAL,
//            KeyModifier.CONTROL,
//            InputConstants.Type.KEYSYM,
//            GLFW.GLFW_KEY_5,
//            "key.categories.forfun"
//        )
//    }
//