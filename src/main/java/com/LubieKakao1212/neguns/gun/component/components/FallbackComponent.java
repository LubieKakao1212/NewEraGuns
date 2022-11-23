package com.LubieKakao1212.neguns.gun.component.components;

import com.LubieKakao1212.neguns.NewEraGunsMod;
import com.LubieKakao1212.neguns.capability.gun.IGun;
import com.LubieKakao1212.neguns.gun.component.IGunComponent;
import com.LubieKakao1212.neguns.gun.state.GunState;
import com.LubieKakao1212.qulib.util.entity.EntityChain;
import com.fathzer.soft.javaluator.AbstractEvaluator;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class FallbackComponent implements IGunComponent {

    private String message;

    public FallbackComponent() {
        message = null;
    }

    public FallbackComponent(String message) {
        this.message = message;
        NewEraGunsMod.LOGGER.warn(message);
    }

    @Override
    public boolean executeAction(ItemStack gun, LivingEntity caster, IGun state) {
        NewEraGunsMod.LOGGER.warn(message);
        return true;
    }

}
