package com.LubieKakao1212.neguns.capability.gun;

import com.LubieKakao1212.neguns.data.AllTheData;
import com.LubieKakao1212.neguns.data.GunTypeInfo;
import com.LubieKakao1212.neguns.gun.state.GunState;
import com.LubieKakao1212.neguns.gun.state.IGunStateProvider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.HashSet;
import java.util.Set;

public class Gun implements IGun, INBTSerializable<CompoundTag> {

    private GunTypeInfo type;
    private Set<IGunStateProvider> stateProviders = new HashSet<>();
    private GunState state = new GunState();

    public Gun(ResourceLocation typeId) {
        type = AllTheData.gunTypes.getOrDefault(typeId, AllTheData.DEFAULT_GUN_TYPE);
    }

    @Override
    public GunTypeInfo getGunType() {
        return type;
    }

    public void applyProvidedState() {
        for(IGunStateProvider provider : stateProviders)
        {
            provider.addVariables(state);
        }
    }

    public void registerStateProvider(IGunStateProvider provider) {
        stateProviders.add(provider);
    }

    @Override
    public GunState getState() {
        return state;
    }

    @Override
    public CompoundTag serializeNBT() {
        return state.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        state.deserializeNBT(nbt);
    }

}
