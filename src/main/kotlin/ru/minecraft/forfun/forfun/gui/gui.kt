package ru.minecraft.forfun.forfun.gui

import com.mojang.blaze3d.platform.Window
import com.mojang.blaze3d.systems.RenderSystem
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.ItemStack
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import ru.minecraft.forfun.forfun.ClientForgeListener.overlay
import ru.minecraft.forfun.forfun.Forfun
import ru.minecraft.forfun.forfun.event.events.onTickEvent
import ru.minecraft.forfun.forfun.registry.ItemsRegistry
import ru.minecraft.forfun.forfun.util.ArmorHelper


@OnlyIn(Dist.CLIENT)
object gui {
    val CIVIL_DEFENCE_BLUR: ResourceLocation = ResourceLocation(Forfun.ID, "textures/misc/civil_defence_blur.png")
    val CONDENSATOR_UP: ResourceLocation = ResourceLocation(Forfun.ID, "textures/misc/condensator_up.png")
    val CONDENSATOR_DOWN: ResourceLocation = ResourceLocation(Forfun.ID, "textures/misc/condensator_down.png")
    val CONDENSATOR_ENABLED_UP: ResourceLocation = ResourceLocation(Forfun.ID, "textures/misc/condensator_enabled_up.png")
    val CONDENSATOR_ENABLED_DOWN: ResourceLocation = ResourceLocation(Forfun.ID, "textures/misc/condensator_enabled_down.png")
    val LIGHTNING_ANIMATION_1: ResourceLocation = ResourceLocation(Forfun.ID, "textures/misc/lightning_animation_1.png")
    val LIGHTNING_ANIMATION_2: ResourceLocation = ResourceLocation(Forfun.ID, "textures/misc/lightning_animation_2.png")
    val LIGHTNING_ANIMATION_3: ResourceLocation = ResourceLocation(Forfun.ID, "textures/misc/lightning_animation_3.png")
    val LIGHTNING_ANIMATION_4: ResourceLocation = ResourceLocation(Forfun.ID, "textures/misc/lightning_animation_4.png")
    val LIGHTNING_ANIMATION_5: ResourceLocation = ResourceLocation(Forfun.ID, "textures/misc/lightning_animation_5.png")
    val LIGHTNING_ANIMATION_6: ResourceLocation = ResourceLocation(Forfun.ID, "textures/misc/lightning_animation_6.png")
    const val LIGHTNING_WIDTH = 24
    const val LIGHTNING_HEIGHT = 512
    const val CONDENSATOR_WIDTH = 14
    const val CONDENSATOR_HEIGHT = 22
    var screenWidth: Int = 0
    var screenHeight: Int = 0
    fun renderTextureOverlay(guiGraphics: GuiGraphics, pShaderLocation: ResourceLocation, pAlpha: Float) {
        RenderSystem.enableBlend()
        RenderSystem.disableDepthTest()
        RenderSystem.depthMask(false)
        guiGraphics.setColor(1.0f, 1.0f, 1.0f, pAlpha)
        guiGraphics.blit(
            pShaderLocation,
            0,
            0,
            0,
            0.0f,
            0.0f,
            this.screenWidth,
            this.screenHeight,
            this.screenWidth,
            this.screenHeight
        )
        RenderSystem.depthMask(true)
        RenderSystem.enableDepthTest()
        guiGraphics.setColor(1.0f, 1.0f, 1.0f, 1.0f)
    }
    fun renderCondensator(guiGraphics: GuiGraphics){
        val xPos = 0 // Отступ x
        val yPos = 0  // Отступ y

        guiGraphics.blit(
            CONDENSATOR_UP,
            xPos, yPos,           // Позиция на экране
            0f, 0f,               // UV-координаты текстуры (начало)
            CONDENSATOR_WIDTH, CONDENSATOR_HEIGHT,  // Размер отрисовки
            CONDENSATOR_WIDTH, CONDENSATOR_HEIGHT   // Размер области текстуры
        )
        guiGraphics.blit(
            CONDENSATOR_UP,
            this.screenWidth-CONDENSATOR_WIDTH, yPos,           // Позиция на экране
            0f, 0f,               // UV-координаты текстуры (начало)
            CONDENSATOR_WIDTH, CONDENSATOR_HEIGHT,  // Размер отрисовки
            CONDENSATOR_WIDTH, CONDENSATOR_HEIGHT   // Размер области текстуры
        )
        guiGraphics.blit(
            CONDENSATOR_DOWN,
            xPos, this.screenHeight-CONDENSATOR_HEIGHT,           // Позиция на экране
            0f, 0f,               // UV-координаты текстуры (начало)
            CONDENSATOR_WIDTH, CONDENSATOR_HEIGHT,  // Размер отрисовки
            CONDENSATOR_WIDTH, CONDENSATOR_HEIGHT   // Размер области текстуры
        )
        guiGraphics.blit(
            CONDENSATOR_DOWN,
            this.screenWidth-CONDENSATOR_WIDTH, this.screenHeight-CONDENSATOR_HEIGHT,           // Позиция на экране
            0f, 0f,               // UV-координаты текстуры (начало)
            CONDENSATOR_WIDTH, CONDENSATOR_HEIGHT,  // Размер отрисовки
            CONDENSATOR_WIDTH, CONDENSATOR_HEIGHT   // Размер области текстуры
        )
    }
    fun renderEnabledCondensator(guiGraphics: GuiGraphics){
        val xPos = 0 // Отступ x
        val yPos = 0
        RenderSystem.enableBlend()
        RenderSystem.disableDepthTest()
        RenderSystem.depthMask(false)
        guiGraphics.setColor(1.0f, 1.0f, 1.0f, 1.0f) // Отступ y
        guiGraphics.blit(
            CONDENSATOR_ENABLED_UP,
            xPos, yPos,           // Позиция на экране
            0f, 0f,               // UV-координаты текстуры (начало)
            CONDENSATOR_WIDTH, CONDENSATOR_HEIGHT,  // Размер отрисовки
            CONDENSATOR_WIDTH, CONDENSATOR_HEIGHT   // Размер области текстуры
        )
        guiGraphics.blit(
            CONDENSATOR_ENABLED_UP,
            this.screenWidth-CONDENSATOR_WIDTH, yPos,           // Позиция на экране
            0f, 0f,               // UV-координаты текстуры (начало)
            CONDENSATOR_WIDTH, CONDENSATOR_HEIGHT,  // Размер отрисовки
            CONDENSATOR_WIDTH, CONDENSATOR_HEIGHT   // Размер области текстуры
        )
        guiGraphics.blit(
            CONDENSATOR_ENABLED_DOWN,
            xPos, this.screenHeight-CONDENSATOR_HEIGHT,           // Позиция на экране
            0f, 0f,               // UV-координаты текстуры (начало)
            CONDENSATOR_WIDTH, CONDENSATOR_HEIGHT,  // Размер отрисовки
            CONDENSATOR_WIDTH, CONDENSATOR_HEIGHT   // Размер области текстуры
        )
        guiGraphics.blit(
            CONDENSATOR_ENABLED_DOWN,
            this.screenWidth-CONDENSATOR_WIDTH, this.screenHeight-CONDENSATOR_HEIGHT,           // Позиция на экране
            0f, 0f,               // UV-координаты текстуры (начало)
            CONDENSATOR_WIDTH, CONDENSATOR_HEIGHT,  // Размер отрисовки
            CONDENSATOR_WIDTH, CONDENSATOR_HEIGHT   // Размер области текстуры
        )
        RenderSystem.depthMask(true)
        RenderSystem.enableDepthTest()
        guiGraphics.setColor(1.0f, 1.0f, 1.0f, 1.0f)
    }
    fun renderCivilDefence(guiGraphics: GuiGraphics, minecraft: Minecraft){
        val window: Window = minecraft.window
        val player = minecraft.player

        this.screenWidth = guiGraphics.guiWidth()
        this.screenHeight = guiGraphics.guiHeight()

        if (player != null && minecraft.options.cameraType.isFirstPerson) {
            val itemstack: ItemStack = player.inventory.getArmor(3)

            if (itemstack.`is`(ItemsRegistry.CIVIL_DEFENCE_HELMET.get()) && overlay){
                renderForState(guiGraphics, minecraft)
                renderTextureOverlay(guiGraphics, CIVIL_DEFENCE_BLUR, 0.3F )
            }
        }
    }
    fun renderLightning(guiGraphics: GuiGraphics){
        this.screenWidth = guiGraphics.guiWidth()
        this.screenHeight = guiGraphics.guiHeight()


        RenderSystem.enableBlend()
        RenderSystem.disableDepthTest()
        RenderSystem.depthMask(false)
        guiGraphics.setColor(1.0f, 1.0f, 1.0f, 1.0f)
        guiGraphics.blit(
            onTickEvent.renderLightning(onTickEvent.frame),
            0, 5,           // Позиция на экране
            0f, 0f,               // UV-координаты текстуры (начало)
            LIGHTNING_WIDTH, LIGHTNING_HEIGHT,  // Размер отрисовки
            LIGHTNING_WIDTH, LIGHTNING_HEIGHT   // Размер области текстуры
        )
        guiGraphics.blit(
            onTickEvent.renderLightning(onTickEvent.frame),
            this.screenWidth-CONDENSATOR_WIDTH-4, 5,           // Позиция на экране
            0f, 0f,               // UV-координаты текстуры (начало)
            LIGHTNING_WIDTH, LIGHTNING_HEIGHT,  // Размер отрисовки
            LIGHTNING_WIDTH, LIGHTNING_HEIGHT   // Размер области текстуры
        )
        RenderSystem.depthMask(true)
        RenderSystem.enableDepthTest()
        guiGraphics.setColor(1.0f, 1.0f, 1.0f, 1.0f)
        renderEnabledCondensator(guiGraphics)
    }
    fun renderForState(guiGraphics: GuiGraphics, minecraft: Minecraft){
        val player = minecraft.player

        this.screenWidth = guiGraphics.guiWidth()
        this.screenHeight = guiGraphics.guiHeight()
        if (player != null) {
            val helmet: ItemStack = player.inventory.getArmor(3)
            val hit = ArmorHelper.getArmorDataInt(helmet, "Bolt")
            return when (hit) {
                0 -> renderCondensator(guiGraphics)
                1 -> renderCondensator(guiGraphics)
                2 -> renderEnabledCondensator(guiGraphics)
                else -> renderLightning(guiGraphics)
            }
        }
    }
}