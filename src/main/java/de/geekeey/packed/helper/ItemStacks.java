package de.geekeey.packed.helper;

import net.minecraft.item.ItemStack;

public final class ItemStacks {

    private ItemStacks() {
    }

    public static boolean notEmpty(ItemStack stack) {
        return !stack.isEmpty();
    }

}
