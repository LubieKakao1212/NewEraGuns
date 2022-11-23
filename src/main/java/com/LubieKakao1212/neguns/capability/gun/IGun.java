package com.LubieKakao1212.neguns.capability.gun;

import com.LubieKakao1212.neguns.data.GunTypeInfo;
import com.LubieKakao1212.neguns.gun.state.GunState;
import com.LubieKakao1212.neguns.gun.state.IGunStateProvider;
import net.minecraft.world.entity.LivingEntity;

public interface IGun {

    GunTypeInfo getGunType();

    void applyProvidedState(LivingEntity caster);

    void registerStateProvider(IGunStateProvider provider);

    GunState getState();

}
