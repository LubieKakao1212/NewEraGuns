package com.LubieKakao1212.modularguns.item;

import com.LubieKakao1212.modularguns.capability.GunCapabilityProvider;
import com.LubieKakao1212.modularguns.data.AllTheData;
import com.LubieKakao1212.modularguns.data.GunType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.Nullable;

public class GunItem extends Item {


    public GunItem(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        if(nbt != null) {
            ResourceLocation gunTypeId = new ResourceLocation(nbt.getString("gunType"));
            return new GunCapabilityProvider(gunTypeId);
        }
        return null;
    }
}
