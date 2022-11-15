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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import org.joml.Vector3d;

public class Explode implements IGunComponent {

    private VectorOrExpression position = null;

    private DoubleOrExpression strength = null;

    private boolean fire = false;

    @Override
    public boolean executeAction(ItemStack gunStack, EntityChain entityChain, IGun gun) {
        if(position == null) {
            entityChain.first().sendMessage(Component.nullToEmpty("Explosion position must be set"), entityChain.first().getUUID());
            return false;
        }
        if(strength == null) {
            entityChain.first().sendMessage(Component.nullToEmpty("Explosion strength must be set"), entityChain.first().getUUID());
            return false;
        }

        AbstractEvaluator evaluator = gun.getGunType().getEvaluator();
        GunState state = gun.getState();

        Vector3d pos = position.get(state, evaluator);
        Double size = strength.get(state, evaluator);

        entityChain.first().getLevel().explode(entityChain.first(), null,null, pos.x(), pos.y(), pos.z(), size.floatValue(), fire, Explosion.BlockInteraction.DESTROY);

        return true;
    }

}
