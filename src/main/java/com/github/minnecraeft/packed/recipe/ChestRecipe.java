package com.github.minnecraeft.packed.recipe;

import com.github.minnecraeft.packed.init.PackedRecipes;
import com.google.common.collect.Sets;
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
        if (crafting.getWidth() != 3 || crafting.getHeight() != 3 || !crafting.getStack(4).isEmpty())
            return false;

        Item[] items = IntStream.range(0, 9)
                .filter(i -> i != 4)
                .mapToObj(crafting::getStack)
                .filter(stack -> !stack.isEmpty())
                .map(ItemStack::getItem)
                .filter(item -> item.isIn(ItemTags.PLANKS))
                .toArray(Item[]::new);

        int distinct = Arrays.stream(items)
                .distinct()
                .mapToInt(item -> 1)
                .reduce(-1, Integer::sum);

        // if 8 pieces of same item are not vanilla -> make chest
        return items.length == 8 && (distinct != 0 || !vanilla.contains(items[0]));

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
