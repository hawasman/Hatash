package com.hawasman.hatash.TileEntities;

import com.hawasman.hatash.capabilities.ModEnergyStorage;
import com.hawasman.hatash.config.GeneralConfig;
import com.hawasman.hatash.setup.ModTilesEntities;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CarbonGeneratorTile extends TileEntity implements ITickableTileEntity, INBTSerializable<CompoundNBT> {

    private static final int NUMBER_OF_SLOTS = 2;
    private final ItemStackHandler itemStackHandler = createHandler();
    private final ModEnergyStorage energyStorage = createEnergy();

    private final LazyOptional<IItemHandler> itemHandler = LazyOptional.of(() -> this.itemStackHandler).cast();
    private final LazyOptional<IEnergyStorage> energyHandler = LazyOptional.of(() -> this.energyStorage).cast();

    public static int burnTime;

    public CarbonGeneratorTile() {
        super(ModTilesEntities.CARBON_GENERATOR_TILE.get());
    }

    @Override
    public void tick() {
        if (!world.isRemote) {
            ItemStack fuelItemStack = itemStackHandler.getStackInSlot(0);
            ItemStack chargingItemStack = itemStackHandler.getStackInSlot(1);
            if (this.isBurning()) {
                if (this.canStoreEnergy()) {
                    --burnTime;
                    energyStorage.addEnergy(GeneralConfig.CARBON_GENERATOR_GENERATE);
                    this.markDirty();
                }
            }

            if (!this.isBurning() && !fuelItemStack.isEmpty() && this.canStoreEnergy()) {
                itemStackHandler.extractItem(0, 1, false);
                burnTime = getBurnTimeTotal(fuelItemStack);
                this.markDirty();
            }

            if (!chargingItemStack.isEmpty() && energyStorage.canExtract() && !this.isEnergyEmpty()) {
                IEnergyStorage stackEnergyStorage = chargingItemStack.getCapability(CapabilityEnergy.ENERGY, null).orElse(null);
                if (stackEnergyStorage.getMaxEnergyStored() > stackEnergyStorage.getEnergyStored()){
                    stackEnergyStorage.receiveEnergy(GeneralConfig.CARBON_GENERATOR_GENERATE, false);
                    this.energyStorage.extractEnergy(GeneralConfig.CARBON_GENERATOR_GENERATE, false);
                }
            }
        }
    }


    private boolean isBurning() {
        return burnTime > 0;
    }


    public static boolean isFuel(ItemStack stack) {
        return net.minecraftforge.common.ForgeHooks.getBurnTime(stack) > 0;
    }

    public static boolean isChargeable(ItemStack itemstack) {
        return itemstack.getCapability(CapabilityEnergy.ENERGY).isPresent();
    }

    private int getBurnTimeTotal(ItemStack stack) {
        return net.minecraftforge.common.ForgeHooks.getBurnTime(stack);
    }

    private boolean canStoreEnergy(){
        return energyStorage.getMaxEnergyStored() > energyStorage.getEnergyStored();
    }

    private boolean isEnergyEmpty(){
        return energyStorage.getEnergyStored() <= 0;
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        CompoundNBT invTag = nbt.getCompound("inv");
        CompoundNBT energyTag = nbt.getCompound("energy");
        itemHandler.ifPresent(h -> ((INBTSerializable<CompoundNBT>) h).deserializeNBT(invTag));
        energyHandler.ifPresent(e -> ((INBTSerializable<CompoundNBT>) e).deserializeNBT(energyTag));
        super.read(state, nbt);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        itemHandler.ifPresent(h -> {
            CompoundNBT compoundNBT = ((INBTSerializable<CompoundNBT>) h).serializeNBT();
            compound.put("inv", compoundNBT);
        });
        energyHandler.ifPresent(e -> {
            CompoundNBT compoundNBT = ((INBTSerializable<CompoundNBT>) e).serializeNBT();
            compound.put("energy", compoundNBT);
        });
        return super.write(compound);
    }


    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return itemHandler.cast();
        if (cap == CapabilityEnergy.ENERGY) {
            return energyHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    private ItemStackHandler createHandler() {
        return new ItemStackHandler(NUMBER_OF_SLOTS) {
            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                if (slot == 0)
                    return isFuel(stack);
                if (slot == 1)
                    return isChargeable(stack);
                return false;
            }

            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                if (!isFuel(stack) && !isChargeable(stack))
                    return stack;
                return super.insertItem(slot, stack, simulate);
            }
        };
    }

    private ModEnergyStorage createEnergy() {
        return new ModEnergyStorage() {
            @Override
            protected void onEnergyChanged() {
                markDirty();
            }
        };
    }
}
