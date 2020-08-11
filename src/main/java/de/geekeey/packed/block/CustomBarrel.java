package de.geekeey.packed.block;

import de.geekeey.packed.init.helpers.BarrelTier;
import de.geekeey.packed.init.helpers.WoodVariant;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BarrelBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.BlockView;

import static net.minecraft.block.Blocks.BARREL;

public class CustomBarrel extends BarrelBlock {

    private final BarrelTier tier;
    private final WoodVariant variant;

    public CustomBarrel(BarrelTier tier, WoodVariant variant) {
        super(FabricBlockSettings.copyOf(BARREL));
        this.tier = tier;
        this.variant = variant;
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return tier.newBlockEntity();
    }

    public BarrelTier getTier() {
        return tier;
    }

    public WoodVariant getVariant() {
        return variant;
    }
}
