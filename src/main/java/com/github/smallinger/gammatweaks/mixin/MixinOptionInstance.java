package com.github.smallinger.gammatweaks.mixin;

import com.github.smallinger.gammatweaks.Config;
import net.minecraft.client.OptionInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(OptionInstance.class)
public class MixinOptionInstance<T> {
    @Shadow
    @Final
    Component caption;

    /**
     * Mixin to return the gamma value of this mod instead of the vanilla one
     */
    @Inject(method = "get", at = @At("HEAD"), cancellable = true)
    public void getModValue(CallbackInfoReturnable<T> cir) {
        if (isGammaOption()) {
            cir.setReturnValue((T) Double.valueOf(Config.getGammaValue()));
        }
    }

    /**
     * Mixin to set the gamma value of this mod instead of the vanilla one
     */
    @Inject(method = "set", at = @At("HEAD"), cancellable = true)
    public void setModValue(T value, CallbackInfo ci) {
        if (isGammaOption()) {
            Config.setGammaValue((Double) value);
            ci.cancel();
        }
    }

    @Unique
    private boolean isGammaOption() {
        if (caption.getContents() instanceof TranslatableContents translatableContents) {
            return translatableContents.getKey().equals("options.gamma");
        }
        return false;
    }
}
