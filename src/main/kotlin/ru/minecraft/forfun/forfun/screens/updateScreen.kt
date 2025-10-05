package ru.minecraft.forfun.forfun.screens

import it.unimi.dsi.fastutil.booleans.BooleanConsumer
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.Button
import net.minecraft.client.gui.components.MultiLineLabel
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.CommonComponents
import net.minecraft.network.chat.Component
import net.minecraft.util.Mth
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn

@OnlyIn(Dist.CLIENT)
class updateScreen(
    private val callback: BooleanConsumer,
    pTitle: Component,
    private val message: Component,
    protected var yesButton: Component,
    protected var noButton: Component
) : Screen(pTitle) {

    companion object {
        private const val MARGIN = 20
    }

    private var multilineMessage: MultiLineLabel = MultiLineLabel.EMPTY
    private var delayTicker: Int = 0
    private val exitButtons: MutableList<Button> = mutableListOf()

    // Вторичный конструктор для совместимости с оригинальным API
    constructor(
        pCallback: BooleanConsumer,
        pTitle: Component,
        pMessage: Component
    ) : this(
        pCallback,
        pTitle,
        pMessage,
        CommonComponents.GUI_YES,
        CommonComponents.GUI_NO
    )

    override fun getNarrationMessage(): Component {
        return CommonComponents.joinForNarration(super.getNarrationMessage(), message)
    }

    override fun init() {
        super.init()
        multilineMessage = MultiLineLabel.create(font, message, width - 50)
        val i = Mth.clamp(messageTop() + messageHeight() + 20, height / 6 + 96, height - 24)
        exitButtons.clear()
        addButtons(i)
    }

    protected fun addButtons(pY: Int) {
        addExitButton(
            Button.builder(yesButton) { _ ->
                callback.accept(true)
            }.bounds(width / 2 - 155, pY, 150, 20).build()
        )
        addExitButton(
            Button.builder(noButton) { _ ->
                callback.accept(false)
            }.bounds(width / 2 - 155 + 160, pY, 150, 20).build()
        )
    }

    protected fun addExitButton(pExitButton: Button) {
        exitButtons.add(addRenderableWidget(pExitButton))
    }

    override fun render(
        pGuiGraphics: GuiGraphics,
        pMouseX: Int,
        pMouseY: Int,
        pPartialTick: Float
    ) {
        this.renderBackground(pGuiGraphics)
        pGuiGraphics.drawCenteredString(font, title, width / 2, titleTop(), 16777215)
        multilineMessage.renderCentered(pGuiGraphics, width / 2, messageTop())
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick)
    }

    private fun titleTop(): Int {
        val i = (height - messageHeight()) / 2
        return Mth.clamp(i - 20 - 9, 10, 80)
    }

    private fun messageTop(): Int {
        return titleTop() + 20
    }

    private fun messageHeight(): Int {
        return multilineMessage.lineCount * 9
    }

    fun setDelay(pTicksUntilEnable: Int) {
        delayTicker = pTicksUntilEnable
        for (button in exitButtons) {
            button.active = false
        }
    }

    override fun tick() {
        super.tick()
        if (--delayTicker == 0) {
            for (button in exitButtons) {
                button.active = true
            }
        }
    }

    override fun shouldCloseOnEsc(): Boolean {
        return false
    }

    override fun keyPressed(pKeyCode: Int, pScanCode: Int, pModifiers: Int): Boolean {
        if (pKeyCode == 256) {
            minecraft?.setScreen(null)
            callback.accept(false)
            return true
        }
        return super.keyPressed(pKeyCode, pScanCode, pModifiers)
    }
}