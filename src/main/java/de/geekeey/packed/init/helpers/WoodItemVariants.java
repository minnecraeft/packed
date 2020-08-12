package de.geekeey.packed.init.helpers;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.function.BiFunction;

/**
 * This class is the extension for {@link WoodBlockVariants} which will register a Item for each {@link WoodVariant}.
 *
 * @param <T> Represent the additional parameter for the blocks
 * @param <B> Represent the block which will exist in different variants
 * @see WoodBlockVariants
 */
public class WoodItemVariants<T, B extends Block> implements Iterable<BlockItem> {

    public final BlockItem oak;
    public final BlockItem spruce;
    public final BlockItem birch;
    public final BlockItem acacia;
    public final BlockItem jungle;
    public final BlockItem darkOak;
    public final BlockItem crimson;
    public final BlockItem warped;

    private final Set<BlockItem> variants = new HashSet<>();

    /**
     * Creates new instance of WoodItemVariants which will internally register all {@link BlockItem BlockItems} one for
     * each variant defined in WoodBlockVariants.
     *
     * @param tier   The additional parameter for the common tier usage
     * @param id     A function creating an {@link Identifier} to register a item by its {@link WoodVariant}
     * @param blocks The corresponding WoodBlockVariants to register the {@link BlockItem} for
     * @param group  The {@link ItemGroup} in which the item will be registered
     */
    public WoodItemVariants(T tier, BiFunction<T, WoodVariant, Identifier> id, WoodBlockVariants<T, B> blocks, ItemGroup group) {
        oak = Registry.register(Registry.ITEM, id.apply(tier, WoodVariants.OAK), newBlockItem(blocks.oak, group));
        spruce = Registry.register(Registry.ITEM, id.apply(tier, WoodVariants.SPRUCE), newBlockItem(blocks.spruce, group));
        birch = Registry.register(Registry.ITEM, id.apply(tier, WoodVariants.BIRCH), newBlockItem(blocks.birch, group));
        acacia = Registry.register(Registry.ITEM, id.apply(tier, WoodVariants.ACACIA), newBlockItem(blocks.acacia, group));
        jungle = Registry.register(Registry.ITEM, id.apply(tier, WoodVariants.JUNGLE), newBlockItem(blocks.jungle, group));
        darkOak = Registry.register(Registry.ITEM, id.apply(tier, WoodVariants.DARK_OAK), newBlockItem(blocks.darkOak, group));
        crimson = Registry.register(Registry.ITEM, id.apply(tier, WoodVariants.CRIMSON), newBlockItem(blocks.crimson, group));
        warped = Registry.register(Registry.ITEM, id.apply(tier, WoodVariants.WARPED), newBlockItem(blocks.warped, group));
    }

    private BlockItem newBlockItem(B block, ItemGroup group) {
        BlockItem item = new BlockItem(block, new Item.Settings().group(group));
        variants.add(item);
        return item;
    }

    @NotNull
    @Override
    public Iterator<BlockItem> iterator() {
        return variants.iterator();
    }
}

