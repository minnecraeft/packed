package de.geekeey.packed.block.misc;

import de.geekeey.packed.init.helpers.StorageTier;
import de.geekeey.packed.init.helpers.WoodVariant;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import org.jetbrains.annotations.NotNull;

public interface Upgradable {
    @NotNull StorageTier getTier();

    void setTier(@NotNull StorageTier tier);

    @NotNull WoodVariant getVariant();

    void setVariant(WoodVariant variant);

    static DefaultedList<ItemStack> ExtendInventory(DefaultedList<ItemStack> old,int newsize){
        var upgradedList = DefaultedList.ofSize(newsize,ItemStack.EMPTY);
        for(int i = 0; i < old.size() ; ++i){
            upgradedList.set(i,old.get(i));
        }
        return upgradedList;
    }
}
