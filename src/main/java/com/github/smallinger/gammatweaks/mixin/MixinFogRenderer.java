package com.github.smallinger.gammatweaks.mixin;

import com.github.smallinger.gammatweaks.Config;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(targets = "net.minecraft.client.renderer.fog.FogRenderer")
abstract class MixinFogRenderer {

    /**
     * Mixin to allow Night Vision fog color without Status Effect
     */
    @Redirect(method = "computeFogColor", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;hasEffect(Lnet/minecraft/core/Holder;)Z", ordinal = 0))
    private static boolean hasNightVision(LivingEntity entity, Holder<MobEffect> effect) {
        return (Config.BRIGHTEN_FOG_COLOR.get() && Config.isNightVisionEnabled()) || entity.hasEffect(effect);
    }
}
