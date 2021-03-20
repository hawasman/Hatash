package com.hawasman.hatash.data;

import com.hawasman.hatash.HatashMod;
import com.hawasman.hatash.data.client.ModBlockStateProvider;
import com.hawasman.hatash.data.client.ModItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = HatashMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class DataGenerator {

    private DataGenerator(){}

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event){
        net.minecraft.data.DataGenerator generator = event.getGenerator();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();
        generator.addProvider(new ModBlockStateProvider(generator, fileHelper));
        generator.addProvider(new ModItemModelProvider(generator, fileHelper));

        ModBlockTagsProvider blockTagsProvider = new ModBlockTagsProvider(generator, fileHelper);
        generator.addProvider(blockTagsProvider);
        generator.addProvider(new ModItemTagsProvider(generator, blockTagsProvider, fileHelper));
    }
}
