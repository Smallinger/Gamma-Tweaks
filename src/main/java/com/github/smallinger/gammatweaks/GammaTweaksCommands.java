package com.github.smallinger.gammatweaks;

import com.github.smallinger.gammatweaks.manager.GammaManager;
import com.github.smallinger.gammatweaks.manager.NightVisionManager;
import com.github.smallinger.gammatweaks.statuseffect.StatusEffectManager;
import com.github.smallinger.gammatweaks.util.InfoProvider;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.client.event.RegisterClientCommandsEvent;

public class GammaTweaksCommands {
    private GammaTweaksCommands() {
    }

    public static void registerCommands(RegisterClientCommandsEvent event) {
        registerGammaCommands(event.getDispatcher());
        registerNightVisionCommands(event.getDispatcher());
    }

    private static void registerGammaCommands(CommandDispatcher<CommandSourceStack> dispatcher) {
        String commandName = Config.NAMESPACED_COMMANDS_ENABLED.get() 
            ? "gammatweaks:gamma" 
            : "gamma";

        dispatcher.register(Commands.literal(commandName)
            .executes(ctx -> {
                GammaManager.toggleGamma();
                return 1;
            })
            .then(Commands.argument("value", IntegerArgumentType.integer())
                .executes(ctx -> {
                    int value = IntegerArgumentType.getInteger(ctx, "value");
                    GammaManager.setGamma(value / 100.0, true, true);
                    return 1;
                }))
            .then(Commands.literal("toggle")
                .executes(ctx -> {
                    GammaManager.toggleGamma();
                    return 1;
                }))
            .then(Commands.literal("min")
                .executes(ctx -> {
                    GammaManager.minGamma();
                    return 1;
                }))
            .then(Commands.literal("max")
                .executes(ctx -> {
                    GammaManager.maxGamma();
                    return 1;
                }))
            .then(Commands.literal("set")
                .then(Commands.argument("value", IntegerArgumentType.integer())
                    .executes(ctx -> {
                        int value = IntegerArgumentType.getInteger(ctx, "value");
                        GammaManager.setGamma(value / 100.0, true, true);
                        return 1;
                    })))
            .then(Commands.literal("increase")
                .executes(ctx -> {
                    GammaManager.increaseGamma(0);
                    return 1;
                })
                .then(Commands.argument("value", IntegerArgumentType.integer())
                    .executes(ctx -> {
                        int value = IntegerArgumentType.getInteger(ctx, "value");
                        GammaManager.increaseGamma(value / 100.0);
                        return 1;
                    })))
            .then(Commands.literal("decrease")
                .executes(ctx -> {
                    GammaManager.decreaseGamma(0);
                    return 1;
                })
                .then(Commands.argument("value", IntegerArgumentType.integer())
                    .executes(ctx -> {
                        int value = IntegerArgumentType.getInteger(ctx, "value");
                        GammaManager.decreaseGamma(value / 100.0);
                        return 1;
                    })))
            .then(Commands.literal("dynamic")
                .executes(ctx -> {
                    toggleDynamicGamma();
                    return 1;
                })
                .then(Commands.literal("toggle")
                    .executes(ctx -> {
                        toggleDynamicGamma();
                        return 1;
                    }))
                .then(Commands.literal("enable")
                    .executes(ctx -> {
                        Config.DYNAMIC_GAMMA_ENABLED.set(true);
                        InfoProvider.sendMessage(Component.translatable("text.gammatweaks.message.dynamicGammaOn"));
                        return 1;
                    }))
                .then(Commands.literal("disable")
                    .executes(ctx -> {
                        Config.DYNAMIC_GAMMA_ENABLED.set(false);
                        InfoProvider.sendMessage(Component.translatable("text.gammatweaks.message.dynamicGammaOff"));
                        return 1;
                    })))
            .then(Commands.literal("statuseffect")
                .executes(ctx -> {
                    toggleGammaStatusEffect();
                    return 1;
                })
                .then(Commands.literal("toggle")
                    .executes(ctx -> {
                        toggleGammaStatusEffect();
                        return 1;
                    }))
                .then(Commands.literal("enable")
                    .executes(ctx -> {
                        Config.SHOW_GAMMA_STATUS_EFFECT.set(true);
                        StatusEffectManager.updateGammaStatusEffect();
                        InfoProvider.sendMessage(Component.translatable("text.gammatweaks.message.statusEffectGammaOn"));
                        return 1;
                    }))
                .then(Commands.literal("disable")
                    .executes(ctx -> {
                        Config.SHOW_GAMMA_STATUS_EFFECT.set(false);
                        StatusEffectManager.updateGammaStatusEffect();
                        InfoProvider.sendMessage(Component.translatable("text.gammatweaks.message.statusEffectGammaOff"));
                        return 1;
                    })))
            .then(Commands.literal("transition")
                .executes(ctx -> {
                    toggleGammaSmoothTransition();
                    return 1;
                })
                .then(Commands.literal("toggle")
                    .executes(ctx -> {
                        toggleGammaSmoothTransition();
                        return 1;
                    }))
                .then(Commands.literal("smooth")
                    .executes(ctx -> {
                        Config.SMOOTH_GAMMA_TRANSITION.set(true);
                        InfoProvider.sendMessage(Component.translatable("text.gammatweaks.message.transitionGammaOn"));
                        return 1;
                    }))
                .then(Commands.literal("none")
                    .executes(ctx -> {
                        Config.SMOOTH_GAMMA_TRANSITION.set(false);
                        InfoProvider.sendMessage(Component.translatable("text.gammatweaks.message.transitionGammaOff"));
                        return 1;
                    }))));
    }

    private static void registerNightVisionCommands(CommandDispatcher<CommandSourceStack> dispatcher) {
        String commandName = Config.NAMESPACED_COMMANDS_ENABLED.get() 
            ? "gammatweaks:nightvision" 
            : "nightvision";

        dispatcher.register(Commands.literal(commandName)
            .executes(ctx -> {
                NightVisionManager.toggleNightVision();
                return 1;
            })
            .then(Commands.literal("enable")
                .executes(ctx -> {
                    NightVisionManager.enableNightVision();
                    return 1;
                }))
            .then(Commands.literal("disable")
                .executes(ctx -> {
                    NightVisionManager.disableNightVision();
                    return 1;
                }))
            .then(Commands.argument("value", IntegerArgumentType.integer())
                .executes(ctx -> {
                    int value = IntegerArgumentType.getInteger(ctx, "value");
                    NightVisionManager.enableAndOrSetNightVision(value);
                    return 1;
                }))
            .then(Commands.literal("toggle")
                .executes(ctx -> {
                    NightVisionManager.toggleNightVision();
                    return 1;
                }))
            .then(Commands.literal("set")
                .then(Commands.argument("value", IntegerArgumentType.integer())
                    .executes(ctx -> {
                        int value = IntegerArgumentType.getInteger(ctx, "value");
                        NightVisionManager.enableAndOrSetNightVision(value);
                        return 1;
                    })))
            .then(Commands.literal("increase")
                .executes(ctx -> {
                    NightVisionManager.increaseNightVision(0);
                    return 1;
                })
                .then(Commands.argument("value", IntegerArgumentType.integer())
                    .executes(ctx -> {
                        int value = IntegerArgumentType.getInteger(ctx, "value");
                        NightVisionManager.increaseNightVision(value);
                        return 1;
                    })))
            .then(Commands.literal("decrease")
                .executes(ctx -> {
                    NightVisionManager.decreaseNightVision(0);
                    return 1;
                })
                .then(Commands.argument("value", IntegerArgumentType.integer())
                    .executes(ctx -> {
                        int value = IntegerArgumentType.getInteger(ctx, "value");
                        NightVisionManager.decreaseNightVision(value);
                        return 1;
                    })))
            .then(Commands.literal("dynamic")
                .executes(ctx -> {
                    toggleDynamicNightVision();
                    return 1;
                })
                .then(Commands.literal("toggle")
                    .executes(ctx -> {
                        toggleDynamicNightVision();
                        return 1;
                    }))
                .then(Commands.literal("enable")
                    .executes(ctx -> {
                        Config.DYNAMIC_NV_ENABLED.set(true);
                        InfoProvider.sendMessage(Component.translatable("text.gammatweaks.message.dynamicNightVisionOn"));
                        return 1;
                    }))
                .then(Commands.literal("disable")
                    .executes(ctx -> {
                        Config.DYNAMIC_NV_ENABLED.set(false);
                        InfoProvider.sendMessage(Component.translatable("text.gammatweaks.message.dynamicNightVisionOff"));
                        return 1;
                    })))
            .then(Commands.literal("statuseffect")
                .executes(ctx -> {
                    toggleNightVisionStatusEffect();
                    return 1;
                })
                .then(Commands.literal("toggle")
                    .executes(ctx -> {
                        toggleNightVisionStatusEffect();
                        return 1;
                    }))
                .then(Commands.literal("enable")
                    .executes(ctx -> {
                        Config.SHOW_NV_STATUS_EFFECT.set(true);
                        StatusEffectManager.updateNightVision();
                        InfoProvider.sendMessage(Component.translatable("text.gammatweaks.message.statusEffectNightVisionOn"));
                        return 1;
                    }))
                .then(Commands.literal("disable")
                    .executes(ctx -> {
                        Config.SHOW_NV_STATUS_EFFECT.set(false);
                        StatusEffectManager.updateNightVision();
                        InfoProvider.sendMessage(Component.translatable("text.gammatweaks.message.statusEffectNightVisionOff"));
                        return 1;
                    })))
            .then(Commands.literal("transition")
                .executes(ctx -> {
                    toggleNightVisionSmoothTransition();
                    return 1;
                })
                .then(Commands.literal("toggle")
                    .executes(ctx -> {
                        toggleNightVisionSmoothTransition();
                        return 1;
                    }))
                .then(Commands.literal("smooth")
                    .executes(ctx -> {
                        Config.SMOOTH_NV_TRANSITION.set(true);
                        InfoProvider.sendMessage(Component.translatable("text.gammatweaks.message.transitionNightVisionOn"));
                        return 1;
                    }))
                .then(Commands.literal("none")
                    .executes(ctx -> {
                        Config.SMOOTH_NV_TRANSITION.set(false);
                        InfoProvider.sendMessage(Component.translatable("text.gammatweaks.message.transitionNightVisionOff"));
                        return 1;
                    }))));
    }

    // Helper methods to access protected manager methods
    private static void toggleDynamicGamma() {
        boolean newValue = !Config.DYNAMIC_GAMMA_ENABLED.get();
        Config.DYNAMIC_GAMMA_ENABLED.set(newValue);
        InfoProvider.sendMessage(Component.translatable(
            newValue ? "text.gammatweaks.message.dynamicGammaOn" : "text.gammatweaks.message.dynamicGammaOff"));
    }

    private static void toggleGammaStatusEffect() {
        boolean newValue = !Config.SHOW_GAMMA_STATUS_EFFECT.get();
        Config.SHOW_GAMMA_STATUS_EFFECT.set(newValue);
        StatusEffectManager.updateGammaStatusEffect();
        InfoProvider.sendMessage(Component.translatable(
            newValue ? "text.gammatweaks.message.statusEffectGammaOn" : "text.gammatweaks.message.statusEffectGammaOff"));
    }

    private static void toggleGammaSmoothTransition() {
        boolean newValue = !Config.SMOOTH_GAMMA_TRANSITION.get();
        Config.SMOOTH_GAMMA_TRANSITION.set(newValue);
        InfoProvider.sendMessage(Component.translatable(
            newValue ? "text.gammatweaks.message.transitionGammaOn" : "text.gammatweaks.message.transitionGammaOff"));
    }

    private static void toggleDynamicNightVision() {
        boolean newValue = !Config.DYNAMIC_NV_ENABLED.get();
        Config.DYNAMIC_NV_ENABLED.set(newValue);
        InfoProvider.sendMessage(Component.translatable(
            newValue ? "text.gammatweaks.message.dynamicNightVisionOn" : "text.gammatweaks.message.dynamicNightVisionOff"));
    }

    private static void toggleNightVisionStatusEffect() {
        boolean newValue = !Config.SHOW_NV_STATUS_EFFECT.get();
        Config.SHOW_NV_STATUS_EFFECT.set(newValue);
        StatusEffectManager.updateNightVision();
        InfoProvider.sendMessage(Component.translatable(
            newValue ? "text.gammatweaks.message.statusEffectNightVisionOn" : "text.gammatweaks.message.statusEffectNightVisionOff"));
    }

    private static void toggleNightVisionSmoothTransition() {
        boolean newValue = !Config.SMOOTH_NV_TRANSITION.get();
        Config.SMOOTH_NV_TRANSITION.set(newValue);
        InfoProvider.sendMessage(Component.translatable(
            newValue ? "text.gammatweaks.message.transitionNightVisionOn" : "text.gammatweaks.message.transitionNightVisionOff"));
    }
}
