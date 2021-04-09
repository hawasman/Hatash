package com.hawasman.hatash.data;

import com.hawasman.hatash.HatashMod;
import com.hawasman.hatash.setup.ModBlocks;
import com.hawasman.hatash.setup.ModItems;
import net.minecraft.data.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        ShapelessRecipeBuilder.shapelessRecipe(ModItems.SILVER_INGOT.get(), 9)
                .addIngredient(ModBlocks.SILVER_BLOCK.get())
                .addCriterion("has_item", hasItem(ModItems.SILVER_INGOT.get()))
                .build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ModBlocks.SILVER_BLOCK.get())
                .key('#', ModItems.SILVER_INGOT.get())
                .patternLine("###")
                .patternLine("###")
                .patternLine("###")
                .addCriterion("has_item", hasItem(ModItems.SILVER_INGOT.get()))
                .build(consumer);

        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(ModBlocks.SILVER_ORE.get()), ModItems.SILVER_INGOT.get(), 0.2f, 200)
                .addCriterion("has_item", hasItem(ModBlocks.SILVER_ORE.get()))
                .build(consumer, modId("silver_ingot_smelting"));
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(ModBlocks.SILVER_ORE.get()), ModItems.SILVER_INGOT.get(), 0.2f, 100)
                .addCriterion("has_item", hasItem(ModBlocks.SILVER_ORE.get()))
                .build(consumer, modId("silver_ingot_blasting"));
    }

    private static ResourceLocation modId(String path) {
        return new ResourceLocation(HatashMod.MOD_ID, path);
    }
}
