package com.hawasman.hatash.Blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;

public class EnergizedBlock extends Block {

    public EnergizedBlock(Properties properties) { super(properties); }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }


}
