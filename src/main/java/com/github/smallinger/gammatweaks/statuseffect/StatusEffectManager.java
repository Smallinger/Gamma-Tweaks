package com.github.smallinger.gammatweaks.statuseffect;

import com.github.smallinger.gammatweaks.Config;
import com.github.smallinger.gammatweaks.manager.GammaManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;

public class StatusEffectManager {
    private static final Minecraft client = Minecraft.getInstance();
    
    public static final Holder.Direct<MobEffect> BRIGHT = new Holder.Direct<>(
            new GammaStatusEffect("bright", MobEffectCategory.BENEFICIAL, 0xFFFFFF));
    public static final Holder.Direct<MobEffect> DIM = new Holder.Direct<>(
            new GammaStatusEffect("dim", MobEffectCategory.HARMFUL, 0x000000));
    public static final Holder.Direct<MobEffect> NIGHT_VISION = new Holder.Direct<>(
            new GammaStatusEffect("night_vision", MobEffectCategory.BENEFICIAL, 0x1F1FA1));

    private StatusEffectManager() {
    }

    public static void updateAllEffects() {
        updateGammaStatusEffect();
        updateNightVision();
    }

    public static void updateNightVision() {
        LocalPlayer player = client.player;
        if (player == null) {
            return;
        }

        if (Config.isNightVisionEnabled() && Config.SHOW_NV_STATUS_EFFECT.get()) {
            addPermEffect(player, NIGHT_VISION);
        } else {
            player.removeEffect(NIGHT_VISION);
        }
    }

    public static void updateGammaStatusEffect() {
        LocalPlayer player = client.player;
        if (player == null) {
            return;
        }

        if (Config.SHOW_GAMMA_STATUS_EFFECT.get()) {
            int gamma = GammaManager.getGammaPercentage();
            if (gamma > 100) {
                if (!player.hasEffect(BRIGHT)) {
                    player.removeEffect(DIM);
                    addPermEffect(player, BRIGHT);
                }
                return;
            } else if (gamma < 0) {
                if (!player.hasEffect(DIM)) {
                    player.removeEffect(BRIGHT);
                    addPermEffect(player, DIM);
                }
                return;
            }
        }
        player.removeEffect(DIM);
        player.removeEffect(BRIGHT);
    }

    private static void addPermEffect(LocalPlayer player, Holder<MobEffect> effect) {
        MobEffectInstance statusEffect = new MobEffectInstance(effect, -1, 0, false, false, true);
        player.addEffect(statusEffect);
    }
}
