package com.LubieKakao1212.neguns.gun.component.components.helper.math;

import com.LubieKakao1212.neguns.capability.gun.IGun;
import com.LubieKakao1212.neguns.data.util.vars.DoubleOrExpression;
import com.LubieKakao1212.neguns.data.util.vars.VectorOrExpression;
import com.LubieKakao1212.neguns.gun.component.IGunComponent;
import com.LubieKakao1212.neguns.gun.state.GunState;
import com.LubieKakao1212.qulib.math.AimUtil;
import com.LubieKakao1212.qulib.util.entity.EntityChain;
import com.LubieKakao1212.qulib.util.joml.Vector3dUtil;
import com.fathzer.soft.javaluator.AbstractEvaluator;
import com.google.gson.annotations.SerializedName;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.joml.Math;
import org.joml.Vector3d;

public class Scatter implements IGunComponent {

    @SerializedName(value = "forward", alternate = "base-direction")
    private VectorOrExpression forward = new VectorOrExpression(Vector3dUtil.south());

    @SerializedName(value = "output", alternate = "output-variable")
    private String outVar = "dir";

    private DoubleOrExpression roll = null;

    private DoubleOrExpression angle = null;

    @Override
    public boolean executeAction(ItemStack gunStack, LivingEntity caster, IGun gun) {
        AbstractEvaluator evaluator = gun.getGunType().getEvaluator();
        GunState state = gun.getState();

        Double roll;

        if(this.roll != null) {
            roll = this.roll.get(state, evaluator);
        }
        else {
            roll = AimUtil.random.nextDouble() * 2 * Math.PI;
        }

        if(this.angle == null) {
            caster.sendMessage(Component.nullToEmpty("Scatter angle must be set"), caster.getUUID());
            return false;
        }

        Double angle = this.angle.get(state, evaluator);

        Vector3d result = AimUtil.calculateForwardWithSpread(angle, roll, forward.get(state, evaluator));

        state.put(outVar, result);

        return true;
    }
}
