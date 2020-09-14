package de.geekeey.packed.recipe;

import com.google.common.collect.Sets;
import de.geekeey.packed.init.PackedRecipes;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialCraftingRecipe;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.IntStream;

public class ChestRecipe extends SpecialCraftingRecipe {

    private final static HashSet<Item> vanilla = Sets.newHashSet(
            Items.OAK_PLANKS,
            Items.SPRUCE_PLANKS,
            Items.BIRCH_PLANKS,
            Items.ACACIA_PLANKS,
            Items.JUNGLE_PLANKS,
            Items.DARK_OAK_PLANKS,
            Items.CRIMSON_PLANKS,
            Items.WARPED_PLANKS
            );

    public ChestRecipe(Identifier identifier) {
        super(identifier);
    }

    @Override
    public boolean matches(CraftingInventory crafting, World world) {
        if (crafting.getWidth() != 3 || crafting.getHeight() != 3) return false;

        Item[] items = IntStream.range(0, 9)
                .filter(i -> i != 4)
                .mapToObj(crafting::getStack)
                .filter(stack -> !stack.isEmpty())
                .map(ItemStack::getItem)
                .filter(item -> item.isIn(ItemTags.PLANKS))
                .toArray(Item[]::new);

        int different = Arrays.stream(items)
                .distinct()
                .mapToInt(item -> 1)
                .reduce(-1, Integer::sum);

        if(!crafting.getStack(4).isEmpty())return false;

        if (different == 0) {
            return !vanilla.contains(items[0]);
        }

        return items.length == 8;
    }

    @Override
    public ItemStack craft(CraftingInventory crafting) {
        return new ItemStack(Items.CHEST);
    }

    @Override
    public boolean fits(int width, int height) {
        return width >= 3 && height >= 3;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return PackedRecipes.CHEST;
    }
}
