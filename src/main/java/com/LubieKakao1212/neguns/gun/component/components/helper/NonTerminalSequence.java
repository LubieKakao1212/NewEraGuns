package com.LubieKakao1212.neguns.gun.component.components.helper;

import com.LubieKakao1212.neguns.capability.gun.IGun;
import com.LubieKakao1212.neguns.gun.component.IGunComponent;
import com.LubieKakao1212.neguns.gun.state.GunState;
import com.LubieKakao1212.qulib.util.entity.EntityChain;
import com.fathzer.soft.javaluator.AbstractEvaluator;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class NonTerminalSequence implements IGunComponent {

    private List<IGunComponent> sequence = new ArrayList<>();

    @Override
    public boolean executeAction(ItemStack gunStack, EntityChain entityChain, IGun gun) {
        for (IGunComponent cmp : sequence) {
            cmp.executeAction(gunStack, entityChain, gun);
        }
        return true;
    }

}
