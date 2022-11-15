package com.LubieKakao1212.neguns.gun.component.components.helper;

import com.LubieKakao1212.neguns.capability.gun.IGun;
import com.LubieKakao1212.neguns.data.util.vars.DoubleOrExpression;
import com.LubieKakao1212.neguns.gun.component.IGunComponent;
import com.LubieKakao1212.neguns.gun.state.GunState;
import com.LubieKakao1212.qulib.util.entity.EntityChain;
import net.minecraft.world.item.ItemStack;

public class DefaultPierce implements IGunComponent {

    public DoubleOrExpression amount = new DoubleOrExpression(1.);

    @Override
    public boolean executeAction(ItemStack gunStack, EntityChain entityChain, IGun gun) {
        GunState state = gun.getState();

        Object pierce = state.get("pierce");

        if(pierce instanceof Double) {
            state.putTemporary("pierce", ((Double)pierce) - amount.get(state, gun.getGunType().getEvaluator()));
            return true;
        }
        else
            return false;
    }
}
