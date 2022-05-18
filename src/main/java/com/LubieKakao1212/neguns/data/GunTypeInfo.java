package com.LubieKakao1212.neguns.data;

import net.minecraft.resources.ResourceLocation;

public class GunTypeInfo {

    //public GunHitBehaviour hit;

    private String model;

    private transient ResourceLocation modelLocation;

    public ResourceLocation getModel() {
        if(modelLocation == null) {
            modelLocation = new ResourceLocation(model);
        }
        return modelLocation;
    }
}
