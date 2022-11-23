package com.LubieKakao1212.neguns.gun.component.components.helper;

import com.LubieKakao1212.neguns.capability.gun.IGun;
import com.LubieKakao1212.neguns.gun.component.IGunComponent;
import com.LubieKakao1212.neguns.gun.state.GunState;
import com.LubieKakao1212.qulib.util.entity.EntityChain;
import com.fathzer.soft.javaluator.AbstractEvaluator;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class CounterExec implements IGunComponent {

    private String counterVar = "hT";
    private double cooldown = 2f;

    private IGunComponent trigger;

    @Override
    public boolean executeAction(ItemStack gunStack, LivingEntity caster, IGun gun) {
        Double var = (Double)gun.getState().get(counterVar);
        var++;

        boolean lastResult = false;

        while(var > cooldown) {
            var -= cooldown;
            lastResult = trigger.executeAction(gunStack, caster, gun);
        }

        gun.getState().put(counterVar, var);
        return lastResult;
    }
}
