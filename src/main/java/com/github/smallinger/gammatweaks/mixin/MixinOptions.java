package com.github.smallinger.gammatweaks.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Options.class)
public class MixinOptions<T> {

    /**
     * Mixin to skip reading and writing the options file for the gamma option
     */
    @WrapOperation(method = "processOptions", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Options$FieldAccess;process(Ljava/lang/String;Lnet/minecraft/client/OptionInstance;)V"))
    private void doNotVisitGamma(Options.FieldAccess instance, String key, OptionInstance<T> option, Operation<Void> original) {
        if (!key.equals("gamma")) {
            original.call(instance, key, option);
        }
    }
}
