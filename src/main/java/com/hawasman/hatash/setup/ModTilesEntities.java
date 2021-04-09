package com.hawasman.hatash.setup;

import com.hawasman.hatash.Blocks.CarbonGenerator;
import com.hawasman.hatash.TileEntities.CarbonGeneratorTile;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;

public class ModTilesEntities {
    public static final RegistryObject<TileEntityType<CarbonGeneratorTile>> CARBON_GENERATOR_TILE =
            Registration.TILES.register("carbon_generator", () -> TileEntityType.Builder.create(CarbonGeneratorTile::new, ModBlocks.CARBON_GENERATOR.get()).build(null));

    static void register(){}
}
