package com.LubieKakao1212.neguns.gun.component;

import com.LubieKakao1212.neguns.gun.state.GunState;
import com.LubieKakao1212.qulib.util.entity.EntityChain;
import net.minecraft.world.item.ItemStack;

public interface IGunComponent {

    boolean executeAction(ItemStack gun, EntityChain entityChain, GunState state);

}
