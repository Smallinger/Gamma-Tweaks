package com.github.smallinger.gammatweaks.util;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LightLayer;

import java.util.ArrayList;
import java.util.List;

public class LightLevelUtil {
    private LightLevelUtil() {
    }

    private static final Minecraft client = Minecraft.getInstance();

    public static Double getAverageLightLevel(int range) {
        return getAverageLightLevel(range, 0);
    }

    public static Double getAverageLightLevel(int range, float skyBrightnessOverride) {
        if (client.level == null || client.player == null) {
            return 15.0;
        }

        BlockPos playerPos = client.player.blockPosition().above();
        List<Double> lightLevels = new ArrayList<>();
        lightLevels.add(getLightLevel(playerPos, skyBrightnessOverride));
        if (range > 0) {
            for (Direction direction : Direction.values()) {
                addLightLevelsInDirection(lightLevels, playerPos, direction, range, skyBrightnessOverride);
            }
        }

        return lightLevels.stream().mapToDouble(lvl -> lvl).average().orElse(15.0);
    }

    private static void addLightLevelsInDirection(List<Double> lightLevels, BlockPos blockPos, Direction direction, int range, float skyBrightnessOverride) {
        for (int i = 0; i < range; i++) {
            BlockPos offsetPos = blockPos.relative(direction, i);
            if(!client.level.getBlockState(offsetPos).isAir()) {
                break;
            }

            lightLevels.add(getLightLevel(offsetPos, skyBrightnessOverride));
        }
    }

    public static double getLightLevel(BlockPos blockPos, float skyBrightnessOverride) {
        if (client.level == null) {
            return 15.0;
        }

        int blockLight = client.level.getBrightness(LightLayer.BLOCK, blockPos);
        int skyLight = client.level.getBrightness(LightLayer.SKY, blockPos);
        float skyBrightness = Math.max(client.level.getTimeOfDay(1f), skyBrightnessOverride);
        return Math.max(blockLight, skyLight * skyBrightness);
    }
}
