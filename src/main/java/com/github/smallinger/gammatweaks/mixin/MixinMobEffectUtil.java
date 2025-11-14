package com.github.smallinger.gammatweaks.mixin;

import com.github.smallinger.gammatweaks.manager.GammaManager;
import com.github.smallinger.gammatweaks.manager.NightVisionManager;
import com.github.smallinger.gammatweaks.statuseffect.GammaStatusEffect;
import com.github.smallinger.gammatweaks.statuseffect.StatusEffectManager;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MobEffectUtil.class)
public class MixinMobEffectUtil {

    /**
     * Mixin to show the gamma or night vision percentage instead of the MobEffect duration
     */
    @Inject(method = "formatDuration", at = @At("HEAD"), cancellable = true)
    private static void getPercentageText(MobEffectInstance effect, float durationFactor, float ticksPerSecond, CallbackInfoReturnable<Component> info) {
        Holder<MobEffect> type = effect.getEffect();
        if (type.value() instanceof GammaStatusEffect) {
            int percentage = type.equals(StatusEffectManager.NIGHT_VISION)
                    ? NightVisionManager.getNightVisionPercentage()
                    : GammaManager.getGammaPercentage();

            info.setReturnValue(Component.literal(percentage + "%"));
        }
    }
}
