package com.LubieKakao1212.neguns.gun.component.components.actions;

import com.LubieKakao1212.neguns.capability.gun.IGun;
import com.LubieKakao1212.neguns.data.util.vars.DoubleOrExpression;
import com.LubieKakao1212.neguns.data.util.vars.VectorOrExpression;
import com.LubieKakao1212.neguns.gun.component.IGunComponent;
import com.LubieKakao1212.neguns.gun.state.GunState;
import com.LubieKakao1212.qulib.util.entity.EntityChain;
import com.fathzer.soft.javaluator.AbstractEvaluator;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import org.joml.Vector3d;

public class Explode implements IGunComponent {

    private VectorOrExpression position = null;

    private DoubleOrExpression strength = null;

    private boolean fire = false;

    @Override
    public boolean executeAction(ItemStack gunStack, LivingEntity caster, IGun gun) {
        if(position == null) {
            caster.sendMessage(Component.nullToEmpty("Explosion position must be set"), caster.getUUID());
            return false;
        }
        if(strength == null) {
            caster.sendMessage(Component.nullToEmpty("Explosion strength must be set"), caster.getUUID());
            return false;
        }

        AbstractEvaluator evaluator = gun.getGunType().getEvaluator();
        GunState state = gun.getState();

        Vector3d pos = position.get(state, evaluator);
        Double size = strength.get(state, evaluator);

        caster.getLevel().explode(caster, null,null, pos.x(), pos.y(), pos.z(), size.floatValue(), fire, Explosion.BlockInteraction.DESTROY);

        return true;
    }

}
