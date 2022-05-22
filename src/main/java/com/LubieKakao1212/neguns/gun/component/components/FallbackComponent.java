package com.LubieKakao1212.neguns.gun.component.components;

import com.LubieKakao1212.neguns.gun.component.IGunComponent;
import com.LubieKakao1212.neguns.gun.dummy.EntityChain;
import com.LubieKakao1212.neguns.gun.state.GunState;
import net.minecraft.world.item.ItemStack;

public class FallbackComponent implements IGunComponent {

    @Override
    public boolean executeAction(ItemStack gun, EntityChain entityChain, GunState state) {
        return true;
    }

}
