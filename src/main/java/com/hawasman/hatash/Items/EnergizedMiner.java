package com.hawasman.hatash.Items;

import com.hawasman.hatash.capabilities.ModCapabilityProvider;
import com.hawasman.hatash.config.Configuration;
import net.minecraft.block.BlockState;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class EnergizedMiner extends EnergizedItem {


    public EnergizedMiner() {
        super();
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        IEnergyStorage energyStorage = stack.getCapability(CapabilityEnergy.ENERGY, null).orElse(null);

        boolean sneakPressed = Screen.hasShiftDown();

        tooltip.add(new StringTextComponent("Energy:" + energyStorage.getEnergyStored()  + "/" + Configuration.GENERAL_CONFIG.MAX_ENERGY));
        if (!sneakPressed) {
            tooltip.add(new TranslationTextComponent("item.hatash.tooltip.show_details"));
        }else{
            tooltip.add(new StringTextComponent("Energy Cost:" + Configuration.GENERAL_CONFIG.MAX_COST));
        }
    }

}
