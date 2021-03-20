package com.hawasman.hatash.setup;

import com.hawasman.hatash.Items.EnergizedMiner;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.fml.RegistryObject;

public class ModItems {
    public static final RegistryObject<Item> SILVER_INGOT = Registration.ITEMS.register("silver_ingot", ()->
            new Item(new Item.Properties().group(ItemGroup.MATERIALS)));
    public static final RegistryObject<Item> ENERGIZED_MINER = Registration.ITEMS.register("energized_miner", EnergizedMiner::new);

    static void register(){}
}
