package com.hawasman.hatash.data;

import com.hawasman.hatash.HatashMod;
import com.hawasman.hatash.setup.ModItems;
import com.hawasman.hatash.setup.ModTags;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

public class ModItemTagsProvider extends ItemTagsProvider {
    public ModItemTagsProvider(DataGenerator dataGenerator, BlockTagsProvider blockTagProvider,ExistingFileHelper existingFileHelper) {
        super(dataGenerator, blockTagProvider, HatashMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerTags() {
        copy(ModTags.Blocks.ORES_SILVER,ModTags.Items.ORES_SILVER);
        copy(Tags.Blocks.ORES, Tags.Items.ORES);
        copy(ModTags.Blocks.STORAGE_BLOCK_SILVER,ModTags.Items.STORAGE_BLOCK_SILVER);
        copy(Tags.Blocks.STORAGE_BLOCKS, Tags.Items.STORAGE_BLOCKS);

        copy(ModTags.Blocks.MACHINES_CARBON_GENERATOR, ModTags.Items.MACHINES_CARBON_GENERATOR);

        getOrCreateBuilder(ModTags.Items.INGOTS_SILVER).add(ModItems.SILVER_INGOT.get());
        getOrCreateBuilder(Tags.Items.INGOTS).addTag(ModTags.Items.INGOTS_SILVER);
        getOrCreateBuilder(ModTags.Items.ITEM_ENERGIZED_MINER).add(ModItems.ENERGIZED_MINER.get());
    }
}
