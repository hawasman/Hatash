package com.hawasman.hatash.Blocks;

import com.hawasman.hatash.TileEntities.CarbonGeneratorTile;
import com.hawasman.hatash.config.Configuration;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public class CarbonGenerator extends EnergizedBlock{
    private boolean isRunning = false;
    private int energyCapacity;
    public CarbonGenerator() {
        super(Properties.create(Material.IRON)
            .sound(SoundType.METAL)
                .hardnessAndResistance(2.0f)
                .setLightLevel(state -> state.get(BlockStateProperties.POWERED) ? 14 : 0 )
        );
        this.energyCapacity = (int) Configuration.GENERAL_CONFIG.MAX_ENERGY;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new CarbonGeneratorTile();
    }
}
