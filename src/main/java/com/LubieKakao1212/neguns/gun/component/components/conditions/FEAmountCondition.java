package com.LubieKakao1212.neguns.gun.component.components.conditions;

import com.LubieKakao1212.neguns.capability.gun.IGun;
import com.LubieKakao1212.neguns.gun.component.IGunComponent;
import com.LubieKakao1212.neguns.data.util.condition.IntCondition;
import com.LubieKakao1212.neguns.gun.state.GunState;
import com.LubieKakao1212.qulib.util.entity.EntityChain;
import com.fathzer.soft.javaluator.AbstractEvaluator;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public class FEAmountCondition implements IGunComponent {

    private IntCondition condition = null;
    private IntCondition expression = null;

    public FEAmountCondition(IntCondition condition) {
        this.condition = condition;
    }

    @Override
    public boolean executeAction(ItemStack gunStack, LivingEntity caster, IGun gun) {
        LazyOptional<IEnergyStorage> energy = gunStack.getCapability(CapabilityEnergy.ENERGY);

        final boolean[] flag = { false };
        if(condition != null) {
            energy.ifPresent((fe) -> flag[0] = condition.solve(fe.getEnergyStored()));
        }else if(expression != null)
        {
            energy.ifPresent((fe) -> flag[0] = expression.solve(fe.getEnergyStored()));
        }

        return flag[0];
    }

}