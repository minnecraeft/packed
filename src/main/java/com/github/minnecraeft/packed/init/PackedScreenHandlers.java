package com.github.minnecraeft.packed.init;

import com.github.minnecraeft.packed.Packed;
import com.github.minnecraeft.packed.screen.ExtendedGenericContainerScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerType;

public class PackedScreenHandlers {
    public static final ScreenHandlerType<ExtendedGenericContainerScreenHandler> GENERIC;

    static {
        GENERIC = ScreenHandlerRegistry.registerExtended(Packed.identifier("generic"), ExtendedGenericContainerScreenHandler::create);
    }

    @SuppressWarnings("EmptyMethod")
    public static void register() {
        // keep so class will get called and initialized
    }

}
