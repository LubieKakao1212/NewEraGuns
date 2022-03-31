package com.LubieKakao1212.modularguns.capability;

import com.LubieKakao1212.modularguns.capability.type.IGunType;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class GunCaps {

    public static final Capability<IGunType> GUN_TYPE = CapabilityManager.get(new CapabilityToken<IGunType>() { });

}
