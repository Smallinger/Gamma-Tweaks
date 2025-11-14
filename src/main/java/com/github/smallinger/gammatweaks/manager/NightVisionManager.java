package com.github.smallinger.gammatweaks.manager;

import com.github.smallinger.gammatweaks.Config;
import com.github.smallinger.gammatweaks.statuseffect.StatusEffectManager;
import com.github.smallinger.gammatweaks.util.InfoProvider;
import com.github.smallinger.gammatweaks.util.LightLevelUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

import java.util.Timer;
import java.util.TimerTask;

public class NightVisionManager {
    private static final Minecraft client = Minecraft.getInstance();
    private static Timer transitionTimer = null;
    private static double dynamicNightVisionTarget = Double.NaN;

    private NightVisionManager() {
    }

    public static int getNightVisionPercentage() {
        return (int)Math.round(Config.getNightVisionValue());
    }

    public static void toggleNightVision() {
        if (Config.isNightVisionEnabled()) {
            disableNightVision();
        }
        else {
            enableNightVision();
        }
    }

    public static void enableAndOrSetNightVision(int newValue) {
        if (Config.isNightVisionEnabled()) {
            NightVisionManager.setNightVision(newValue, true, true);
        }
        else {
            enableNightVision(newValue);
        }
    }

    public static void enableNightVision() {
        enableNightVision(Config.TOGGLED_NIGHT_VISION.get());
    }

    private static void enableNightVision(int newValue) {
        NightVisionManager.setNightVision(0, false, false);
        Config.setNightVisionEnabled(true);
        NightVisionManager.setNightVision(newValue, true, true);
    }

    public static void setDimensionPreference() {
        if (client.level == null || !Config.NV_DIMENSION_PREFERENCE_ENABLED.get()) {
            return;
        }

        net.minecraft.resources.ResourceKey<net.minecraft.world.level.Level> dimension = client.level.dimension();
        if (dimension.equals(net.minecraft.world.level.Level.OVERWORLD)) {
            setNightVision(Config.NV_OVERWORLD_PREFERENCE.get(), false, false);
        }
        else if (dimension.equals(net.minecraft.world.level.Level.NETHER)) {
            setNightVision(Config.NV_NETHER_PREFERENCE.get(), false, false);
        }
        else if (dimension.equals(net.minecraft.world.level.Level.END)) {
            setNightVision(Config.NV_END_PREFERENCE.get(), false, false);
        }
    }

    public static void disableNightVision() {
        NightVisionManager.setNightVision(0, true, true);
    }

    public static void increaseNightVision(int value) {
        double newValue = Config.getNightVisionValue();
        newValue += value == 0 ? Config.NIGHT_VISION_STEP.get() : value;
        setNightVision(newValue, false, true);
    }

    public static void decreaseNightVision(int value) {
        double newValue = Config.getNightVisionValue();
        newValue -= value == 0 ? Config.NIGHT_VISION_STEP.get() : value;
        setNightVision(newValue, false, true);
    }

    public static void setDynamicNightVision() {
        if (!Config.DYNAMIC_NV_ENABLED.get() || Config.isDynamicNightVisionPaused()) {
            return;
        }

        double lightLevel = LightLevelUtil.getAverageLightLevel(
            Config.NV_AVERAGING_LIGHT_RANGE.get(), 
            Config.NV_SKY_BRIGHTNESS_OVERRIDE.get() / 100f
        );
        double step = (getMaxDynamicStrength() - getMinDynamicStrength()) / 15.0;
        double target = (getMinDynamicStrength() + step * (15 - lightLevel));
        if (dynamicNightVisionTarget != target) {
            dynamicNightVisionTarget = target;
            setNightVision(target, true, false, true);
        }
    }

    public static void setNightVision(double newValue, boolean smoothTransition, boolean showMessage) {
        setNightVision(newValue, smoothTransition, showMessage, false);
    }

    private static void setNightVision(double newValue, boolean smoothTransition, boolean showMessage, boolean dynamic) {
        if (transitionTimer != null) {
            transitionTimer.cancel();
        }

        if (Config.NV_LIMIT_CHECK.get() && Config.MAX_NIGHT_VISION.get() > Config.MIN_NIGHT_VISION.get()) {
            newValue = Math.clamp(newValue, Config.MIN_NIGHT_VISION.get(), Config.MAX_NIGHT_VISION.get());
        }

        if (smoothTransition && (Config.SMOOTH_NV_TRANSITION.get() || dynamic)) {
            double valueChangePerTick = (dynamic ? Config.DYNAMIC_NV_TRANSITION_SPEED.get() : Config.NV_TRANSITION_SPEED.get()) / 100.0;
            if (newValue < Config.getNightVisionValue()) {
                valueChangePerTick *= -1;
            }
            startTransitionTimer(newValue, valueChangePerTick, showMessage);
        }
        else {
            Config.setNightVisionValue(newValue);
            Config.setNightVisionEnabled(newValue != 0);
            StatusEffectManager.updateNightVision();
            if (showMessage) {
                InfoProvider.showNightVisionStatusHudMessage();
            }
        }

        if (Config.UPDATE_NV_TOGGLE.get() && newValue != 0) {
            Config.TOGGLED_NIGHT_VISION.set((int)Math.round(newValue));
        }
    }

    protected static void toggleStatusEffect() {
        boolean newStatus = !Config.SHOW_NV_STATUS_EFFECT.get();
        Config.SHOW_NV_STATUS_EFFECT.set(newStatus);
        Component message = Component.translatable("text.gammatweaks.message.statusEffectNightVision" + (newStatus ? "On" : "Off"));
        InfoProvider.sendMessage(message);
    }

    protected static void toggleSmoothTransition() {
        boolean newStatus = !Config.SMOOTH_NV_TRANSITION.get();
        Config.SMOOTH_NV_TRANSITION.set(newStatus);
        Component message = Component.translatable("text.gammatweaks.message.transitionNightVision" + (newStatus ? "On" : "Off"));
        InfoProvider.sendMessage(message);
    }

    private static void startTransitionTimer(double newValue, double valueChangePerTick, boolean showMessage) {
        transitionTimer = new Timer();
        transitionTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                double nextValue = Config.getNightVisionValue() + valueChangePerTick;
                if ((valueChangePerTick > 0 && nextValue >= newValue) ||
                        (valueChangePerTick < 0 && nextValue <= newValue)) {
                    transitionTimer.cancel();
                    Config.setNightVisionValue(newValue);
                    Config.setNightVisionEnabled(newValue != 0);
                    StatusEffectManager.updateNightVision();
                }
                else {
                    Config.setNightVisionValue(nextValue);
                    Config.setNightVisionEnabled(nextValue != 0);
                    StatusEffectManager.updateNightVision();
                }

                if (showMessage) {
                    InfoProvider.showNightVisionStatusHudMessage();
                }
            }
        }, 0, 10);
    }

    private static double getMinDynamicStrength() {
        return Config.MIN_DYNAMIC_NV.get() / 100.0;
    }

    private static double getMaxDynamicStrength() {
        return Config.MAX_DYNAMIC_NV.get() / 100.0;
    }

    private static double getTransitionSpeed(boolean dynamic) {
        return (dynamic ? Config.DYNAMIC_NV_TRANSITION_SPEED.get() : Config.NV_TRANSITION_SPEED.get()) / 100.0;
    }
}
