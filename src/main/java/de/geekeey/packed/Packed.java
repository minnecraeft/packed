package de.geekeey.packed;

import de.geekeey.packed.init.PackedEntities;
import de.geekeey.packed.init.PackedBlocks;
import de.geekeey.packed.init.PackedItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class Packed implements ModInitializer {

    public static final String ID = "packed";

    public static final ItemGroup ITEM_GROUP = FabricItemGroupBuilder.build(new Identifier(ID, ID), () -> new ItemStack(PackedItems.BARREL_DEFAULT_TIER.oak));

    public static Identifier id(String path) {
        return new Identifier(ID, path);
    }

    @Override
    public void onInitialize() {
        PackedEntities.register();
        PackedBlocks.register();
        PackedItems.register();
    }
}
