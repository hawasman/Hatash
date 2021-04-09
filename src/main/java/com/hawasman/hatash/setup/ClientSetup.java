package com.hawasman.hatash.setup;

import com.hawasman.hatash.HatashMod;
import com.hawasman.hatash.screens.CarbonGeneratorScreen;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = HatashMod.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup {
    public static void init(final FMLClientSetupEvent fmlClientSetupEvent) {
        ScreenManager.registerFactory(ModContainers.CARBON_GENERATOR_CONTAINER.get(), CarbonGeneratorScreen::new);
    }
}
