package com.hawasman.hatash.capabilities;

import com.hawasman.hatash.config.Configuration;
import net.minecraft.item.ItemStack;
import net.minecraftforge.energy.EnergyStorage;

public class EnergyCapability extends EnergyStorage {
    private ItemStack itemStack;
    private int Capacity;
    public EnergyCapability(ItemStack itemStack, int capacity) {
        super((int) Configuration.GENERAL_CONFIG.MAX_ENERGY,Integer.MAX_VALUE, Integer.MAX_VALUE);
        this.itemStack = itemStack;
        this.energy = itemStack.hasTag() && itemStack.getTag().contains("energy") ? itemStack.getTag().getInt("energy") : 0;
    }
}
