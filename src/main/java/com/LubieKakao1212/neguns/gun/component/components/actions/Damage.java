package com.LubieKakao1212.neguns.gun.component.components.actions;

import com.LubieKakao1212.neguns.capability.gun.IGun;
import com.LubieKakao1212.neguns.data.util.vars.DoubleOrExpression;
import com.LubieKakao1212.neguns.gun.component.IGunComponent;
import com.LubieKakao1212.neguns.gun.state.GunState;
import com.LubieKakao1212.qulib.util.entity.EntityChain;
import com.google.gson.annotations.SerializedName;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import javax.swing.text.html.parser.Entity;

public class Damage implements IGunComponent {

    private DoubleOrExpression damage = new DoubleOrExpression(1.);

    @SerializedName(value = "receiving-entity")
    private String targetEntity;

    @Override
    public boolean executeAction(ItemStack gunStack, EntityChain entityChain, IGun gun) {
        GunState state = gun.getState();

        Object var = state.get(targetEntity);

        if(var instanceof LivingEntity) {
            ((LivingEntity)var).hurt(DamageSource.GENERIC, damage.get(state, gun.getGunType().getEvaluator()).floatValue());
        }
        else {
            return false;
        }

        return true;
    }
}
