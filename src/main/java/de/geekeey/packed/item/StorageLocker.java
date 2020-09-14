package de.geekeey.packed.item;

import de.geekeey.packed.block.entity.VariantCrateBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class StorageLocker extends Item {

    public StorageLocker(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        if (world.isClient) return ActionResult.PASS;
        BlockPos pos = context.getBlockPos();
        BlockEntity container = world.getBlockEntity(pos);

        if (container instanceof VariantCrateBlockEntity) {
            VariantCrateBlockEntity crate = (VariantCrateBlockEntity) container;
            crate.setLocked(!crate.isLocked());
            crate.refreshFilter();
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }
}
