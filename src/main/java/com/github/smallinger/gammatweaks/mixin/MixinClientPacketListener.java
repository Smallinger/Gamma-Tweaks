package com.github.smallinger.gammatweaks.mixin;

import com.github.smallinger.gammatweaks.manager.GammaManager;
import com.github.smallinger.gammatweaks.manager.NightVisionManager;
import com.github.smallinger.gammatweaks.statuseffect.StatusEffectManager;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundPlayerAbilitiesPacket;
import net.minecraft.network.protocol.game.ClientboundRespawnPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public class MixinClientPacketListener {

    /**
     * Mixin to enable client side status effects on world load etc
     */
    @Inject(method = "handlePlayerAbilities", at = @At("RETURN"))
    private void onPlayerAbilities(ClientboundPlayerAbilitiesPacket packet, CallbackInfo ci) {
        StatusEffectManager.updateAllEffects();
        GammaManager.setDimensionPreference();
        NightVisionManager.setDimensionPreference();
    }

    /**
     * Mixin to make sure the client side status effects remain enabled after death
     */
    @Inject(method = "handleRespawn", at = @At("TAIL"))
    private void onPlayerRespawn(ClientboundRespawnPacket packet, CallbackInfo info) {
        StatusEffectManager.updateAllEffects();
    }
}
