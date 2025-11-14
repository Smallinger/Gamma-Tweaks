package com.github.smallinger.gammatweaks;

import com.github.smallinger.gammatweaks.manager.GammaManager;
import com.github.smallinger.gammatweaks.manager.NightVisionManager;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.RegisterClientCommandsEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.tick.LevelTickEvent;
import com.github.smallinger.gammatweaks.keybindings.KeyBindings;

@Mod(value = GammaTweaks.MODID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = GammaTweaks.MODID, value = Dist.CLIENT)
public class GammaTweaksClient {
    public GammaTweaksClient(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
        NeoForge.EVENT_BUS.addListener(this::onLevelTick);
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        GammaTweaks.LOGGER.info("Gamma Tweaks Client Setup");
    }

    @SubscribeEvent
    public static void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
        KeyBindings.register(event);
    }

    @SubscribeEvent
    public static void onRegisterClientCommands(RegisterClientCommandsEvent event) {
        GammaTweaksCommands.registerCommands(event);
    }

    public void onLevelTick(LevelTickEvent.Pre event) {
        if (event.getLevel().isClientSide()) {
            GammaManager.setDynamicGamma();
            NightVisionManager.setDynamicNightVision();
            KeyBindings.handleKeyInputs();
        }
    }
}
