package com.github.smallinger.gammatweaks;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    // === GAMMA SETTINGS ===
    static {
        BUILDER.push("gamma");
    }

    public static final ModConfigSpec.IntValue DEFAULT_GAMMA = BUILDER
            .comment("Default gamma value (0-1500)")
            .defineInRange("defaultGamma", 100, -750, 1500);

    public static final ModConfigSpec.IntValue TOGGLED_GAMMA = BUILDER
            .comment("Toggled gamma value (0-1500)")
            .defineInRange("toggledGamma", 1500, -750, 1500);

    public static final ModConfigSpec.BooleanValue UPDATE_TOGGLE = BUILDER
            .comment("Update toggle value when gamma is changed")
            .define("updateToggle", false);

    public static final ModConfigSpec.IntValue GAMMA_STEP = BUILDER
            .comment("Gamma step value for increase/decrease (0-100)")
            .defineInRange("gammaStep", 10, 0, 100);

    public static final ModConfigSpec.BooleanValue SHOW_GAMMA_STATUS_EFFECT = BUILDER
            .comment("Show gamma as status effect")
            .define("showGammaStatusEffect", false);

    public static final ModConfigSpec.BooleanValue RESET_GAMMA_ON_CLOSE = BUILDER
            .comment("Reset gamma when closing the game")
            .define("resetGammaOnClose", false);

    // Gamma Transition
    static {
        BUILDER.push("transition");
    }

    public static final ModConfigSpec.BooleanValue SMOOTH_GAMMA_TRANSITION = BUILDER
            .comment("Enable smooth gamma transition")
            .define("smoothGammaTransition", false);

    public static final ModConfigSpec.IntValue GAMMA_TRANSITION_SPEED = BUILDER
            .comment("Gamma transition speed (0-10000)")
            .defineInRange("gammaTransitionSpeed", 3000, 0, 10000);

    static {
        BUILDER.pop();
        BUILDER.push("dynamic");
    }

    // Dynamic Gamma
    public static final ModConfigSpec.BooleanValue DYNAMIC_GAMMA_ENABLED = BUILDER
            .comment("Enable dynamic gamma")
            .define("dynamicGammaEnabled", false);

    public static final ModConfigSpec.IntValue MIN_DYNAMIC_GAMMA = BUILDER
            .comment("Minimum dynamic gamma value (0-1500)")
            .defineInRange("minDynamicGamma", 100, -750, 1500);

    public static final ModConfigSpec.IntValue MAX_DYNAMIC_GAMMA = BUILDER
            .comment("Maximum dynamic gamma value (0-1500)")
            .defineInRange("maxDynamicGamma", 1000, -750, 1500);

    public static final ModConfigSpec.IntValue DYNAMIC_GAMMA_TRANSITION_SPEED = BUILDER
            .comment("Dynamic gamma transition speed (0-1000)")
            .defineInRange("dynamicGammaTransitionSpeed", 200, 0, 1000);

    public static final ModConfigSpec.IntValue GAMMA_AVERAGING_LIGHT_RANGE = BUILDER
            .comment("Gamma averaging light range (0-16)")
            .defineInRange("gammaAveragingLightRange", 8, 0, 16);

    public static final ModConfigSpec.IntValue GAMMA_SKY_BRIGHTNESS_OVERRIDE = BUILDER
            .comment("Gamma sky brightness override (0-100)")
            .defineInRange("gammaSkyBrightnessOverride", 0, 0, 100);

    static {
        BUILDER.pop();
        BUILDER.push("dimensionPreference");
    }

    // Gamma Dimension Preferences
    public static final ModConfigSpec.BooleanValue GAMMA_DIMENSION_PREFERENCE_ENABLED = BUILDER
            .comment("Enable dimension-specific gamma preferences")
            .define("gammaDimensionPreferenceEnabled", false);

    public static final ModConfigSpec.IntValue GAMMA_OVERWORLD_PREFERENCE = BUILDER
            .comment("Gamma preference for Overworld (0-1500)")
            .defineInRange("gammaOverworldPreference", 1500, -750, 1500);

    public static final ModConfigSpec.IntValue GAMMA_NETHER_PREFERENCE = BUILDER
            .comment("Gamma preference for Nether (0-1500)")
            .defineInRange("gammaNetherPreference", 1500, -750, 1500);

    public static final ModConfigSpec.IntValue GAMMA_END_PREFERENCE = BUILDER
            .comment("Gamma preference for End (0-1500)")
            .defineInRange("gammaEndPreference", 1500, -750, 1500);

    static {
        BUILDER.pop();
        BUILDER.push("limiter");
    }

    // Gamma Limiter
    public static final ModConfigSpec.BooleanValue GAMMA_LIMIT_CHECK = BUILDER
            .comment("Enable gamma limiter")
            .define("gammaLimitCheck", true);

    public static final ModConfigSpec.IntValue MIN_GAMMA = BUILDER
            .comment("Minimum gamma value (-750-1500)")
            .defineInRange("minGamma", -750, -750, 1500);

    public static final ModConfigSpec.IntValue MAX_GAMMA = BUILDER
            .comment("Maximum gamma value (-750-1500)")
            .defineInRange("maxGamma", 1500, -750, 1500);

    static {
        BUILDER.pop();
        BUILDER.push("hudMessage");
    }

    // Gamma HUD Message
    public static final ModConfigSpec.BooleanValue SHOW_GAMMA_HUD_MESSAGE = BUILDER
            .comment("Show gamma HUD message")
            .define("showGammaHudMessage", true);

    public static final ModConfigSpec.IntValue GAMMA_HUD_DEFAULT_COLOR = BUILDER
            .comment("Gamma HUD default color")
            .defineInRange("gammaHudDefaultColor", 43520, 0, 0xFFFFFF);

    public static final ModConfigSpec.IntValue GAMMA_HUD_POSITIVE_COLOR = BUILDER
            .comment("Gamma HUD positive color")
            .defineInRange("gammaHudPositiveColor", 0xFFAA00, 0, 0xFFFFFF);

    public static final ModConfigSpec.IntValue GAMMA_HUD_NEGATIVE_COLOR = BUILDER
            .comment("Gamma HUD negative color")
            .defineInRange("gammaHudNegativeColor", 0xAA0000, 0, 0xFFFFFF);

    static {
        BUILDER.pop(); // pop hudMessage
        BUILDER.pop(); // pop gamma
    }

    // === NIGHT VISION SETTINGS ===
    static {
        BUILDER.push("nightVision");
    }

    public static final ModConfigSpec.IntValue TOGGLED_NIGHT_VISION = BUILDER
            .comment("Toggled night vision value (0-100)")
            .defineInRange("toggledNightVision", 100, 0, 100);

    public static final ModConfigSpec.BooleanValue UPDATE_NV_TOGGLE = BUILDER
            .comment("Update night vision toggle value when changed")
            .define("updateNvToggle", false);

    public static final ModConfigSpec.IntValue NIGHT_VISION_STEP = BUILDER
            .comment("Night vision step value for increase/decrease (0-50)")
            .defineInRange("nightVisionStep", 2, 0, 50);

    public static final ModConfigSpec.BooleanValue BRIGHTEN_FOG_COLOR = BUILDER
            .comment("Brighten fog color with night vision")
            .define("brightenFogColor", true);

    public static final ModConfigSpec.BooleanValue SHOW_NV_STATUS_EFFECT = BUILDER
            .comment("Show night vision as status effect")
            .define("showNvStatusEffect", false);

    public static final ModConfigSpec.BooleanValue RESET_NV_ON_CLOSE = BUILDER
            .comment("Reset night vision when closing the game")
            .define("resetNvOnClose", false);

    static {
        BUILDER.push("transition");
    }

    // Night Vision Transition
    public static final ModConfigSpec.BooleanValue SMOOTH_NV_TRANSITION = BUILDER
            .comment("Enable smooth night vision transition")
            .define("smoothNvTransition", false);

    public static final ModConfigSpec.IntValue NV_TRANSITION_SPEED = BUILDER
            .comment("Night vision transition speed (0-1000)")
            .defineInRange("nvTransitionSpeed", 200, 0, 1000);

    static {
        BUILDER.pop();
        BUILDER.push("dynamic");
    }

    // Dynamic Night Vision
    public static final ModConfigSpec.BooleanValue DYNAMIC_NV_ENABLED = BUILDER
            .comment("Enable dynamic night vision")
            .define("dynamicNvEnabled", false);

    public static final ModConfigSpec.IntValue MIN_DYNAMIC_NV = BUILDER
            .comment("Minimum dynamic night vision value (0-100)")
            .defineInRange("minDynamicNv", 0, 0, 100);

    public static final ModConfigSpec.IntValue MAX_DYNAMIC_NV = BUILDER
            .comment("Maximum dynamic night vision value (0-100)")
            .defineInRange("maxDynamicNv", 100, 0, 100);

    public static final ModConfigSpec.IntValue DYNAMIC_NV_TRANSITION_SPEED = BUILDER
            .comment("Dynamic night vision transition speed (0-1000)")
            .defineInRange("dynamicNvTransitionSpeed", 200, 0, 1000);

    public static final ModConfigSpec.IntValue NV_AVERAGING_LIGHT_RANGE = BUILDER
            .comment("Night vision averaging light range (0-16)")
            .defineInRange("nvAveragingLightRange", 8, 0, 16);

    public static final ModConfigSpec.IntValue NV_SKY_BRIGHTNESS_OVERRIDE = BUILDER
            .comment("Night vision sky brightness override (0-100)")
            .defineInRange("nvSkyBrightnessOverride", 0, 0, 100);

    static {
        BUILDER.pop();
        BUILDER.push("dimensionPreference");
    }

    // Night Vision Dimension Preferences
    public static final ModConfigSpec.BooleanValue NV_DIMENSION_PREFERENCE_ENABLED = BUILDER
            .comment("Enable dimension-specific night vision preferences")
            .define("nvDimensionPreferenceEnabled", false);

    public static final ModConfigSpec.IntValue NV_OVERWORLD_PREFERENCE = BUILDER
            .comment("Night vision preference for Overworld (0-100)")
            .defineInRange("nvOverworldPreference", 100, 0, 100);

    public static final ModConfigSpec.IntValue NV_NETHER_PREFERENCE = BUILDER
            .comment("Night vision preference for Nether (0-100)")
            .defineInRange("nvNetherPreference", 100, 0, 100);

    public static final ModConfigSpec.IntValue NV_END_PREFERENCE = BUILDER
            .comment("Night vision preference for End (0-100)")
            .defineInRange("nvEndPreference", 100, 0, 100);

    static {
        BUILDER.pop();
        BUILDER.push("limiter");
    }

    // Night Vision Limiter
    public static final ModConfigSpec.BooleanValue NV_LIMIT_CHECK = BUILDER
            .comment("Enable night vision limiter")
            .define("nvLimitCheck", true);

    public static final ModConfigSpec.IntValue MIN_NIGHT_VISION = BUILDER
            .comment("Minimum night vision value (0-100)")
            .defineInRange("minNightVision", 0, 0, 100);

    public static final ModConfigSpec.IntValue MAX_NIGHT_VISION = BUILDER
            .comment("Maximum night vision value (0-100)")
            .defineInRange("maxNightVision", 100, 0, 100);

    static {
        BUILDER.pop();
        BUILDER.push("hudMessage");
    }

    // Night Vision HUD Message
    public static final ModConfigSpec.BooleanValue SHOW_NV_HUD_MESSAGE = BUILDER
            .comment("Show night vision HUD message")
            .define("showNvHudMessage", true);

    public static final ModConfigSpec.IntValue NV_HUD_ENABLED_COLOR = BUILDER
            .comment("Night vision HUD enabled color")
            .defineInRange("nvHudEnabledColor", 43520, 0, 0xFFFFFF);

    public static final ModConfigSpec.IntValue NV_HUD_DISABLED_COLOR = BUILDER
            .comment("Night vision HUD disabled color")
            .defineInRange("nvHudDisabledColor", 0xAA0000, 0, 0xFFFFFF);

    static {
        BUILDER.pop(); // pop hudMessage
        BUILDER.pop(); // pop nightVision
    }

    // === OTHER SETTINGS ===
    static {
        BUILDER.push("other");
    }

    public static final ModConfigSpec.BooleanValue NAMESPACED_COMMANDS_ENABLED = BUILDER
            .comment("Enable namespaced commands (gammatweaks:gamma instead of gamma)")
            .define("namespacedCommandsEnabled", false);

    static {
        BUILDER.pop(); // pop other
    }

    static final ModConfigSpec SPEC = BUILDER.build();

    // Runtime values
    private static double gammaValue = 1.0;
    private static boolean nightVisionEnabled = false;
    private static double nightVisionValue = 100.0;
    private static boolean dynamicGammaPaused = false;
    private static boolean dynamicNightVisionPaused = false;

    /**
     * Initialize runtime values from config defaults
     * Should be called after config is loaded
     */
    public static void initRuntimeValues() {
        gammaValue = DEFAULT_GAMMA.get() / 100.0;
        nightVisionValue = TOGGLED_NIGHT_VISION.get();
        nightVisionEnabled = false;
        dynamicGammaPaused = false;
        dynamicNightVisionPaused = false;
    }

    public static double getGammaValue() {
        return gammaValue;
    }

    public static void setGammaValue(double value) {
        gammaValue = value;
    }

    public static boolean isNightVisionEnabled() {
        return nightVisionEnabled;
    }

    public static void setNightVisionEnabled(boolean enabled) {
        nightVisionEnabled = enabled;
    }

    public static double getNightVisionValue() {
        return nightVisionValue;
    }

    public static void setNightVisionValue(double value) {
        nightVisionValue = value;
    }

    public static boolean isDynamicGammaPaused() {
        return dynamicGammaPaused;
    }

    public static void setDynamicGammaPaused(boolean paused) {
        dynamicGammaPaused = paused;
    }

    public static boolean isDynamicNightVisionPaused() {
        return dynamicNightVisionPaused;
    }

    public static void setDynamicNightVisionPaused(boolean paused) {
        dynamicNightVisionPaused = paused;
    }
}
