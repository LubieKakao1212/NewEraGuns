package com.LubieKakao1212.neguns.data;

import com.LubieKakao1212.neguns.capability.gun.IGun;
import com.LubieKakao1212.neguns.gun.component.IGunComponent;
import com.LubieKakao1212.neguns.gun.dummy.EntityChain;
import com.LubieKakao1212.neguns.gun.state.GunState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class GunTypeInfo {

    private String model;

    private transient ResourceLocation modelLocation;

    private List<IGunComponent> trigger;

    public ResourceLocation getModel() {
        if(modelLocation == null) {
            modelLocation = new ResourceLocation(model);
        }
        return modelLocation;
    }

    public boolean trigger(ItemStack gun, EntityChain entityChain, GunState state) {
        for(IGunComponent component : trigger) {
            component.executeAction(gun, entityChain, state);
        }
        return true;
    }

}
