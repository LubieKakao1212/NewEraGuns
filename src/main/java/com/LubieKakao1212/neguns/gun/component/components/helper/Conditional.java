package com.LubieKakao1212.neguns.gun.component.components.helper;

import com.LubieKakao1212.neguns.capability.gun.IGun;
import com.LubieKakao1212.neguns.gun.component.IGunComponent;
import com.LubieKakao1212.neguns.gun.component.components.FallbackComponent;
import com.LubieKakao1212.neguns.gun.state.GunState;
import com.LubieKakao1212.qulib.util.entity.EntityChain;
import com.fathzer.soft.javaluator.AbstractEvaluator;
import com.google.gson.annotations.SerializedName;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class Conditional implements IGunComponent {

    private IGunComponent condition = new FallbackComponent("No condition set in Conditional component, will always succeed");

    @SerializedName("trigger_success")
    private IGunComponent success = new FallbackComponent();

    @SerializedName("trigger_fail")
    private IGunComponent fail = new FallbackComponent();

    @Override
    public boolean executeAction(ItemStack gun, EntityChain entityChain, IGun state) {
        if (!condition.executeAction(gun, entityChain, state))
            return fail.executeAction(gun, entityChain, state);
        return success.executeAction(gun, entityChain, state);
    }
}
