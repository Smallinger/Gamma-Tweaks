package com.github.smallinger.gammatweaks.mixin;

import com.github.smallinger.gammatweaks.statuseffect.GammaStatusEffect;
import net.minecraft.client.gui.Gui;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Gui.class)
public class MixinGui {

    /**
     * Mixin to provide the gamma StatusEffect Identifier, is needed because they aren't actually registered
     */
    @Inject(method = "getMobEffectSprite", at = @At("HEAD"), cancellable = true)
    private static void getGammaTexture(Holder<MobEffect> effect, CallbackInfoReturnable<ResourceLocation> cir) {
        if (effect.value() instanceof GammaStatusEffect gammaEffect) {
            cir.setReturnValue(gammaEffect.getIdentifier());
        }
    }
}
