package com.github.smallinger.gammatweaks.mixin;

import com.github.smallinger.gammatweaks.Config;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(GameRenderer.class)
abstract class MixinGameRenderer {

    /**
     * Mixin to adjust the night vision strength
     */
    @WrapMethod(method = "getNightVisionScale")
    private static float adjustNightVisionStrength(LivingEntity entity, float tickProgress, Operation<Float> original) {
        float strength;

        if (Config.isNightVisionEnabled() || Config.DYNAMIC_NV_ENABLED.get()) {
            strength = (float) (Config.getNightVisionValue() / 100.0);
        }
        else {
            strength = original.call(entity, tickProgress);
        }

        return strength;
    }
}
