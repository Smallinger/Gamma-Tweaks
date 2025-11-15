package com.github.smallinger.gammatweaks.mixin;

import com.github.smallinger.gammatweaks.statuseffect.GammaStatusEffect;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.MobEffectTextureManager;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MobEffectTextureManager.class)
public abstract class MixinMobEffectTextureManager {

    /**
     * Inject at head to provide a sprite for our synthetic gamma effects that bypass the registry.
     */
    @Inject(method = "get", at = @At("HEAD"), cancellable = true)
    private void gammatweaks$provideGammaSprite(Holder<MobEffect> effect, CallbackInfoReturnable<TextureAtlasSprite> cir) {
        if (effect.value() instanceof GammaStatusEffect gammaEffect) {
            TextureAtlasSprite sprite = ((TextureAtlasHolderAccessor) this).gammatweaks$invokeGetSprite(gammaEffect.getIdentifier());
            cir.setReturnValue(sprite);
        }
    }
}
