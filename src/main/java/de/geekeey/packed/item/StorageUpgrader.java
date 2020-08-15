package de.geekeey.packed.item;

import de.geekeey.packed.block.StorageBarrel;
import de.geekeey.packed.block.entity.CustomBarrelEntity;
import de.geekeey.packed.block.entity.CustomChestEntity;
import de.geekeey.packed.block.entity.StorageBarrelEntity;
import de.geekeey.packed.init.helpers.StorageBarrelTiers;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class StorageUpgrader extends Item {
    public StorageUpgrader(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (!context.getWorld().isClient) {
            var pos = context.getBlockPos();
            var blockState = context.getWorld().getBlockState(pos);
            var blockEntity = context.getWorld().getBlockEntity(pos);

            if (blockEntity instanceof StorageBarrelEntity) {
                var entity = (StorageBarrelEntity) blockEntity;
                entity.setTier(entity.getTier().upgrade().orElse(entity.getTier()));
                Identifier newBlockIdentifier = StorageBarrelTiers.identifier(entity.getTier(), entity.getVariant());
                var newBlockState = Registry.BLOCK.get(newBlockIdentifier).getDefaultState().with(StorageBarrel.FACING, blockState.get(StorageBarrel.FACING));
                context.getWorld().setBlockState(pos, newBlockState);
                return ActionResult.SUCCESS;
            }
            else if(blockEntity instanceof CustomBarrelEntity){

            }
            else if(blockEntity instanceof CustomChestEntity){

            }
        }

        return ActionResult.FAIL;
    }
}
