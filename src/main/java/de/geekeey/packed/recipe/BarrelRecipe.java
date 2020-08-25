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
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.IntStream;

public class BarrelRecipe extends SpecialCraftingRecipe {
    private static final HashSet<Item> vanilla_planks = Sets.newHashSet(
            Items.OAK_PLANKS,
            Items.SPRUCE_PLANKS,
            Items.BIRCH_PLANKS,
            Items.ACACIA_PLANKS,
            Items.JUNGLE_PLANKS,
            Items.DARK_OAK_PLANKS,
            Items.CRIMSON_PLANKS,
            Items.WARPED_PLANKS
    );
    private static final HashSet<Item> vanilla_slabs = Sets.newHashSet(
            Items.OAK_SLAB,
            Items.SPRUCE_SLAB,
            Items.BIRCH_SLAB,
            Items.ACACIA_SLAB,
            Items.JUNGLE_SLAB,
            Items.DARK_OAK_SLAB,
            Items.CRIMSON_SLAB,
            Items.WARPED_SLAB
    );

    public BarrelRecipe(Identifier identifier) {
        super(identifier);
    }

    @Override
    public boolean matches(CraftingInventory crafting, World world) {
        if (crafting.getWidth() != 3 || crafting.getHeight() != 3) return false;

        // middle slot must be empty or we won't craft
        if (!crafting.getStack(4).isEmpty()) return false;

        // All planks in the correct spot
        Item[] planks = IntStream.of(0, 2, 3, 5, 6, 8)
                .mapToObj(crafting::getStack)
                .map(ItemStack::getItem)
                .filter(stack -> stack.isIn(ItemTags.PLANKS))
                .toArray(Item[]::new);

        if (planks.length != 6) return false;

        // all slabs in their valid position
        Item[] slabs = IntStream.of(1, 7)
                .mapToObj(crafting::getStack)
                .map(ItemStack::getItem)
                .filter(stack -> stack.isIn(ItemTags.WOODEN_SLABS))
                .toArray(Item[]::new);

        if (slabs.length != 2) return false;

        // the amount of unique planks in that list
        int plankCount = Arrays.stream(planks)
                .distinct()
                .mapToInt(item -> 1)
                .sum();

        // the amount of unique slabs in that list
        int slabCount = Arrays.stream(slabs)
                .distinct()
                .mapToInt(item -> 1)
                .sum();

        if (plankCount == 1 && slabCount == 1) {
            // -> same wood variants
            if (vanilla_planks.contains(planks[0]) && vanilla_slabs.contains(slabs[0])) {
                // -> planks and slabs are vanilla variants
                String plankType = Registry.ITEM.getId(planks[0]).toString().split("_")[0];
                String slabType = Registry.ITEM.getId(slabs[0]).toString().split("_")[0];
                return !plankType.equals(slabType);
            }
        }
        // -> different wood variants but count matches
        return true;
/*
        //if (plankCount == 1 && plankCount == 1)

        //if only one wood variant is used each and their wood types match it is a mod barrel and we dont craft the vanilla chest
        if (plankCount == 1 && slabCount == 1) {
            if (vanilla_planks.contains(planks[0]) && vanilla_slabs.contains(slabs[0]) && Registry.ITEM.getId(planks[0]).toString().split("_")[0].equals(Registry.ITEM.getId(slabs[0]).toString().split("_")[0]))
                return false;
        }
        //if it isn't a mod barrel we check weather the recipe is valid for the vanilla barrel recipe
        return planks.length == 6 && slabs.length == 2;*/
    }

    @Override
    public ItemStack craft(CraftingInventory crafting) {
        return new ItemStack(Items.BARREL);
    }

    @Override
    public boolean fits(int width, int height) {
        return width >= 3 && height >= 3;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return PackedRecipes.BARREL;
    }
}
