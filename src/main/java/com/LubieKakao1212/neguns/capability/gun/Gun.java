package com.LubieKakao1212.neguns.capability.gun;

import com.LubieKakao1212.neguns.data.AllTheData;
import com.LubieKakao1212.neguns.data.GunTypeInfo;
import com.LubieKakao1212.neguns.gun.state.GunState;
import com.LubieKakao1212.neguns.gun.state.IGunStateProvider;
import com.LubieKakao1212.qulib.math.AimUtil;
import com.LubieKakao1212.qulib.math.MathUtil;
import com.LubieKakao1212.qulib.util.joml.Vector3dUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.HashSet;
import java.util.Set;

public class Gun implements IGun, INBTSerializable<CompoundTag> {

    private ResourceLocation typeId;
    private GunTypeInfo type = null;
    private Set<IGunStateProvider> stateProviders = new HashSet<>();
    private GunState state = new GunState();

    public Gun(ResourceLocation typeId) {
        this.typeId = typeId;
    }

    public void acquireGunType() {
        //TODO refresh stack capabilities
        type = AllTheData.gunTypes.getOrDefault(typeId, AllTheData.DEFAULT_GUN_TYPE);
    }

    @Override
    public GunTypeInfo getGunType() {
        if(type == null || !type.isValid()) {
            acquireGunType();
        }
        return type;
    }

    @Override
    public void applyProvidedState(LivingEntity caster) {
        for(IGunStateProvider provider : stateProviders)
        {
            provider.addVariables(state);
        }
        state.pushScope("caster");

        state.putTemporary("pos", Vector3dUtil.of(caster.position()));
        state.putTemporary("eye_pos", Vector3dUtil.of(caster.getEyePosition()));
        state.putTemporary("aim", Vector3dUtil.of(caster.getLookAngle()));

        state.popScope("caster");
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
