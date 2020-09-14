package de.geekeey.packed.init;

import de.geekeey.packed.Packed;
import de.geekeey.packed.block.VariantBarrelBlock;
import de.geekeey.packed.block.VariantChestBlock;
import de.geekeey.packed.block.VariantCrateBlock;
import de.geekeey.packed.init.helpers.*;
import de.geekeey.packed.item.StorageLocker;
import de.geekeey.packed.item.StorageUpgrader;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.registry.Registry;

public class PackedItems {

    public static final ItemGroup ITEM_GROUP;

    public static final WoodItemVariants<StorageTier, VariantBarrelBlock> BARREL_DEFAULT;
    public static final WoodItemVariants<StorageTier, VariantBarrelBlock> BARREL_TIER_1;
    public static final WoodItemVariants<StorageTier, VariantBarrelBlock> BARREL_TIER_2;
    public static final WoodItemVariants<StorageTier, VariantBarrelBlock> BARREL_TIER_3;

    public static final WoodItemVariants<StorageTier, VariantChestBlock> CHEST_DEFAULT;
    public static final WoodItemVariants<StorageTier, VariantChestBlock> CHEST_TIER_1;
    public static final WoodItemVariants<StorageTier, VariantChestBlock> CHEST_TIER_2;
    public static final WoodItemVariants<StorageTier, VariantChestBlock> CHEST_TIER_3;

    public static final WoodItemVariants<StorageTier, VariantCrateBlock> CRATE_DEFAULT;
    public static final WoodItemVariants<StorageTier, VariantCrateBlock> CRATE_TIER_1;
    public static final WoodItemVariants<StorageTier, VariantCrateBlock> CRATE_TIER_2;
    public static final WoodItemVariants<StorageTier, VariantCrateBlock> CRATE_TIER_3;

    public static final StorageUpgrader STORAGE_UPGRADER_DEFAULT;
    public static final StorageUpgrader STORAGE_UPGRADER_TIER1;
    public static final StorageUpgrader STORAGE_UPGRADER_TIER2;

    public static final StorageLocker STORAGE_LOCKER;

    static {
        BARREL_DEFAULT = new WoodItemVariants<>(PackedBlocks::barrel, PackedBlocks.BARREL_DEFAULT);
        BARREL_TIER_1 = new WoodItemVariants<>(PackedBlocks::barrel, PackedBlocks.BARREL_TIER_1);
        BARREL_TIER_2 = new WoodItemVariants<>(PackedBlocks::barrel, PackedBlocks.BARREL_TIER_2);
        BARREL_TIER_3 = new WoodItemVariants<>(PackedBlocks::barrel, PackedBlocks.BARREL_TIER_3);

        CHEST_DEFAULT = new WoodItemVariants<>(PackedBlocks::chest, PackedBlocks.CHEST_DEFAULT);
        CHEST_TIER_1 = new WoodItemVariants<>(PackedBlocks::chest, PackedBlocks.CHEST_TIER_1);
        CHEST_TIER_2 = new WoodItemVariants<>(PackedBlocks::chest, PackedBlocks.CHEST_TIER_2);
        CHEST_TIER_3 = new WoodItemVariants<>(PackedBlocks::chest, PackedBlocks.CHEST_TIER_3);

        CRATE_DEFAULT = new WoodItemVariants<>(PackedBlocks::crate, PackedBlocks.CRATE_DEFAULT);
        CRATE_TIER_1 = new WoodItemVariants<>(PackedBlocks::crate, PackedBlocks.CRATE_TIER_1);
        CRATE_TIER_2 = new WoodItemVariants<>(PackedBlocks::crate, PackedBlocks.CRATE_TIER_2);
        CRATE_TIER_3 = new WoodItemVariants<>(PackedBlocks::crate, PackedBlocks.CRATE_TIER_3);

        ITEM_GROUP = FabricItemGroupBuilder.create(Packed.id("packed"))
                .icon(() -> new ItemStack(PackedItems.BARREL_DEFAULT.oak()))
                .appendItems(stacks -> {
                    BARREL_DEFAULT.stream().map(ItemStack::new).forEach(stacks::add);
                    stacks.add(ItemStack.EMPTY);
                    BARREL_TIER_1.stream().map(ItemStack::new).forEach(stacks::add);
                    stacks.add(ItemStack.EMPTY);
                    BARREL_TIER_2.stream().map(ItemStack::new).forEach(stacks::add);
                    stacks.add(ItemStack.EMPTY);
                    BARREL_TIER_3.stream().map(ItemStack::new).forEach(stacks::add);
                    stacks.add(ItemStack.EMPTY);
                    CHEST_DEFAULT.stream().map(ItemStack::new).forEach(stacks::add);
                    stacks.add(ItemStack.EMPTY);
                    CHEST_TIER_1.stream().map(ItemStack::new).forEach(stacks::add);
                    stacks.add(ItemStack.EMPTY);
                    CHEST_TIER_2.stream().map(ItemStack::new).forEach(stacks::add);
                    stacks.add(ItemStack.EMPTY);
                    CHEST_TIER_3.stream().map(ItemStack::new).forEach(stacks::add);
                    stacks.add(ItemStack.EMPTY);
                    CRATE_DEFAULT.stream().map(ItemStack::new).forEach(stacks::add);
                    stacks.add(ItemStack.EMPTY);
                    CRATE_TIER_1.stream().map(ItemStack::new).forEach(stacks::add);
                    stacks.add(ItemStack.EMPTY);
                    CRATE_TIER_2.stream().map(ItemStack::new).forEach(stacks::add);
                    stacks.add(ItemStack.EMPTY);
                    CRATE_TIER_3.stream().map(ItemStack::new).forEach(stacks::add);
                    stacks.add(ItemStack.EMPTY);
                })
                .build();

        STORAGE_UPGRADER_DEFAULT = RegistryHelpers.register("storage_upgrader_default", new StorageUpgrader(newSettings(), StorageTiers.DEFAULT, StorageTiers.TIER_1));
        STORAGE_UPGRADER_TIER1 = RegistryHelpers.register("storage_upgrader_tier1", new StorageUpgrader(newSettings(), StorageTiers.TIER_1, StorageTiers.TIER_2));
        STORAGE_UPGRADER_TIER2 = RegistryHelpers.register("storage_upgrader_tier2", new StorageUpgrader(newSettings(), StorageTiers.TIER_2, StorageTiers.TIER_3));

        STORAGE_LOCKER = RegistryHelpers.register("storage_locker", new StorageLocker(newSettings().maxCount(1)));
    }

    private static Item.Settings newSettings() {
        return new Item.Settings().group(ITEM_GROUP);
    }

    @SuppressWarnings("EmptyMethod")
    public static void register() {
        // keep so class will get called and initialized
    }

}
