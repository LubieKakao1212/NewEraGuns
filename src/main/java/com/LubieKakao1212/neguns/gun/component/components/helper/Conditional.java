package com.LubieKakao1212.neguns.gun.component.components.helper;

import com.LubieKakao1212.neguns.gun.component.IGunComponent;
import com.LubieKakao1212.neguns.gun.dummy.EntityChain;
import com.LubieKakao1212.neguns.gun.state.GunState;
import com.google.gson.annotations.SerializedName;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Conditional implements IGunComponent {

    private List<IGunComponent> conditions = new ArrayList<>();

    @SerializedName("trigger_success")
    private IGunComponent success;

    @SerializedName("trigger_fail")
    private IGunComponent fail;

    @Override
    public boolean executeAction(ItemStack gun, EntityChain entityChain, GunState state) {
        for(IGunComponent condition : conditions) {
            if (!condition.executeAction(gun, entityChain, state)) {
                return fail.executeAction(gun, entityChain, state);
            }
        }
        return success.executeAction(gun, entityChain, state);
    }

}