package com.LubieKakao1212.modularguns.capability;

import com.LubieKakao1212.modularguns.capability.type.GunType;
import com.LubieKakao1212.modularguns.data.GunTypeInfo;
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

    private LazyOptional<GunType> gunType;

    public GunCapabilityProvider(ResourceLocation gunTypeId) {
        this.gunTypeId = gunTypeId;
        gunType = LazyOptional.of(() -> new GunType(gunTypeId));
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == GunCaps.GUN_TYPE) {
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
