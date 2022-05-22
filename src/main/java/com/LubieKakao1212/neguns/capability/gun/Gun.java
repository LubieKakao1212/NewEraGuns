package com.LubieKakao1212.neguns.capability.gun;

import com.LubieKakao1212.neguns.data.AllTheData;
import com.LubieKakao1212.neguns.data.GunTypeInfo;
import com.mojang.datafixers.TypeRewriteRule;
import net.minecraft.resources.ResourceLocation;

public class Gun implements IGun {

    private GunTypeInfo type;

    public Gun(ResourceLocation typeId) {
        type = AllTheData.gunTypes.getOrDefault(typeId, AllTheData.DEFAULT_GUN_TYPE);
    }

    @Override
    public GunTypeInfo getGunType() {
        return type;
    }

}
