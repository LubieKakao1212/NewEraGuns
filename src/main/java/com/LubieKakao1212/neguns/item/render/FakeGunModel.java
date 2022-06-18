package com.LubieKakao1212.neguns.item.render;

import com.LubieKakao1212.neguns.NewEraGunsMod;
import com.LubieKakao1212.neguns.item.GunItem;
import com.LubieKakao1212.neguns.resources.NEGunsDataCache;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class FakeGunModel extends AnimatedGeoModel<GunItem> {

    private static final ResourceLocation modelResourceLocation = new ResourceLocation(NewEraGunsMod.MODID, "geo/fake.geo.json");
    private static final ResourceLocation textureResourceLocation = new ResourceLocation(NewEraGunsMod.MODID, "textures/fake.png");

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
        return NEGunsDataCache.FAKE_ANIMATION_ID;
    }

}
