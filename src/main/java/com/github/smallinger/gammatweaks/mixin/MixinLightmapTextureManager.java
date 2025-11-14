package com.github.smallinger.gammatweaks.mixin;

import com.github.smallinger.gammatweaks.Config;
import com.github.smallinger.gammatweaks.manager.GammaManager;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LightTexture.class)
abstract class MixinLightmapTextureManager {

    /**
     * Mixin needed to allow negative gamma
     */
    @ModifyExpressionValue(method = "updateLightTexture", at = @At(value = "INVOKE", target = "Ljava/lang/Math;max(FF)F", ordinal = 0))
    private float allowNegativeGamma(float original) {
        float gamma = (float) GammaManager.getGamma();
        if (gamma < 0) {
            return gamma;
        }

        return original;
    }

    /**
     * Mixin to allow Night Vision without Status Effect
     */
    @Redirect(method = "updateLightTexture", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;hasEffect(Lnet/minecraft/core/Holder;)Z", ordinal = 0))
    private boolean hasNightVision(LocalPlayer player, Holder<MobEffect> effect) {
        return Config.isNightVisionEnabled() || Config.DYNAMIC_NV_ENABLED.get() || player.hasEffect(effect);
    }
}
