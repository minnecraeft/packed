package com.github.minnecraeft.packed.init.helpers;

import com.google.common.collect.ImmutableMap;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.function.BiFunction;
import java.util.stream.Stream;

/**
 * This class is the extension for {@link WoodBlockVariants} which will register a Item for each {@link WoodVariant}.
 *
 * @param <T> Represent the additional parameter for the blocks
 * @param <B> Represent the block which will exist in different variants
 * @see WoodBlockVariants
 */
public class WoodItemVariants<T, B extends Block> implements Iterable<BlockItem> {

    public final ImmutableMap<WoodVariant, BlockItem> variants;

    /**
     * Creates new instance of WoodItemVariants which will internally register all {@link BlockItem BlockItems} one for
     * each variant defined in WoodBlockVariants.
     *
     * @param id     A function creating an {@link Identifier} to register a item by its {@link WoodVariant}
     * @param blocks The corresponding WoodBlockVariants to register the {@link BlockItem} for
     */
    public WoodItemVariants(BiFunction<T, WoodVariant, Identifier> id, WoodBlockVariants<T, B> blocks) {
        ImmutableMap.Builder<WoodVariant, BlockItem> builder = ImmutableMap.builder();

        for (WoodVariant variant : blocks.variants.keySet()) {
            Identifier identifier = id.apply(blocks.tier, variant);
            BlockItem item = new BlockItem(blocks.variants.get(variant), new Item.Settings().group(ItemGroup.SEARCH));
            Registry.register(Registry.ITEM, identifier, item);
            builder.put(variant, item);
        }

        variants = builder.build();
    }

    public BlockItem oak() {
        return variants.get(WoodVariants.OAK);
    }

    public BlockItem spruce() {
        return variants.get(WoodVariants.SPRUCE);
    }

    public BlockItem birch() {
        return variants.get(WoodVariants.BIRCH);
    }

    public BlockItem acacia() {
        return variants.get(WoodVariants.ACACIA);
    }

    public BlockItem jungle() {
        return variants.get(WoodVariants.JUNGLE);
    }

    public BlockItem darkOak() {
        return variants.get(WoodVariants.DARK_OAK);
    }

    public BlockItem crimson() {
        return variants.get(WoodVariants.CRIMSON);
    }

    public BlockItem warped() {
        return variants.get(WoodVariants.WARPED);
    }

    @NotNull
    @Override
    public Iterator<BlockItem> iterator() {
        return variants.values().iterator();
    }

    public Stream<BlockItem> stream() {
        return variants.values().stream();
    }

}

