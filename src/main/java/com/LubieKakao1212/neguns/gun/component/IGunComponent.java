package com.LubieKakao1212.neguns.gun.component;

import com.LubieKakao1212.neguns.gun.dummy.EntityChain;
import com.LubieKakao1212.neguns.gun.state.GunState;
import net.minecraft.world.item.ItemStack;

public interface IGunComponent {

    boolean executeAction(ItemStack gun, EntityChain entityChain, GunState state);

}
