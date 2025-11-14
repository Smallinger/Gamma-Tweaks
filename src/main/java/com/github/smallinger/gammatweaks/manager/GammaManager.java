package com.github.smallinger.gammatweaks.manager;

import com.github.smallinger.gammatweaks.Config;
import com.github.smallinger.gammatweaks.statuseffect.StatusEffectManager;
import com.github.smallinger.gammatweaks.util.InfoProvider;
import com.github.smallinger.gammatweaks.util.LightLevelUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

import java.util.Timer;
import java.util.TimerTask;

public class GammaManager {
    private static final Minecraft client = Minecraft.getInstance();
    private static Timer transitionTimer = null;
    private static double dynamicGammaTarget = Double.NaN;

    private GammaManager() {
    }

    public static double getGamma() {
        return Config.getGammaValue();
    }

    public static int getGammaPercentage() {
        return (int)Math.round(Config.getGammaValue() * 100);
    }

    public static void toggleGamma() {
        if (Config.DYNAMIC_GAMMA_ENABLED.get()) {
            toggleDynamicPause();
            return;
        }

        double newValue = Config.getGammaValue() == getDefaultValue() ? getToggledValue() : getDefaultValue();
        dynamicGammaTarget = Double.NaN;
        setGamma(newValue, true, true);
    }

    public static void toggleDynamicPause() {
        Config.setDynamicGammaPaused(!Config.isDynamicGammaPaused());
        InfoProvider.showDynamicGammaHudMessage();
        if (Config.isDynamicGammaPaused()) {
            dynamicGammaTarget = Double.NaN;
            setGamma(getDefaultValue(), true, false, true);
        }
    }

    public static void increaseGamma(double value) {
        double newValue = Config.getGammaValue();
        newValue += value == 0 ? getStepValue() : value;
        setGamma(newValue, false, true);
    }

    public static void decreaseGamma(double value) {
        double newValue = Config.getGammaValue();
        newValue -= value == 0 ? getStepValue() : value;
        setGamma(newValue, false, true);
    }

    public static void minGamma() {
        setGamma(getMinimumStrength(), true, true);
    }

    public static void maxGamma() {
        setGamma(getMaximumStrength(), true, true);
    }

    public static void setDimensionPreference() {
        if (client.level == null || !Config.GAMMA_DIMENSION_PREFERENCE_ENABLED.get()) {
            return;
        }

        net.minecraft.resources.ResourceKey<net.minecraft.world.level.Level> dimension = client.level.dimension();
        if (dimension.equals(net.minecraft.world.level.Level.OVERWORLD)) {
            setGamma(Config.GAMMA_OVERWORLD_PREFERENCE.get() / 100.0, false, false);
        }
        else if (dimension.equals(net.minecraft.world.level.Level.NETHER)) {
            setGamma(Config.GAMMA_NETHER_PREFERENCE.get() / 100.0, false, false);
        }
        else if (dimension.equals(net.minecraft.world.level.Level.END)) {
            setGamma(Config.GAMMA_END_PREFERENCE.get() / 100.0, false, false);
        }
    }

    public static void setDynamicGamma() {
        if (!Config.DYNAMIC_GAMMA_ENABLED.get() || Config.isDynamicGammaPaused()) {
            return;
        }

        double lightLevel = LightLevelUtil.getAverageLightLevel(
            Config.GAMMA_AVERAGING_LIGHT_RANGE.get(), 
            Config.GAMMA_SKY_BRIGHTNESS_OVERRIDE.get() / 100f
        );
        double step = (getMaxDynamicStrength() - getMinDynamicStrength()) / 15.0;
        double target = (getMinDynamicStrength() + step * (15 - lightLevel));
        if (dynamicGammaTarget != target) {
            dynamicGammaTarget = target;
            setGamma(target, true, false, true);
        }
    }

    public static void setGamma(double newValue, boolean smoothTransition, boolean showMessage) {
        if (Config.DYNAMIC_GAMMA_ENABLED.get()) {
            if (showMessage) {
                Component message = Component.translatable("text.gammatweaks.message.incompatibleWithDynamicGamma");
                InfoProvider.sendMessage(message);
            }
            return;
        }

        setGamma(newValue, smoothTransition, showMessage, false);
    }

    private static void setGamma(double newValue, boolean smoothTransition, boolean showMessage, boolean dynamic) {
        if (transitionTimer != null) {
            transitionTimer.cancel();
        }

        if (Config.GAMMA_LIMIT_CHECK.get() && Config.MAX_GAMMA.get() > Config.MIN_GAMMA.get()) {
            newValue = Math.clamp(newValue, getMinimumStrength(), getMaximumStrength());
        }

        if (smoothTransition && (Config.SMOOTH_GAMMA_TRANSITION.get() || dynamic)) {
            double valueChangePerTick = getTransitionSpeed(dynamic) / 100;
            if (newValue < Config.getGammaValue()) {
                valueChangePerTick *= -1;
            }
            startTransitionTimer(newValue, valueChangePerTick, showMessage);
        }
        else {
            Config.setGammaValue(newValue);
            StatusEffectManager.updateGammaStatusEffect();
            if (showMessage) {
                InfoProvider.showGammaHudMessage();
            }
        }

        if (Config.UPDATE_TOGGLE.get() && newValue != getDefaultValue()) {
            Config.TOGGLED_GAMMA.set((int)Math.round(newValue * 100));
        }
    }

    protected static void toggleDynamicGamma() {
        boolean newStatus = !Config.DYNAMIC_GAMMA_ENABLED.get();
        Config.DYNAMIC_GAMMA_ENABLED.set(newStatus);
        Component message = Component.translatable("text.gammatweaks.message.dynamicGamma" + (newStatus ? "On" : "Off"));
        InfoProvider.sendMessage(message);
    }

    protected static void toggleStatusEffect() {
        boolean newStatus = !Config.SHOW_GAMMA_STATUS_EFFECT.get();
        Config.SHOW_GAMMA_STATUS_EFFECT.set(newStatus);
        StatusEffectManager.updateGammaStatusEffect();
        Component message = Component.translatable("text.gammatweaks.message.statusEffectGamma" + (newStatus ? "On" : "Off"));
        InfoProvider.sendMessage(message);
    }

    protected static void toggleSmoothTransition() {
        boolean newStatus = !Config.SMOOTH_GAMMA_TRANSITION.get();
        Config.SMOOTH_GAMMA_TRANSITION.set(newStatus);
        Component message = Component.translatable("text.gammatweaks.message.transitionGamma" + (newStatus ? "On" : "Off"));
        InfoProvider.sendMessage(message);
    }

    private static void startTransitionTimer(double newValue, double valueChangePerTick, boolean showMessage) {
        transitionTimer = new Timer();
        transitionTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                double nextValue = Config.getGammaValue() + valueChangePerTick;
                if ((valueChangePerTick > 0 && nextValue >= newValue) ||
                        (valueChangePerTick < 0 && nextValue <= newValue)) {
                    transitionTimer.cancel();
                    Config.setGammaValue(newValue);
                }
                else {
                    Config.setGammaValue(nextValue);
                }

                StatusEffectManager.updateGammaStatusEffect();
                if (showMessage) {
                    InfoProvider.showGammaHudMessage();
                }
            }
        }, 0, 10);
    }

    private static double getDefaultValue() {
        return Config.DEFAULT_GAMMA.get() / 100.0;
    }

    private static double getToggledValue() {
        return Config.TOGGLED_GAMMA.get() / 100.0;
    }

    private static double getStepValue() {
        return Config.GAMMA_STEP.get() / 100.0;
    }

    private static double getMinimumStrength() {
        return Config.MIN_GAMMA.get() / 100.0;
    }

    private static double getMaximumStrength() {
        return Config.MAX_GAMMA.get() / 100.0;
    }

    private static double getMinDynamicStrength() {
        return Config.MIN_DYNAMIC_GAMMA.get() / 100.0;
    }

    private static double getMaxDynamicStrength() {
        return Config.MAX_DYNAMIC_GAMMA.get() / 100.0;
    }

    private static double getTransitionSpeed(boolean dynamic) {
        return (dynamic ? Config.DYNAMIC_GAMMA_TRANSITION_SPEED.get() : Config.GAMMA_TRANSITION_SPEED.get()) / 100.0;
    }
}
