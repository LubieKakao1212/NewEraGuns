package com.LubieKakao1212.neguns.gun.component.components.actions;

import com.LubieKakao1212.neguns.capability.gun.IGun;
import com.LubieKakao1212.neguns.data.util.vars.DoubleOrExpression;
import com.LubieKakao1212.neguns.data.util.vars.VectorOrExpression;
import com.LubieKakao1212.neguns.gun.component.IGunComponent;
import com.LubieKakao1212.neguns.gun.state.GunState;
import com.LubieKakao1212.neguns.physics.BlockStatePos;
import com.LubieKakao1212.neguns.physics.Raycast;
import com.LubieKakao1212.neguns.physics.RaycastHit;
import com.LubieKakao1212.qulib.util.entity.EntityChain;
import com.LubieKakao1212.qulib.util.joml.Vector3dUtil;
import com.fathzer.soft.javaluator.AbstractEvaluator;
import com.google.gson.annotations.SerializedName;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.joml.Vector3d;

import java.util.List;

public class RaycastAction implements IGunComponent {

    private VectorOrExpression direction = new VectorOrExpression(Vector3dUtil.south());

    private VectorOrExpression origin = new VectorOrExpression(new Vector3d().zero());

    private DoubleOrExpression pierce = new DoubleOrExpression(0.);

    /**
     *  1 -> blocks
     *  2 -> entities
     *  3 -> both
     */
    @SerializedName(value = "target-filter")
    private int targetFilter;

    private DoubleOrExpression distance = new DoubleOrExpression(1.);

    @SerializedName(value = "hit-block")
    private IGunComponent blockHitAction;

    @SerializedName(value = "hit-entity")
    private IGunComponent entityHitAction;

    @SerializedName(value = "variable-scope")
    private String scope = null;

    @Override
    public boolean executeAction(ItemStack gunStack, LivingEntity caster, IGun gun) {

        AbstractEvaluator evaluator = gun.getGunType().getEvaluator();
        GunState state = gun.getState();

        Raycast raycast = new Raycast.Builder(Raycast.Target.fromMask(targetFilter)).setSorted(true).addPierceHandler(hit -> 0).build();

        List<RaycastHit> hits = raycast.perform(caster.level, origin.get(state, evaluator), direction.get(state, evaluator), distance.get(state, evaluator));

        if(scope != null) {
            state.pushScope(scope);
        }

        state.put("pierce", pierce.get(state, evaluator));
        for(RaycastHit hit : hits) {
            putCommonVariables(state, hit);
            if(hit.target() instanceof BlockStatePos) {
                BlockStatePos pos = (BlockStatePos) hit.target();
                //TODO change to put()
                state.putTemporary("hit_blockPos", Vector3dUtil.of(pos.pos()));
                //TODO add blockState
                blockHitAction.executeAction(gunStack, caster, gun);
            }
            else if(hit.target() instanceof Entity) {
                Entity entity = (Entity) hit.target();
                if(entity == caster)
                    continue;
                //Entities can only be stored as temporary vars
                state.putTemporary("hit_entity", entity);
                entityHitAction.executeAction(gunStack, caster, gun);
            }

            if(((Double)state.get("pierce")) <= 0.) {
                break;
            }
        }

        if(scope != null) {
            state.popScope(scope);
        }

        return true;
    }

    private void putCommonVariables(GunState state, RaycastHit hit) {
        //TODO change to put()
        state.putTemporary("hit_point", hit.intersection().pointNear());
        state.putTemporary("hit_exitPoint", hit.intersection().pointFar());
        state.putTemporary("hit_distance", hit.intersection().distanceMin());
        state.putTemporary("hit_exitDistance", hit.intersection().distanceMax());
        state.putTemporary("hit_isInside", hit.intersection().isInside());
    }

}
