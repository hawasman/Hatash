package com.hawasman.hatash.setup;

import com.hawasman.hatash.HatashMod;
import net.minecraft.block.Block;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class Registration {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, HatashMod.MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, HatashMod.MOD_ID);
    public static final DeferredRegister<TileEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, HatashMod.MOD_ID);
    public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, HatashMod.MOD_ID);

    public static void register(){
        IEventBus modEvents = FMLJavaModLoadingContext.get().getModEventBus();
        BLOCKS.register(modEvents);
        ITEMS.register(modEvents);
        TILES.register(modEvents);
        CONTAINERS.register(modEvents);

        ModItems.register();
        ModBlocks.register();
        ModTilesEntities.register();
        ModContainers.register();
    }
}
