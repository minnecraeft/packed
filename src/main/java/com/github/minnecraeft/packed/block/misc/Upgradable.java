package com.github.minnecraeft.packed.block.misc;

import com.github.minnecraeft.packed.init.helpers.StorageTier;
import com.github.minnecraeft.packed.init.helpers.WoodVariant;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import org.jetbrains.annotations.NotNull;

import java.util.stream.IntStream;

public interface Upgradable {
    @NotNull StorageTier getTier();

    void setTier(@NotNull StorageTier tier);

    @NotNull WoodVariant getVariant();

    void setVariant(WoodVariant variant);

    static DefaultedList<ItemStack> resize(DefaultedList<ItemStack> current, int size) {
        DefaultedList<ItemStack> stacks = DefaultedList.ofSize(size, ItemStack.EMPTY);
        IntStream.range(0, current.size()).forEach(i -> stacks.set(i, current.get(i)));
        return stacks;
    }
}
