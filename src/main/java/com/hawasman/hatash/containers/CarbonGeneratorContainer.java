package com.hawasman.hatash.containers;

import com.hawasman.hatash.TileEntities.CarbonGeneratorTile;
import com.hawasman.hatash.setup.ModContainers;
import com.hawasman.hatash.utils.inventory.SlotCharging;
import com.hawasman.hatash.utils.inventory.SlotFuel;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class CarbonGeneratorContainer extends Container {

    private TileEntity tileEntity;
    private IItemHandler playerInventory;
    private final IInventory generatorInventory;
    private final IIntArray generatorData;

    public CarbonGeneratorContainer(int windowId, PlayerInventory inv, PacketBuffer extraData) {

        this(windowId, inv.player.getEntityWorld(), extraData.readBlockPos(), inv, inv.player, new Inventory(2), new IntArray(1));
    }

    public CarbonGeneratorContainer(int windowId, World world, BlockPos pos, PlayerInventory playerInventory, PlayerEntity player, IInventory generatorInventory, IIntArray generatorData) {
        super(ModContainers.CARBON_GENERATOR_CONTAINER.get(), windowId);
        tileEntity = world.getTileEntity(pos);
        this.playerInventory = new InvWrapper(playerInventory);
        this.generatorData = generatorData;
        this.generatorInventory = generatorInventory;
        if (tileEntity != null && tileEntity instanceof CarbonGeneratorTile) {
            tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
                addSlot(new SlotFuel(h, 0, 80, 23));
                addSlot(new SlotCharging(h, 1, 146, 60));
            });
        }
        layoutPlayerInventorySlots(8, 84);
    }

    public CarbonGeneratorContainer(int i, World world, BlockPos pos, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        this(i, playerInventory.player.getEntityWorld(), pos, playerInventory, playerEntity, new Inventory(1), new IntArray(1));
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return this.generatorInventory.isUsableByPlayer(playerIn);
    }

    private int addSlotRange(IItemHandler handler, int index, int x, int y, int amount, int dx) {
        for (int i = 0; i < amount; i++) {
            addSlot(new SlotItemHandler(handler, index, x, y));
            x += dx;
            index++;
        }
        return index;
    }

    private int addSlotBox(IItemHandler handler, int index, int x, int y, int horAmount, int dx, int verAmount, int dy) {
        for (int j = 0; j < verAmount; j++) {
            index = addSlotRange(handler, index, x, y, horAmount, dx);
            y += dy;
        }
        return index;
    }

    private void layoutPlayerInventorySlots(int leftCol, int topRow) {
        // Player inventory
        addSlotBox(playerInventory, 9, leftCol, topRow, 9, 18, 3, 18);

        // Hotbar
        topRow += 58;
        addSlotRange(playerInventory, 0, leftCol, topRow, 9, 18);
    }

    public int getEnergy() {
        return tileEntity.getCapability(CapabilityEnergy.ENERGY).map(IEnergyStorage::getEnergyStored).orElse(0);
    }

    public int getBurnTime(){
        if ( tileEntity instanceof CarbonGeneratorTile){
            int burnTime =  ((CarbonGeneratorTile)tileEntity).burnTime;
            return burnTime;
        }
        return 0;
    }

    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (index != 0 && index != 1) {
                if (this.isFuel(itemstack1)) {
                    if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (this.isChargeable(itemstack1)) {
                    if (!this.mergeItemStack(itemstack1, 1, 2, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= 2 && index < 29) {
                    if (!this.mergeItemStack(itemstack1, 29, 38, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= 29 && index < 38 && !this.mergeItemStack(itemstack1, 2, 29, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemstack1, 2, 38, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, itemstack1);
        }

        return itemstack;
    }

    private boolean isChargeable(ItemStack itemstack) {
        return CarbonGeneratorTile.isChargeable(itemstack);
    }

    protected boolean isFuel(ItemStack stack) {
        return CarbonGeneratorTile.isFuel(stack);
    }

    @OnlyIn(Dist.CLIENT)
    public int getBurnLeftScaled() {
        int i = this.generatorData.get(1);
        if (i == 0) {
            i = 200;
        }

        return this.generatorData.get(0) * 13 / i;
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isBurning() {
        return this.generatorData.get(0) > 0;
    }
}
