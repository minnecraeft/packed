package de.geekeey.packed.registry;

import de.geekeey.packed.initialisers.Initializer;
import de.geekeey.packed.screen.ExtendedGenericContainerScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerType;

public class ScreenHandler {
    public static final ScreenHandlerType<ExtendedGenericContainerScreenHandler> GENERIC;

    static {
        GENERIC = ScreenHandlerRegistry.registerSimple(Initializer.id("generic"),(syncid, inv) -> ExtendedGenericContainerScreenHandler.create(syncid,inv,3,18));
    }
}
