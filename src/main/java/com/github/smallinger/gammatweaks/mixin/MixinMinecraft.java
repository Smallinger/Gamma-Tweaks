package com.github.smallinger.gammatweaks.mixin;

import com.github.smallinger.gammatweaks.Config;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MixinMinecraft {

    /**
     * Mixin to reset gamma and night vision when closing the game if configured
     */
    @Inject(method = "close", at = @At("HEAD"))
    private void resetOnClose(CallbackInfo ci) {
        if (Config.RESET_GAMMA_ON_CLOSE.get()) {
            Config.setGammaValue(Config.DEFAULT_GAMMA.get() / 100.0);
        }

        if (Config.RESET_NV_ON_CLOSE.get()) {
            Config.setNightVisionEnabled(false);
        }
    }
}
