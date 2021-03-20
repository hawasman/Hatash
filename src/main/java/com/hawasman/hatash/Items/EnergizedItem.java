package com.hawasman.hatash.Items;

import com.hawasman.hatash.capabilities.ModCapabilityProvider;
import com.hawasman.hatash.config.Configuration;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class EnergizedItem extends Item {
    private int energyCapacity;
    private float efficiency;

    public EnergizedItem() {
        super(new Item.Properties().maxStackSize(1).setNoRepair().group(ItemGroup.TOOLS));
        this.energyCapacity = (int) Configuration.GENERAL_CONFIG.MAX_ENERGY;
        this.efficiency = Configuration.GENERAL_CONFIG.MINER_EFFICIENCY;
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return this.energyCapacity;
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        IEnergyStorage energyStorage = stack.getCapability(CapabilityEnergy.ENERGY, null).orElse(null);
        return (energyStorage.getEnergyStored() < energyStorage.getMaxEnergyStored());
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return new ModCapabilityProvider(stack, (int) Configuration.GENERAL_CONFIG.MAX_ENERGY);
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        return stack.getCapability(CapabilityEnergy.ENERGY, null)
                .map(e -> 1D - (e.getEnergyStored() / (double) e.getMaxEnergyStored()))
                .orElse(0D);
    }

    @Override
    public int getRGBDurabilityForDisplay(ItemStack stack) {
        return stack.getCapability(CapabilityEnergy.ENERGY)
                .map(e -> MathHelper.hsvToRGB(Math.max(0.0F, (float) e.getEnergyStored() / (float) e.getMaxEnergyStored()) / 3.0F, 1.0F, 1.0F))
                .orElse(super.getRGBDurabilityForDisplay(stack));
    }

    @Override
    public void fillItemGroup(@Nonnull ItemGroup group, @Nonnull NonNullList<ItemStack> items) {
        super.fillItemGroup(group, items);
        if (!isInGroup(group))
            return;

        ItemStack charged = new ItemStack(this);
        charged.getOrCreateTag().putDouble("energy", Configuration.GENERAL_CONFIG.MAX_ENERGY);
        items.add(charged);
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        IEnergyStorage energyStorage = stack.getCapability(CapabilityEnergy.ENERGY).orElse(null);
        return energyStorage.getEnergyStored() > 0 ? this.efficiency : 1;
    }

    @Override
    public boolean canHarvestBlock(ItemStack stack, BlockState state) {
        return true;
    }
}
