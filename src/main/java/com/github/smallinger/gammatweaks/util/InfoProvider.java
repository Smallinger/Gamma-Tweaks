package com.github.smallinger.gammatweaks.util;

import com.github.smallinger.gammatweaks.Config;
import com.github.smallinger.gammatweaks.GammaTweaks;
import com.github.smallinger.gammatweaks.manager.GammaManager;
import com.github.smallinger.gammatweaks.manager.NightVisionManager;
import com.github.smallinger.gammatweaks.statuseffect.StatusEffectManager;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public final class InfoProvider {
    private static final Minecraft client = Minecraft.getInstance();
    private static long lastShaderWarningTime = 0;
    private static final long SHADER_WARNING_COOLDOWN = 10000; // 10 seconds in milliseconds

    private InfoProvider() {
    }

    public static void sendMessage(Component message) {
        if (client.player == null) {
            GammaTweaks.LOGGER.info(message.getString());
            return;
        }

        client.player.displayClientMessage(message, false);
    }

    public static void showGammaHudMessage() {
        StatusEffectManager.updateGammaStatusEffect();
        if (!Config.SHOW_GAMMA_HUD_MESSAGE.get()) {
            return;
        }

        int gamma = GammaManager.getGammaPercentage();
        MutableComponent message;
        int color;
        
        if (gamma == 100) {
            message = Component.translatable("text.gammatweaks.message.gammaDisabled");
            color = Config.GAMMA_HUD_DEFAULT_COLOR.get();
        }
        else {
            message = Component.translatable("text.gammatweaks.message.gammaPercentage", gamma);
            
            if (gamma < 0) {
                color = Config.GAMMA_HUD_NEGATIVE_COLOR.get();
            }
            else if (gamma > 100) {
                color = Config.GAMMA_HUD_POSITIVE_COLOR.get();
            }
            else {
                color = Config.GAMMA_HUD_DEFAULT_COLOR.get();
            }
        }

        message.withColor(color);
        client.gui.setOverlayMessage(message, false);
        
        // Show shader warning as chat message if Iris has a shader pack loaded (with cooldown)
        if (IrisIntegration.isShaderPackActive() && gamma != 100) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastShaderWarningTime >= SHADER_WARNING_COOLDOWN) {
                client.player.displayClientMessage(Component.translatable("text.gammatweaks.message.shaderWarning"), false);
                lastShaderWarningTime = currentTime;
            }
        }
    }

    public static void showDynamicGammaHudMessage() {
        if (!Config.SHOW_GAMMA_HUD_MESSAGE.get()) {
            return;
        }

        MutableComponent message;
        if (Config.isDynamicGammaPaused()) {
            message = Component.translatable("text.gammatweaks.message.dynamicGammaDisabled");
            message.withColor(Config.NV_HUD_DISABLED_COLOR.get());
        }
        else {
            message = Component.translatable("text.gammatweaks.message.dynamicGammaEnabled");
            message.withColor(Config.NV_HUD_ENABLED_COLOR.get());
        }

        client.gui.setOverlayMessage(message, false);
    }

    public static void showDynamicNightVisionHudMessage() {
        if (!Config.SHOW_NV_HUD_MESSAGE.get()) {
            return;
        }

        MutableComponent message;
        if (Config.isDynamicNightVisionPaused()) {
            message = Component.translatable("text.gammatweaks.message.dynamicNightVisionDisabled");
            message.withColor(Config.NV_HUD_DISABLED_COLOR.get());
        }
        else {
            message = Component.translatable("text.gammatweaks.message.dynamicNightVisionEnabled");
            message.withColor(Config.NV_HUD_ENABLED_COLOR.get());
        }

        client.gui.setOverlayMessage(message, false);
    }

    public static void showNightVisionStatusHudMessage() {
        StatusEffectManager.updateNightVision();
        if (!Config.SHOW_NV_HUD_MESSAGE.get()) {
            return;
        }

        if (Config.isNightVisionEnabled()) {
            showNightVisionStrengthHudMessage();
        }
        else {
            MutableComponent message = Component.translatable("text.gammatweaks.message.nightVisionDisabled");
            message.withColor(Config.NV_HUD_DISABLED_COLOR.get());
            client.gui.setOverlayMessage(message, false);
        }
    }

    public static void showNightVisionStrengthHudMessage() {
        if (!Config.SHOW_NV_HUD_MESSAGE.get()) {
            return;
        }

        int nightVision = NightVisionManager.getNightVisionPercentage();
        MutableComponent message = Component.translatable("text.gammatweaks.message.nightVisionPercentage", nightVision);
        message.withColor(Config.NV_HUD_ENABLED_COLOR.get());
        client.gui.setOverlayMessage(message, false);
    }
}
