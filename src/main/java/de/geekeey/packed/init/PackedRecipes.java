package de.geekeey.packed.init;

import de.geekeey.packed.Packed;
import de.geekeey.packed.recipe.ChestRecipe;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.SpecialRecipeSerializer;
import net.minecraft.util.registry.Registry;

public class PackedRecipes {

    public static final SpecialRecipeSerializer<Recipe<?>> CHEST;

    static {
        CHEST = register("crafting_special_chest", new SpecialRecipeSerializer<>(ChestRecipe::new));
    }

    private static <S extends RecipeSerializer<Recipe<?>>, T extends Recipe<?>> S register(String identifier, S serializer) {
        return Registry.register(Registry.RECIPE_SERIALIZER, Packed.id(identifier), serializer);
    }

    public static void register() {
        // keep so class will get called and initialized
    }

}
