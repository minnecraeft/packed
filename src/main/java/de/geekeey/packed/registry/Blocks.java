package de.geekeey.packed.registry;

import de.geekeey.packed.block.CustomBarrel;
import de.geekeey.packed.block.CustomChestBlock;
import de.geekeey.packed.block.entity.CustomBarrelEntity;
import de.geekeey.packed.block.entity.CustomChestEntity;
import de.geekeey.packed.initialisers.Initializer;
import net.minecraft.block.Block;
import net.minecraft.util.registry.Registry;

import java.util.HashSet;
import java.util.Set;

import static net.minecraft.block.AbstractBlock.Settings.copy;
import static net.minecraft.block.Blocks.BARREL;

public class Blocks {

    public static final Block OAK_BARREL;
    public static final Block SPRUCE_BARREL;
    public static final Block BIRCH_BARREL;
    public static final Block JUNGLE_BARREL;
    public static final Block ACACIA_BARREL;
    public static final Block DARK_OAK_BARREL;

    public static final Block OAK_CHEST;
    public static final Block SPRUCE_CHEST;
    public static final Block BIRCH_CHEST;
    public static final Block JUNGLE_CHEST;
    public static final Block ACACIA_CHEST;
    public static final Block DARK_OAK_CHEST;

    public static final Set<CustomBarrel> BARRELS_3_9 = new HashSet<>();
    public static final Set<CustomBarrel> BARRELS_4_9 = new HashSet<>();
    public static final Set<CustomBarrel> BARRELS_5_9 = new HashSet<>();
    public static final Set<CustomBarrel> BARRELS_6_9 = new HashSet<>();

    public static final Set<CustomChestBlock> CHESTS_3_9 = new HashSet<>();


    static {
        OAK_BARREL = register("barrel_oak", createBarrel3x9());
        SPRUCE_BARREL = register("barrel_spruce", createBarrel3x9());
        BIRCH_BARREL = register("barrel_birch", createBarrel3x9());
        JUNGLE_BARREL = register("barrel_jungle", createBarrel3x9());
        ACACIA_BARREL = register("barrel_acacia", createBarrel3x9());
        DARK_OAK_BARREL = register("barrel_dark_oak", createBarrel3x9());

        OAK_CHEST = register("chest_oak",createChest3x9());
        SPRUCE_CHEST = register("chest_spruce",createChest3x9());
        BIRCH_CHEST = register("chest_birch",createChest3x9());
        JUNGLE_CHEST = register("chest_jungle",createChest3x9());
        ACACIA_CHEST = register("chest_acacia",createChest3x9());
        DARK_OAK_CHEST = register("chest_dark_oak",createChest3x9());
    }

    private static CustomBarrel createBarrel3x9() {
        CustomBarrel barrel = new CustomBarrel(copy(BARREL), CustomBarrelEntity::create3x9);
        BARRELS_3_9.add(barrel);
        return barrel;
    }

    private static CustomChestBlock createChest3x9() {
        CustomChestBlock chest = new CustomChestBlock(copy(BARREL),()-> BlockEntities.CHEST_3_9, CustomChestEntity::create3x9);
        CHESTS_3_9.add(chest);
        return chest;
    }

    private static <T extends Block> T register(String identifier, T block) {
        return Registry.register(Registry.BLOCK, Initializer.id(identifier), block);
    }

    public static void register() {
        // does nothing but will init this class
    }
}
