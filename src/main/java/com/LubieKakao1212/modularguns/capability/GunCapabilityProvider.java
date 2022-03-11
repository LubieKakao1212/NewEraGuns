package com.LubieKakao1212.modularguns.capability;

import com.LubieKakao1212.modularguns.data.GunType;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GunCapabilityProvider implements ICapabilitySerializable<CompoundTag> {

    private GunType gunType;
    private ResourceLocation gunTypeId;

    public GunCapabilityProvider(ResourceLocation gunTypeId) {
        this.gunTypeId = gunTypeId;
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
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
