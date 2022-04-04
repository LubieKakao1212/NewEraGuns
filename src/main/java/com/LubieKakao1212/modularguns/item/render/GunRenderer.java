package com.LubieKakao1212.modularguns.item.render;

import com.LubieKakao1212.modularguns.ModularGunsMod;
import com.LubieKakao1212.modularguns.capability.GunCaps;
import com.LubieKakao1212.modularguns.capability.type.IGunType;
import com.LubieKakao1212.modularguns.item.GunItem;
import com.LubieKakao1212.modularguns.resources.MdGunsClientCache;
import com.LubieKakao1212.modularguns.resources.MdGunsResources;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransform;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;
import software.bernie.geckolib3.geo.exception.GeckoLibException;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

import java.util.HashMap;
import java.util.Map;

public class GunRenderer extends GeoItemRenderer<GunItem> {

    public GunRenderer() {
        super(MdGunsClientCache.defaultModel);
    }

    @Override
    public void renderByItem(ItemStack itemStack, ItemTransforms.TransformType transformType, PoseStack matrixStack, MultiBufferSource bufferIn, int combinedLightIn, int p_239207_6_) {
        LazyOptional<IGunType> gun = itemStack.getCapability(GunCaps.GUN_TYPE);

        final ResourceLocation[] transformsLocation = {new ResourceLocation(ModularGunsMod.MODID)};

        gun.ifPresent((gunType) -> {
            gunType.getGunType().ifPresent((typeInfo) -> transformsLocation[0] = typeInfo.getModel());
        });

        ItemTransforms transforms = MdGunsResources.getTransforms(transformsLocation[0]);

        PoseStack.Pose firstPose = matrixStack.last();
        matrixStack.popPose();
        PoseStack.Pose secondPose = matrixStack.last();
        matrixStack.pushPose();
        matrixStack.pushPose();

        matrixStack.setIdentity();

        ItemTransform transform = transforms.getTransform(transformType);

        if(transformType == ItemTransforms.TransformType.FIXED) {
            matrixStack.mulPoseMatrix(secondPose.pose());
            transform.apply(false, matrixStack);
            matrixStack.translate(-0.5, -0.5, -0.5);
        }else {
            transform.apply(false, matrixStack);
            //matrixStack.mulPoseMatrix(secondPose.pose());
            matrixStack.mulPoseMatrix(firstPose.pose());
        }
        /*if(transformType == ItemTransforms.TransformType.GUI) {
            PoseStack.Pose pose = matrixStack.last();

            Matrix4f matrixTRS = Matrix4f.createTranslateMatrix(transform.translation.x(), transform.translation.y(), transform.translation.z());
            matrixTRS.multiply(new Quaternion(transform.rotation.x(), transform.rotation.y(), transform.rotation.z(), true));
            matrixTRS.multiply(Matrix4f.createScaleMatrix(transform.scale.x(), transform.scale.y(), transform.scale.z()));

            pose.pose().multiplyBackward(matrixTRS);
        }else
        {
            transform.apply(false, matrixStack);
        }
*/
        super.renderByItem(itemStack, transformType, matrixStack, bufferIn, combinedLightIn, p_239207_6_);

        matrixStack.popPose();
        matrixStack.mulPoseMatrix(secondPose.pose());
    }

    @Override
    public void render(GunItem animatable, PoseStack stack, MultiBufferSource bufferIn, int packedLightIn, ItemStack itemStack) {
        LazyOptional<IGunType> gun = itemStack.getCapability(GunCaps.GUN_TYPE);

        final ResourceLocation[] modelLocation = {new ResourceLocation(ModularGunsMod.MODID, "potato")};

        gun.ifPresent((gunType) -> {
            gunType.getGunType().ifPresent((typeInfo) -> modelLocation[0] = typeInfo.getModel());
        });

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
