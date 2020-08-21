package de.geekeey.packed;

import de.geekeey.packed.block.VariantChestBlock;
import de.geekeey.packed.block.entity.VariantChestBlockEntity;
import de.geekeey.packed.client.GenericScreen;
import de.geekeey.packed.client.render.VariantCrateBlockEntityRenderer;
import de.geekeey.packed.client.render.VariantChestBlockEntityRenderer;
import de.geekeey.packed.init.PackedEntities;
import de.geekeey.packed.init.PackedItems;
import de.geekeey.packed.init.PackedScreenHandlers;
import de.geekeey.packed.init.helpers.*;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;

import java.util.function.Function;

@Environment(EnvType.CLIENT)
public class PackedClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ScreenRegistry.register(PackedScreenHandlers.GENERIC, GenericScreen::new);

        //registration of chest textures
        for (WoodVariants value : WoodVariants.values()) {
            ClientSpriteRegistryCallback.event(TexturedRenderLayers.CHEST_ATLAS_TEXTURE).register((atlasTexture, registry) -> registry.register(Packed.id("entity/chest/" + value.getIdentifier().getPath() + "/normal")));
            ClientSpriteRegistryCallback.event(TexturedRenderLayers.CHEST_ATLAS_TEXTURE).register((atlasTexture, registry) -> registry.register(Packed.id("entity/chest/" + value.getIdentifier().getPath() + "/normal_left")));
            ClientSpriteRegistryCallback.event(TexturedRenderLayers.CHEST_ATLAS_TEXTURE).register((atlasTexture, registry) -> registry.register(Packed.id("entity/chest/" + value.getIdentifier().getPath() + "/normal_right")));
        }

        //Registration of lock textures
        for (StorageTiers value : StorageTiers.values()) {
            ClientSpriteRegistryCallback.event(TexturedRenderLayers.CHEST_ATLAS_TEXTURE).register((atlasTexture, registry) -> registry.register(Packed.id("entity/chest/" + value.getIdentifier().getPath())));
        }

        BlockEntityRendererRegistry.INSTANCE.register(PackedEntities.CUSTOM_CHEST, dispatcher -> {
            register(PackedItems.CHEST_DEFAULT, dispatcher, variant -> new VariantChestBlockEntity(StorageTiers.DEFAULT, variant));
            register(PackedItems.CHEST_TIER_1, dispatcher, variant -> new VariantChestBlockEntity(StorageTiers.TIER_1, variant));
            register(PackedItems.CHEST_TIER_2, dispatcher, variant -> new VariantChestBlockEntity(StorageTiers.TIER_2, variant));
            register(PackedItems.CHEST_TIER_3, dispatcher, variant -> new VariantChestBlockEntity(StorageTiers.TIER_3, variant));
            return createDefaultRenderer(dispatcher);
        });


        BlockEntityRendererRegistry.INSTANCE.register(PackedEntities.CRATE, VariantCrateBlockEntityRenderer::new);

    }

    private static VariantChestBlockEntityRenderer createDefaultRenderer(BlockEntityRenderDispatcher dispatcher) {
        return new VariantChestBlockEntityRenderer(dispatcher, Blocks.CHEST);
    }

    private static void register(WoodItemVariants<StorageTier, VariantChestBlock> items, BlockEntityRenderDispatcher dispatcher, Function<WoodVariant, VariantChestBlockEntity> factory) {
        items.variants.forEach((variant, item) -> {
            Block block = item.getBlock();

            VariantChestBlockEntityRenderer renderer = new VariantChestBlockEntityRenderer(dispatcher, block);
            BuiltinItemRendererRegistry.INSTANCE.register(item, (stack, matrices, vertices, light, overlay) -> {
                renderer.render(factory.apply(variant), 0.0f, matrices, vertices, light, overlay);
            });
        });
    }

}
