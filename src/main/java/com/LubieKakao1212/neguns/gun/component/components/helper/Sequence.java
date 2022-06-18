package com.LubieKakao1212.neguns.gun.component.components.helper;

import com.LubieKakao1212.neguns.gun.component.IGunComponent;
import com.LubieKakao1212.neguns.gun.state.GunState;
import com.LubieKakao1212.qulib.util.entity.EntityChain;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Sequence implements IGunComponent {

    private List<IGunComponent> sequence = new ArrayList<>();

    @Override
    public boolean executeAction(ItemStack gun, EntityChain entityChain, GunState state) {
        for(IGunComponent component : sequence) {
            if(!component.executeAction(gun, entityChain, state)) return false;
        }
        return true;
    }

    public void Add(IGunComponent cmp) {
        sequence.add(cmp);
    }


}
