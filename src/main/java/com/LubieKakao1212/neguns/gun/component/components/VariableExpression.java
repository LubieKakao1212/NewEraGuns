package com.LubieKakao1212.neguns.gun.component.components;

import com.LubieKakao1212.neguns.capability.gun.IGun;
import com.LubieKakao1212.neguns.gun.component.IGunComponent;
import com.LubieKakao1212.qulib.util.entity.EntityChain;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

//TODO
public class VariableExpression implements IGunComponent {

    @Override
    public boolean executeAction(ItemStack gunStack, LivingEntity caster, IGun gun) {
        return false;
    }
}
