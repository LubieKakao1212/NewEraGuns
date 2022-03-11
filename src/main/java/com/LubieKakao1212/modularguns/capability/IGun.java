package com.LubieKakao1212.modularguns.capability;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import org.joml.Quaterniond;
import org.joml.Vector3d;

import java.util.List;

public interface IGun {

    void shoot(Entity shooter, Vector3d pos, Quaterniond aim);

    ResourceLocation getModelId(ItemStack stack);

    List<ItemStack> getAttachments();

}
