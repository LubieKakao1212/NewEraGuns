package com.LubieKakao1212.modularguns.item.render;

import com.LubieKakao1212.modularguns.ModularGunsMod;
import com.LubieKakao1212.modularguns.capability.GunCaps;
import com.LubieKakao1212.modularguns.capability.type.IGunType;
import com.LubieKakao1212.modularguns.item.GunItem;
import com.LubieKakao1212.modularguns.resources.MdGunsResources;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;
import software.bernie.geckolib3.geo.exception.GeckoLibException;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

import java.util.HashMap;
import java.util.Map;

public class GunRenderer extends GeoItemRenderer<GunItem> {

    private static Map<ResourceLocation, GunModel> modelCache = new HashMap<>();
    private GunModel defaultModel;


    public GunRenderer(ResourceLocation defaultModelId) {
        super(new GunModel(defaultModelId));
        defaultModel = (GunModel) modelProvider;
        modelCache.put(defaultModelId, defaultModel);
    }

    @Override
    public void renderByItem(ItemStack itemStack, ItemTransforms.TransformType transform, PoseStack matrixStack, MultiBufferSource bufferIn, int combinedLightIn, int p_239207_6_) {
        LazyOptional<IGunType> gun = itemStack.getCapability(GunCaps.GUN_TYPE);

        final ResourceLocation[] transformsLocation = {new ResourceLocation(ModularGunsMod.MODID)};

        gun.ifPresent((gunType) -> {
            gunType.getGunType().ifPresent((typeInfo) -> transformsLocation[0] = typeInfo.getModel());
        });

        ItemTransforms transforms = MdGunsResources.getTransforms(transformsLocation[0]);

        matrixStack.pushPose();

        transforms.getTransform(transform).apply(false, matrixStack);

        super.renderByItem(itemStack, transform, matrixStack, bufferIn, combinedLightIn, p_239207_6_);

        matrixStack.popPose();
    }

    @Override
    public void render(GunItem animatable, PoseStack stack, MultiBufferSource bufferIn, int packedLightIn, ItemStack itemStack) {
        LazyOptional<IGunType> gun = itemStack.getCapability(GunCaps.GUN_TYPE);

        final ResourceLocation[] modelLocation = {new ResourceLocation(ModularGunsMod.MODID, "potato")};

        gun.ifPresent((gunType) -> {
            gunType.getGunType().ifPresent((typeInfo) -> modelLocation[0] = typeInfo.getModel());
        });

        GunModel model = resolveModel(modelLocation[0]);

        setModel(model);

        try
        {
            super.render(animatable, stack, bufferIn, packedLightIn, itemStack);
        }
        catch (GeckoLibException e)
        {
            setModel(defaultModel);
            super.render(animatable, stack, bufferIn, packedLightIn, itemStack);
        }
    }

    private GunModel resolveModel(ResourceLocation id) {
        GunModel model = modelCache.get(id);

        if(model == null) {
            model = new GunModel(id);
            modelCache.put(id, model);
        }
        return model;
    }
}
