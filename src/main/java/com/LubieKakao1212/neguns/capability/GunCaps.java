package com.LubieKakao1212.neguns.capability;

import com.LubieKakao1212.neguns.capability.gun.IGun;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class GunCaps {

    public static final Capability<IGun> GUN = CapabilityManager.get(new CapabilityToken<IGun>() { });

}
