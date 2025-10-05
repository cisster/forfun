package ru.minecraft.forfun.forfun.mixin;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.minecraft.forfun.forfun.screens.updateScreen;
import ru.minecraft.forfun.forfun.util.autoupdater.AutoUpdater;

@Mixin(TitleScreen.class)
public class TitleScreenMixin extends Screen {

    protected TitleScreenMixin(Component pTitle) {
        super(pTitle);
    }

    @Inject(at = @At("TAIL"), method = "init")
    private void addCustomButton(CallbackInfo ci) {
        final Minecraft mc = Minecraft.getInstance();
        this.addRenderableWidget(Button.builder(Component.literal("Порверить обновления"), button -> {
            if (AutoUpdater.INSTANCE.checkShowScreen()) {
                mc.tell(() -> {
                    mc.setScreen(
                            new updateScreen(
                                    (accepted) -> {
                                        if (accepted) {
                                            AutoUpdater.INSTANCE.getUpdated();
                                            mc.setScreen(null);
                                        } else {
                                            // Действие при отказе
                                            mc.setScreen(null);
                                        }
                                    },
                                    Component.literal("Установить обновление мода"),
                                    Component.literal("Мод для приколов - " + AutoUpdater.INSTANCE.checkUpdate())
                                            .withStyle(Style.EMPTY.withColor(
                                                    TextColor.fromLegacyFormat(ChatFormatting.GREEN)
                                            ))
                            )
                    );
                });
            }
        }).bounds(this.width - 100, 10, 90, 20).build()); // Position in top-right
    }
}