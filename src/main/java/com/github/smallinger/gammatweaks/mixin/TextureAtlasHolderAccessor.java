package com.github.smallinger.gammatweaks.mixin;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.TextureAtlasHolder;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(TextureAtlasHolder.class)
public interface TextureAtlasHolderAccessor {
    @Invoker("getSprite")
    TextureAtlasSprite gammatweaks$invokeGetSprite(ResourceLocation location);
}
