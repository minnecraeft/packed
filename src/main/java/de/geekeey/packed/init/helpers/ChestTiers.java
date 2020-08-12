package de.geekeey.packed.init.helpers;

import de.geekeey.packed.Packed;
import de.geekeey.packed.block.entity.CustomChestEntity;
import de.geekeey.packed.init.PackedEntities;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.util.Identifier;

import java.util.function.Supplier;

/**
 * The default implementation for the {@link ChestTier ChestTiers} which are used in this mod.
 */
public enum ChestTiers implements ChestTier {

    /**
     * The default tier which should create the default block.
     * In our case a 3 by 9 container.
     */
    DEFAULT("default", CustomChestEntity::create3x9, () -> PackedEntities.CHEST_3_9),
    /**
     * The tier 1 which has a single row more than the default.
     * In our case a 4 by 9 container.
     */
    TIER1("tier1", CustomChestEntity::create4x9, () -> PackedEntities.CHEST_4_9),
    /**
     * The tier 2 which has two more rows than the default.
     * In our case a 5 by 9 container.
     */
    TIER2("tier2", CustomChestEntity::create5x9, () -> PackedEntities.CHEST_5_9),
    /**
     * The tier 3 which has three more rows than the default.
     * In our case a 6 by 9 container.
     * This is the highest tier currently available.
     */
    TIER3("tier3", CustomChestEntity::create6x9, () -> PackedEntities.CHEST_6_9);

    private final String identifier;
    private final Supplier<BlockEntityType<? extends ChestBlockEntity>> type;
    private final Supplier<CustomChestEntity> supplier;

    ChestTiers(String identifier, Supplier<CustomChestEntity> supplier, Supplier<BlockEntityType<? extends ChestBlockEntity>> type) {
        this.identifier = identifier;
        this.supplier = supplier;
        this.type = type;
    }

    /**
     * Creates a new {@link Identifier} in the namespace of {@link Packed} which will uniquely identify a CustomChest
     * by its {@link ChestTier} and its {@link WoodVariant}.
     *
     * @param tier    The chest tier of the chest
     * @param variant The wood variant of the chest
     * @return A new Identifier in the form of <code>{variant}_chest_{tier}</code>
     */
    public static Identifier identifier(ChestTier tier, WoodVariant variant) {
        return Packed.id(String.format("%s_chest_%s", variant.identifier(), tier.identifier()));
    }

    @Override
    public Supplier<BlockEntityType<? extends ChestBlockEntity>> getBlockEntityType() {
        return type;
    }

    @Override
    public CustomChestEntity newBlockEntity() {
        return supplier.get();
    }

    @Override
    public String identifier() {
        return identifier;
    }

}
