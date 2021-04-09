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

public class ModEnergyCapabilityProvider implements ICapabilityProvider {
    private final LazyOptional<IEnergyStorage> capability;

    public ModEnergyCapabilityProvider(ItemStack stack, int energyCapacity){
         capability = LazyOptional.of(() -> new EnergizedItem(stack, energyCapacity));
    }


    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return getCapability(cap);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap) {
        return cap == CapabilityEnergy.ENERGY ? capability.cast() : LazyOptional.empty();
    }
}
