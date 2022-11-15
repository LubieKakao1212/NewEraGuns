package com.LubieKakao1212.neguns.gun.component.components.actions;

import com.LubieKakao1212.neguns.NewEraGunsMod;
import com.LubieKakao1212.neguns.capability.gun.IGun;
import com.LubieKakao1212.neguns.data.util.vars.DoubleOrExpression;
import com.LubieKakao1212.neguns.gun.component.IGunComponent;
import com.LubieKakao1212.neguns.gun.state.GunState;
import com.LubieKakao1212.qulib.capability.energy.InternalEnergyStorage;
import com.LubieKakao1212.qulib.util.entity.EntityChain;
import com.fathzer.soft.javaluator.AbstractEvaluator;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public class FEDrain implements IGunComponent {

    private DoubleOrExpression amount = new DoubleOrExpression();

    @Override
    public boolean executeAction(ItemStack gunStack, EntityChain entityChain, IGun gun) {
        LazyOptional<IEnergyStorage> energyCapability = gunStack.getCapability(CapabilityEnergy.ENERGY);
        if(energyCapability.isPresent()) {
            IEnergyStorage energyStorage = energyCapability.resolve().get();
            if(energyStorage instanceof InternalEnergyStorage) {
                InternalEnergyStorage energy = (InternalEnergyStorage) energyStorage;
                energy.extractEnergyInternal(amount.get(gun.getState(), gun.getGunType().getEvaluator()).intValue(), false);
            }else
            {
                //TODO better message
                NewEraGunsMod.LOGGER.info("Gun is probably not a gun.");
            }
        }else
        {
            NewEraGunsMod.LOGGER.info("Attempting to drain energy from a gun that doesn't have it");
        }
        return true;
    }
}
