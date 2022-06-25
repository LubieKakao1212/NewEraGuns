package com.LubieKakao1212.neguns.capability.energy;

import com.LubieKakao1212.qulib.capability.energy.InternalEnergyStorage;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class GunEnergyStorage extends InternalEnergyStorage {

    private static final String ENERGY_TAG_ID = "energy";

    private ItemStack stack;

    public GunEnergyStorage(ItemStack stack, int capacity) {
        this(stack, capacity, capacity);
    }

    public GunEnergyStorage(ItemStack stack, int capacity, int maxTransfer) {
        this(stack, capacity, maxTransfer, maxTransfer);
    }

    public GunEnergyStorage(ItemStack stack, int capacity, int maxReceive, int maxExtract) {
        this(stack, capacity, maxReceive, maxExtract, 0);
    }

    public GunEnergyStorage(ItemStack stack, int capacity, int maxReceive, int maxExtract, int energy) {
        super(capacity, maxReceive, maxExtract, energy);
        this. stack = stack;
        readStackTag();
        writeStackTag();
    }

    @Override
    public int extractEnergyInternal(int maxExtract, boolean simulate) {
        int out = super.extractEnergyInternal(maxExtract, simulate);
        writeStackTag();
        return out;
    }

    @Override
    public int receiveEnergyInternal(int maxReceive, boolean simulate) {
        int out = super.receiveEnergyInternal(maxExtract, simulate);
        writeStackTag();
        return out;
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        int out = super.receiveEnergy(maxExtract, simulate);
        writeStackTag();
        return out;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        int out = super.extractEnergy(maxExtract, simulate);
        writeStackTag();
        return out;
    }

    @Override
    public int getEnergyStored() {
        return readStackTag();
    }

    private void writeStackTag() {
        if(!stack.hasTag()) {
            stack.setTag(new CompoundTag());
        }
        stack.getTag().putInt(ENERGY_TAG_ID, energy);
    }

    private int readStackTag() {
        if(stack.hasTag()) {
            energy = stack.getTag().getInt(ENERGY_TAG_ID);
            return energy;
        }
        return 0;
    }
}
