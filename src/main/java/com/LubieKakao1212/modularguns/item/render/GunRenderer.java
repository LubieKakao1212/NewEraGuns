package com.LubieKakao1212.modularguns.item.render;

import com.LubieKakao1212.modularguns.ModularGunsMod;
import com.LubieKakao1212.modularguns.capability.GunCaps;
import com.LubieKakao1212.modularguns.capability.gun.IGun;
import com.LubieKakao1212.modularguns.item.GunItem;
import com.LubieKakao1212.modularguns.resources.MdGunsClientCache;
import com.LubieKakao1212.modularguns.resources.MdGunsResources;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransform;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;
import software.bernie.geckolib3.geo.exception.GeckoLibException;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class GunRenderer extends GeoItemRenderer<GunItem> {

    public GunRenderer() {
        super(MdGunsClientCache.defaultModel);
    }

    @Override
    public void renderByItem(ItemStack itemStack, ItemTransforms.TransformType transformType, PoseStack matrixStack, MultiBufferSource bufferIn, int combinedLightIn, int p_239207_6_) {
        LazyOptional<IGun> gun = itemStack.getCapability(GunCaps.GUN);

        final ResourceLocation[] transformsLocation = {new ResourceLocation(ModularGunsMod.MODID)};

        gun.ifPresent((gunType) ->
            transformsLocation[0] = gunType.getGunType().getModel()
        );

        ItemTransforms transforms = MdGunsResources.getTransforms(transformsLocation[0]);

        PoseStack.Pose firstPose = matrixStack.last();
        matrixStack.popPose();
        PoseStack.Pose secondPose = matrixStack.last();
        matrixStack.pushPose();
        matrixStack.setIdentity();
        matrixStack.pushPose();

        ItemTransform transform = transforms.getTransform(transformType);

        if(transformType == ItemTransforms.TransformType.FIXED) {
            //Applying transforms for ItemFrame rendering
            matrixStack.mulPoseMatrix(secondPose.pose());
            transform.apply(false, matrixStack);
            matrixStack.translate(-0.5, -0.5, -0.5);
        }else {
            //Applying transforms for standard rendering
            transform.apply(false, matrixStack);
            matrixStack.mulPoseMatrix(firstPose.pose());
        }

        super.renderByItem(itemStack, transformType, matrixStack, bufferIn, combinedLightIn, p_239207_6_);

        //Returning to original transformation
        matrixStack.popPose();
        matrixStack.mulPoseMatrix(secondPose.pose());
    }

    @Override
    public void render(GunItem animatable, PoseStack stack, MultiBufferSource bufferIn, int packedLightIn, ItemStack itemStack) {
        LazyOptional<IGun> gun = itemStack.getCapability(GunCaps.GUN);

        final ResourceLocation[] modelLocation = {new ResourceLocation(ModularGunsMod.MODID, "potato")};

        gun.ifPresent((gunType) ->
            modelLocation[0] = gunType.getGunType().getModel()
        );

        GunModel model = MdGunsClientCache.getOrCreateModel(modelLocation[0]);

        setModel(model);

        try
        {
            super.render(animatable, stack, bufferIn, packedLightIn, itemStack);
        }
        catch (GeckoLibException e)
        {
            setModel(MdGunsClientCache.defaultModel);
            super.render(animatable, stack, bufferIn, packedLightIn, itemStack);
        }
    }
}
