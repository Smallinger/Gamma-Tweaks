package com.github.smallinger.gammatweaks.util;

import com.github.smallinger.gammatweaks.GammaTweaks;
import java.lang.reflect.Method;

public final class IrisIntegration {
    private static Boolean irisLoaded = null;
    private static Class<?> irisApiClass = null;
    private static Method getInstanceMethod = null;
    private static Method isShaderPackInUseMethod = null;

    private IrisIntegration() {
    }

    /**
     * Checks if Iris is loaded and has an active shader pack.
     * @return true if a shader pack is currently in use, false otherwise
     */
    public static boolean isShaderPackActive() {
        if (irisLoaded == null) {
            try {
                irisApiClass = Class.forName("net.irisshaders.iris.api.v0.IrisApi");
                getInstanceMethod = irisApiClass.getMethod("getInstance");
                isShaderPackInUseMethod = irisApiClass.getMethod("isShaderPackInUse");
                irisLoaded = true;
                GammaTweaks.LOGGER.info("Iris detected, shader compatibility checks enabled");
            } catch (ClassNotFoundException | NoSuchMethodException e) {
                irisLoaded = false;
                GammaTweaks.LOGGER.debug("Iris not detected");
            }
        }

        if (!irisLoaded) {
            return false;
        }

        try {
            Object apiInstance = getInstanceMethod.invoke(null);
            return (Boolean) isShaderPackInUseMethod.invoke(apiInstance);
        } catch (Exception e) {
            GammaTweaks.LOGGER.error("Error checking Iris shader status", e);
            return false;
        }
    }
}
