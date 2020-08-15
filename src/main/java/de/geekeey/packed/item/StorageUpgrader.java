package de.geekeey.packed.item;

import de.geekeey.packed.block.StorageBarrel;
import de.geekeey.packed.block.entity.StorageBarrelEntity;
import de.geekeey.packed.init.helpers.StorageBarrelTier;
import de.geekeey.packed.init.helpers.StorageBarrelTiers;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class StorageUpgrader extends Item {
    private final StorageBarrelTier fromTier;
    private final StorageBarrelTier toTier;

    public StorageUpgrader(Settings settings, StorageBarrelTier fromTier, StorageBarrelTier toTier) {
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

            if (blockEntity instanceof StorageBarrelEntity) {
                var entity = (StorageBarrelEntity) blockEntity;
                if(entity.getTier().equals(fromTier)){
                    entity.setTier(toTier);
                    Identifier newBlockIdentifier = StorageBarrelTiers.identifier(entity.getTier(), entity.getVariant());
                    var newBlockState = Registry.BLOCK.get(newBlockIdentifier).getDefaultState().with(StorageBarrel.FACING, blockState.get(StorageBarrel.FACING));
                    context.getWorld().setBlockState(pos, newBlockState);
                    return ActionResult.SUCCESS;
                }
            }
            return ActionResult.PASS;
        }

        return ActionResult.PASS;
    }
}
