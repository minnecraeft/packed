package de.geekeey.packed.init.helpers;

import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.function.BiFunction;

public class WoodBlockVariants<T, B extends Block> implements Iterable<B> {

    public final B oak;
    public final B spruce;
    public final B birch;
    public final B acacia;
    public final B jungle;
    public final B darkOak;
    public final B crimson;
    public final B warped;

    private final Set<B> variants = new HashSet<>();

    public WoodBlockVariants(T tier, BiFunction<T, WoodVariant, Identifier> id, BiFunction<T, WoodVariant, B> block) {
        oak = register(tier, WoodVariants.OAK, id, block);
        spruce = register(tier, WoodVariants.SPRUCE, id, block);
        birch = register(tier, WoodVariants.BIRCH, id, block);
        acacia = register(tier, WoodVariants.ACACIA, id, block);
        jungle = register(tier, WoodVariants.JUNGLE, id, block);
        darkOak = register(tier, WoodVariants.DARK_OAK, id, block);
        crimson = register(tier, WoodVariants.CRIMSON, id, block);
        warped = register(tier, WoodVariants.WARPED, id, block);
    }

    private B register(T tier, WoodVariant variant, BiFunction<T, WoodVariant, Identifier> id, BiFunction<T, WoodVariant, B> block) {
        B object = block.apply(tier, variant);
        variants.add(object);
        Registry.register(Registry.BLOCK, id.apply(tier, variant), object);
        return object;
    }

    @NotNull
    @Override
    public Iterator<B> iterator() {
        return variants.iterator();
    }
}

