package com.LubieKakao1212.neguns.gun.component.components.conditions;

import com.LubieKakao1212.neguns.capability.gun.IGun;
import com.LubieKakao1212.neguns.expression.EvaluationException;
import com.LubieKakao1212.neguns.expression.MultiTypeEvaluator;
import com.LubieKakao1212.neguns.gun.component.IGunComponent;
import com.LubieKakao1212.qulib.util.entity.EntityChain;
import net.minecraft.advancements.critereon.EntityHurtPlayerTrigger;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class Condition implements IGunComponent {

    private String expression;

    @Override
    public boolean executeAction(ItemStack gunStack, LivingEntity caster, IGun gun) {
        try {
            return MultiTypeEvaluator.safeBoolean(gun.getGunType().getEvaluator().evaluate(expression, gun.getState()));
        } catch (EvaluationException e)
        {
            Player player = (Player)caster;
            player.sendMessage(new TextComponent(e.getMessage()), player.getUUID());
            return false;
        }
    }
}
