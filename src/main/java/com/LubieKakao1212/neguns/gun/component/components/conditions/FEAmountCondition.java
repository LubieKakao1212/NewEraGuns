package com.LubieKakao1212.neguns.gun.component.components.conditions;

import com.LubieKakao1212.neguns.data.behaviour.condition.IntCondition;
import com.LubieKakao1212.neguns.gun.component.IGunComponent;
import com.LubieKakao1212.neguns.gun.state.GunState;
import com.LubieKakao1212.qulib.util.entity.EntityChain;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public class FEAmountCondition implements IGunComponent {

    private IntCondition condition;

    public FEAmountCondition(IntCondition condition) {
        this.condition = condition;
    }

    @Override
    public boolean executeAction(ItemStack gun, EntityChain entityChain, GunState state) {
        LazyOptional<IEnergyStorage> energy = gun.getCapability(CapabilityEnergy.ENERGY);

        final boolean[] flag = { false };
        energy.ifPresent((fe) -> flag[0] = condition.solve(fe.getEnergyStored()));

        return flag[0];
    }

}
