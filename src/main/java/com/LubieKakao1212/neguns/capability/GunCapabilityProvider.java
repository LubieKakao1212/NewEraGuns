package com.LubieKakao1212.neguns.capability;

import com.LubieKakao1212.neguns.capability.energy.GunEnergyStorage;
import com.LubieKakao1212.neguns.capability.gun.Gun;
import com.LubieKakao1212.qulib.capability.energy.InternalEnergyStorage;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GunCapabilityProvider implements ICapabilitySerializable<CompoundTag> {

    private static String GUN_STATE_KEY = "GunState";

    private ResourceLocation gunTypeId;

    private LazyOptional<Gun> gunType;

    private LazyOptional<InternalEnergyStorage> energy;

    public GunCapabilityProvider(ItemStack gunStack, ResourceLocation gunTypeId, CompoundTag serialized) {
        this.gunTypeId = gunTypeId;

        energy = LazyOptional.of(() -> new GunEnergyStorage(gunStack,10000, 100, 10, 0));
        gunType = LazyOptional.of(() -> new Gun(gunTypeId));
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
        gunType.ifPresent((gun) -> {
            nbtOut.put(GUN_STATE_KEY, gun.serializeNBT());
        });
        return nbtOut;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        gunType.ifPresent((gun)-> {
            if(nbt.contains(GUN_STATE_KEY, Tag.TAG_COMPOUND)) {
                gun.deserializeNBT(nbt.getCompound(GUN_STATE_KEY));
            }
        });
    }
}
