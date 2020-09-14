package de.geekeey.packed.init;

import de.geekeey.packed.Packed;
import de.geekeey.packed.screen.ExtendedGenericContainerScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerType;

public class PackedScreenHandlers {
    public static final ScreenHandlerType<ExtendedGenericContainerScreenHandler> GENERIC;

    static {
        GENERIC = ScreenHandlerRegistry.registerExtended(Packed.id("generic"), ExtendedGenericContainerScreenHandler::create);
    }

    @SuppressWarnings("EmptyMethod")
    public static void register() {
        // keep so class will get called and initialized
    }

}
