package com.LubieKakao1212.neguns.capability;

import com.LubieKakao1212.neguns.capability.gun.Gun;
import com.LubieKakao1212.qulib.capability.energy.InternalEnergyStorage;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GunCapabilityProvider implements ICapabilitySerializable<CompoundTag> {

    private static final String ENERGY_TAG_ID = "energy";

    private ResourceLocation gunTypeId;

    private LazyOptional<Gun> gunType;

    private LazyOptional<InternalEnergyStorage> energy;

    public GunCapabilityProvider(ResourceLocation gunTypeId, CompoundTag serialized) {
        this.gunTypeId = gunTypeId;
        gunType = LazyOptional.of(() -> new Gun(gunTypeId));

        boolean energyInitialized = false;

        if(serialized != null) {
            if(serialized.contains(ENERGY_TAG_ID, Tag.TAG_INT)) {
                final int amount = serialized.getInt("energy");
                energy = LazyOptional.of(() -> new InternalEnergyStorage(10000, 100, 10, amount));
                energyInitialized = true;
            }
        }

        if(!energyInitialized) {
            energy = LazyOptional.of(() -> new InternalEnergyStorage(10000, 100, 10, 5000));
        }
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == GunCaps.GUN) {
            return (LazyOptional<T>) gunType;
        }
        if(cap == CapabilityEnergy.ENERGY) {
            return (LazyOptional<T>) energy;
        }
        return LazyOptional.empty();
    }
    
    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbtOut = new CompoundTag();
        energy.ifPresent((energyStorage) ->
                nbtOut.put(ENERGY_TAG_ID, energyStorage.serializeNBT())
        );
        return nbtOut;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {

    }
}
