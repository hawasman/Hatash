package com.hawasman.hatash.capabilities;

import com.hawasman.hatash.Items.EnergizedItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ModCapabilityProvider implements ICapabilityProvider {

    private ItemStack itemStack;
    private int energyCapacity;
    private LazyOptional<IEnergyStorage> capability = LazyOptional.of(() -> new EnergyCapability(itemStack, energyCapacity));

    public ModCapabilityProvider(ItemStack itemStack, int energyCapacity) {
        this.itemStack = itemStack;
        this.energyCapacity = energyCapacity;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return cap == CapabilityEnergy.ENERGY ? capability.cast() : LazyOptional.empty();
    }
}
