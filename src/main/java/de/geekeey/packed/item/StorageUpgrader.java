package de.geekeey.packed.item;

import de.geekeey.packed.block.VariantStorageBarrel;
import de.geekeey.packed.block.entity.VariantBarrelBlockEntity;
import de.geekeey.packed.block.entity.VariantChestBlockEntity;
import de.geekeey.packed.block.entity.VariantStorageBarrelBlockEntity;
import de.geekeey.packed.init.PackedBlocks;
import de.geekeey.packed.init.helpers.StorageTier;
import net.minecraft.block.BarrelBlock;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.enums.ChestType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.registry.Registry;

public class StorageUpgrader extends Item {
    private final StorageTier fromTier;
    private final StorageTier toTier;

    public StorageUpgrader(Settings settings, StorageTier fromTier, StorageTier toTier) {
        super(settings);
        this.fromTier = fromTier;
        this.toTier = toTier;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (!context.getWorld().isClient) {
            var pos = context.getBlockPos();
            var blockState = context.getWorld().getBlockState(pos);
            var blockEntity = context.getWorld().getBlockEntity(pos);

            if (blockEntity instanceof VariantStorageBarrelBlockEntity) {
                var entity = (VariantStorageBarrelBlockEntity) blockEntity;
                if (entity.getTier().equals(fromTier)) {
                    entity.setTier(toTier);
                    var identifier = PackedBlocks.storageBarrel(entity.getTier(), entity.getVariant());
                    var newBlockState = Registry.BLOCK.get(identifier).getDefaultState().with(VariantStorageBarrel.FACING, blockState.get(VariantStorageBarrel.FACING));
                    context.getWorld().setBlockState(pos, newBlockState);
                    return ActionResult.SUCCESS;
                }
            }
            else if(blockEntity instanceof VariantBarrelBlockEntity){
                var entity = (VariantBarrelBlockEntity) blockEntity;
                if (entity.getTier().equals(fromTier)) {
                    entity.setTier(toTier);
                    var identifier = PackedBlocks.barrel(entity.getTier(), entity.getVariant());
                    var newBlockState = Registry.BLOCK.get(identifier).getDefaultState().with(BarrelBlock.FACING, blockState.get(BarrelBlock.FACING));
                    context.getWorld().setBlockState(pos, newBlockState);
                    return ActionResult.SUCCESS;
                }
            }
            else if(blockEntity instanceof VariantChestBlockEntity){
                var entity = (VariantChestBlockEntity) blockEntity;
                if (entity.getTier().equals(fromTier)) {
                    entity.setTier(toTier);
                    var identifier = PackedBlocks.chest(entity.getTier(), entity.getVariant());
                    var newBlockState = Registry.BLOCK.get(identifier).getDefaultState().with(ChestBlock.FACING, blockState.get(ChestBlock.FACING)).with(ChestBlock.CHEST_TYPE, ChestType.SINGLE);
                    context.getWorld().setBlockState(pos, newBlockState);
                    return ActionResult.SUCCESS;
                }
            }
        }
        return ActionResult.PASS;
    }
}
