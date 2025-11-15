package com.github.smallinger.gammatweaks.keybindings;

import com.github.smallinger.gammatweaks.manager.GammaManager;
import com.github.smallinger.gammatweaks.manager.NightVisionManager;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import net.neoforged.neoforge.client.settings.KeyModifier;
import org.lwjgl.glfw.GLFW;

import static com.github.smallinger.gammatweaks.GammaTweaks.MODID;

public class KeyBindings {
    
        private static final String GAMMA_CATEGORY = "key.categories." + MODID + ".gamma";
        private static final String NIGHT_VISION_CATEGORY = "key.categories." + MODID + ".nightvision";

    public static KeyMapping GAMMA_TOGGLE;
    public static KeyMapping GAMMA_INCREASE;
    public static KeyMapping GAMMA_DECREASE;
    public static KeyMapping GAMMA_MAX;
    public static KeyMapping GAMMA_MIN;
    public static KeyMapping NIGHT_VISION_TOGGLE;
    public static KeyMapping NIGHT_VISION_INCREASE;
    public static KeyMapping NIGHT_VISION_DECREASE;

    public static void register(RegisterKeyMappingsEvent event) {
        GAMMA_TOGGLE = new KeyMapping(
                "key.gammatweaks.gammaToggle",
                KeyConflictContext.IN_GAME,
                KeyModifier.NONE,
                InputConstants.Type.KEYSYM.getOrCreate(GLFW.GLFW_KEY_G),
                GAMMA_CATEGORY
        );
        event.register(GAMMA_TOGGLE);

        GAMMA_INCREASE = new KeyMapping(
                "key.gammatweaks.increaseGamma",
                KeyConflictContext.IN_GAME,
                KeyModifier.NONE,
                InputConstants.Type.KEYSYM.getOrCreate(GLFW.GLFW_KEY_UP),
                GAMMA_CATEGORY
        );
        event.register(GAMMA_INCREASE);

        GAMMA_DECREASE = new KeyMapping(
                "key.gammatweaks.decreaseGamma",
                KeyConflictContext.IN_GAME,
                KeyModifier.NONE,
                InputConstants.Type.KEYSYM.getOrCreate(GLFW.GLFW_KEY_DOWN),
                GAMMA_CATEGORY
        );
        event.register(GAMMA_DECREASE);

        GAMMA_MAX = new KeyMapping(
                "key.gammatweaks.maxGamma",
                KeyConflictContext.IN_GAME,
                KeyModifier.NONE,
                InputConstants.Type.KEYSYM.getOrCreate(GLFW.GLFW_KEY_UNKNOWN),
                GAMMA_CATEGORY
        );
        event.register(GAMMA_MAX);

        GAMMA_MIN = new KeyMapping(
                "key.gammatweaks.minGamma",
                KeyConflictContext.IN_GAME,
                KeyModifier.NONE,
                InputConstants.Type.KEYSYM.getOrCreate(GLFW.GLFW_KEY_UNKNOWN),
                GAMMA_CATEGORY
        );
        event.register(GAMMA_MIN);

        NIGHT_VISION_TOGGLE = new KeyMapping(
                "key.gammatweaks.nightVisionToggle",
                KeyConflictContext.IN_GAME,
                KeyModifier.NONE,
                InputConstants.Type.KEYSYM.getOrCreate(GLFW.GLFW_KEY_H),
                NIGHT_VISION_CATEGORY
        );
        event.register(NIGHT_VISION_TOGGLE);

        NIGHT_VISION_INCREASE = new KeyMapping(
                "key.gammatweaks.increaseNightVision",
                KeyConflictContext.IN_GAME,
                KeyModifier.NONE,
                InputConstants.Type.KEYSYM.getOrCreate(GLFW.GLFW_KEY_RIGHT),
                NIGHT_VISION_CATEGORY
        );
        event.register(NIGHT_VISION_INCREASE);

        NIGHT_VISION_DECREASE = new KeyMapping(
                "key.gammatweaks.decreaseNightVision",
                KeyConflictContext.IN_GAME,
                KeyModifier.NONE,
                InputConstants.Type.KEYSYM.getOrCreate(GLFW.GLFW_KEY_LEFT),
                NIGHT_VISION_CATEGORY
        );
        event.register(NIGHT_VISION_DECREASE);
    }

    public static void handleKeyInputs() {
        if (GAMMA_TOGGLE == null) return;
        
        while (GAMMA_TOGGLE.consumeClick()) {
            GammaManager.toggleGamma();
        }

        while (GAMMA_INCREASE.consumeClick()) {
            GammaManager.increaseGamma(0);
        }

        while (GAMMA_DECREASE.consumeClick()) {
            GammaManager.decreaseGamma(0);
        }

        while (GAMMA_MAX.consumeClick()) {
            GammaManager.maxGamma();
        }

        while (GAMMA_MIN.consumeClick()) {
            GammaManager.minGamma();
        }

        while (NIGHT_VISION_TOGGLE.consumeClick()) {
            NightVisionManager.toggleNightVision();
        }

        while (NIGHT_VISION_INCREASE.consumeClick()) {
            NightVisionManager.increaseNightVision(0);
        }

        while (NIGHT_VISION_DECREASE.consumeClick()) {
            NightVisionManager.decreaseNightVision(0);
        }
    }
}
