package com.github.smallinger.gammatweaks.statuseffect;

import com.github.smallinger.gammatweaks.GammaTweaks;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class GammaStatusEffect extends MobEffect {
    private final String key;

    public GammaStatusEffect(String key, MobEffectCategory category, int color) {
        super(category, color);
        this.key = key;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return false;
    }

    public ResourceLocation getIdentifier() {
        return ResourceLocation.fromNamespaceAndPath(GammaTweaks.MODID, "mob_effect/" + key);
    }
}
