package com.hawasman.hatash.data;

import com.google.common.collect.ImmutableList;
import com.hawasman.hatash.setup.ModBlocks;
import com.hawasman.hatash.setup.Registration;
import com.mojang.datafixers.util.Pair;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.LootTableProvider;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraft.loot.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ModLootTableProvider extends LootTableProvider {
    public ModLootTableProvider(DataGenerator dataGeneratorIn) {
        super(dataGeneratorIn);
    }

    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> getTables() {
        return ImmutableList.of(
                Pair.of(ModBlockLootTables::new, LootParameterSets.BLOCK)
        );
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationTracker validationtracker) {
        map.forEach(((resourceLocation, lootTable) -> LootTableManager.validateLootTable(validationtracker,resourceLocation,lootTable)));
    }

    public class ModBlockLootTables extends BlockLootTables{
        @Override
        protected void addTables() {
            registerDropSelfLootTable(ModBlocks.SILVER_BLOCK.get());
            registerDropSelfLootTable(ModBlocks.SILVER_ORE.get());
            registerDropSelfLootTable(ModBlocks.CARBON_GENERATOR.get());
        }

        @Override
        protected Iterable<Block> getKnownBlocks() {
            return Registration.BLOCKS.getEntries().stream().map(RegistryObject::get).collect(Collectors.toList());
        }
    }
}
