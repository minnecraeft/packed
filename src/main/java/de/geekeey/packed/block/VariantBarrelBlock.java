package de.geekeey.packed.block;

import de.geekeey.packed.block.entity.VariantBarrelBlockEntity;
import de.geekeey.packed.init.helpers.StorageTier;
import de.geekeey.packed.init.helpers.WoodVariant;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BarrelBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.NotNull;

import static net.minecraft.block.Blocks.BARREL;

public class VariantBarrelBlock extends BarrelBlock {

    private final StorageTier tier;
    private final WoodVariant variant;

    public VariantBarrelBlock(@NotNull StorageTier tier, @NotNull WoodVariant variant) {
        super(FabricBlockSettings.copyOf(BARREL));
        this.tier = tier;
        this.variant = variant;
    }

    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        return new VariantBarrelBlockEntity(getTier(), getVariant());
    }

    public @NotNull StorageTier getTier() {
        return tier;
    }

    public @NotNull WoodVariant getVariant() {
        return variant;
    }

}
