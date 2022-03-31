package com.LubieKakao1212.modularguns.capability.type;

import com.LubieKakao1212.modularguns.data.GunTypeInfo;
import net.minecraftforge.common.util.LazyOptional;

public interface IGunType {

    LazyOptional<GunTypeInfo> getGunType();

}
