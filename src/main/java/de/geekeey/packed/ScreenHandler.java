package de.geekeey.packed;

import de.geekeey.packed.screen.BasicScreenHandler;
import de.geekeey.packed.screen.ExtendedGenericContainerScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerType;

public class ScreenHandler {
    public static final ScreenHandlerType<ExtendedGenericContainerScreenHandler> GENERIC3x18;
    public static final ScreenHandlerType<BasicScreenHandler> BASIC;

    static {
        GENERIC3x18 = ScreenHandlerRegistry.registerSimple(Initializer.id("generic"),(syncid,inv) -> ExtendedGenericContainerScreenHandler.create(syncid,inv,3,18));
        BASIC = ScreenHandlerRegistry.registerExtended(Initializer.id("basic"), BasicScreenHandler::new);

    }
}
