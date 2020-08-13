package de.geekeey.packed;

import de.geekeey.packed.block.CustomChest;
import de.geekeey.packed.block.entity.CustomChestEntity;
import de.geekeey.packed.client.GenericScreen;
import de.geekeey.packed.client.render.CustomChestEntityRenderer;
import de.geekeey.packed.client.render.StorageBarrelEntityRenderer;
import de.geekeey.packed.init.PackedEntities;
import de.geekeey.packed.init.PackedItems;
import de.geekeey.packed.init.PackedScreenHandlers;
import de.geekeey.packed.init.helpers.*;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
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
            ClientSpriteRegistryCallback.event(TexturedRenderLayers.CHEST_ATLAS_TEXTURE).register((atlasTexture, registry) -> registry.register(Packed.id("entity/chest/" + value.identifier() + "/normal")));
            ClientSpriteRegistryCallback.event(TexturedRenderLayers.CHEST_ATLAS_TEXTURE).register((atlasTexture, registry) -> registry.register(Packed.id("entity/chest/" + value.identifier() + "/normal_left")));
            ClientSpriteRegistryCallback.event(TexturedRenderLayers.CHEST_ATLAS_TEXTURE).register((atlasTexture, registry) -> registry.register(Packed.id("entity/chest/" + value.identifier() + "/normal_right")));
        }

        //Registration of lock textures
        for (ChestTiers value : ChestTiers.values()) {
            ClientSpriteRegistryCallback.event(TexturedRenderLayers.CHEST_ATLAS_TEXTURE).register((atlasTexture, registry) -> registry.register(Packed.id("entity/chest/" + value.identifier().getPath())));
        }

        BlockEntityRendererRegistry.INSTANCE.register(PackedEntities.CUSTOM_CHEST, PackedClient::createDefaultRenderer);
        register(PackedItems.CHEST_DEFAULT_TIER, variant -> new CustomChestEntity(ChestTiers.DEFAULT, variant));
        register(PackedItems.CHEST_TIER_1, variant -> new CustomChestEntity(ChestTiers.TIER_1, variant));
        register(PackedItems.CHEST_TIER_2, variant -> new CustomChestEntity(ChestTiers.TIER_2, variant));
        register(PackedItems.CHEST_TIER_3, variant -> new CustomChestEntity(ChestTiers.TIER_3, variant));

        BlockEntityRendererRegistry.INSTANCE.register(PackedEntities.STORAGE_BARREL_ENTITY, StorageBarrelEntityRenderer::new);

    }

    private static CustomChestEntityRenderer createDefaultRenderer(BlockEntityRenderDispatcher dispatcher) {
        return new CustomChestEntityRenderer(dispatcher, Blocks.CHEST);
    }

    private static void register(WoodItemVariants<ChestTier, CustomChest> items, Function<WoodVariant, CustomChestEntity> factory) {
        items.variants.forEach((variant, item) -> {
            Block block = item.getBlock();
            CustomChestEntityRenderer renderer = new CustomChestEntityRenderer(BlockEntityRenderDispatcher.INSTANCE, block);
            BuiltinItemRendererRegistry.INSTANCE.register(item, (stack, matrices, vertices, light, overlay) -> {
                renderer.render(factory.apply(variant), 0.0f, matrices, vertices, light, overlay);
            });
        });
    }

}
