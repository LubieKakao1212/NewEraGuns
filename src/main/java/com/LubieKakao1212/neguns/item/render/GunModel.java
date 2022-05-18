package com.LubieKakao1212.neguns.item.render;

import com.LubieKakao1212.neguns.item.GunItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class GunModel extends AnimatedGeoModel<GunItem> {

    private ResourceLocation modelResourceLocation;
    private ResourceLocation animationResourceLocation;
    private ResourceLocation textureResourceLocation;

    public GunModel(ResourceLocation modelId) {
        this.modelResourceLocation = new ResourceLocation(modelId.getNamespace(), "geo/"+modelId.getPath() + ".geo.json");
        this.animationResourceLocation = new ResourceLocation(modelId.getNamespace(), "animations/"+modelId.getPath() + ".animation.json");
        this.textureResourceLocation = new ResourceLocation(modelId.getNamespace(), "textures/"+modelId.getPath() + ".png");
    }

    @Override
    public ResourceLocation getModelLocation(GunItem object) {
        return modelResourceLocation;
    }

    @Override
    public ResourceLocation getTextureLocation(GunItem object) {
        return textureResourceLocation;
    }

    @Override
    public ResourceLocation getAnimationFileLocation(GunItem animatable) {
        return animationResourceLocation;
    }
}
