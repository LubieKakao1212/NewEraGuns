package com.LubieKakao1212.modularguns.capability;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import org.joml.Quaterniond;
import org.joml.Vector3d;

import java.util.List;

public class Gun implements IGun {

    @Override
    public void shoot(Entity shooter, Vector3d pos, Quaterniond aim) {

    }

    @Override
    public List<ItemStack> getAttachments() {
        return null;
    }
}
