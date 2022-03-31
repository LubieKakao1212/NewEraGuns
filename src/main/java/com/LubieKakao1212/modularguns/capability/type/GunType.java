package com.LubieKakao1212.modularguns.capability.type;

import com.LubieKakao1212.modularguns.data.AllTheData;
import com.LubieKakao1212.modularguns.data.GunTypeInfo;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.common.util.LazyOptional;

public class GunType implements IGunType {

    private LazyOptional<GunTypeInfo> type;

    public GunType(ResourceLocation typeId) {
        type = LazyOptional.of(() -> AllTheData.gunTypes.get(typeId));
    }

    @Override
    public LazyOptional<GunTypeInfo> getGunType() {
        return type;
    }

}
