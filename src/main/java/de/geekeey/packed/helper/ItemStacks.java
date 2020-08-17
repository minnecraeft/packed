package de.geekeey.packed.helper;

import net.minecraft.item.ItemStack;

public class ItemStacks {

    private boolean canCombine(ItemStack one, ItemStack two) {
        return ItemStack.areItemsEqualIgnoreDamage(one, two) && ItemStack.areTagsEqual(one, two);
    }

}
