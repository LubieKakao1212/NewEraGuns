package com.LubieKakao1212.modularguns.capability;

import com.LubieKakao1212.modularguns.capability.gun.Gun;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GunCapabilityProvider implements ICapabilitySerializable<CompoundTag> {

    private ResourceLocation gunTypeId;

    private LazyOptional<Gun> gunType;

    public GunCapabilityProvider(ResourceLocation gunTypeId) {
        this.gunTypeId = gunTypeId;
        gunType = LazyOptional.of(() -> new Gun(gunTypeId));
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == GunCaps.GUN) {
            return (LazyOptional<T>) gunType;
        }
        return LazyOptional.empty();
    }
    
    @Override
    public CompoundTag serializeNBT() {
        return new CompoundTag();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {

    }
}
