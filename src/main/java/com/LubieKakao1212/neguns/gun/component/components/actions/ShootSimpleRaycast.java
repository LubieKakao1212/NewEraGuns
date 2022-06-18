package com.LubieKakao1212.neguns.gun.component.components.actions;

import com.LubieKakao1212.neguns.gun.component.IGunComponent;
import com.LubieKakao1212.qulib.util.entity.EntityChain;
import com.LubieKakao1212.neguns.gun.state.GunState;
import com.LubieKakao1212.neguns.physics.BlockStatePos;
import com.LubieKakao1212.neguns.physics.Raycast;
import com.LubieKakao1212.neguns.physics.RaycastHit;
import com.LubieKakao1212.qulib.math.AimUtil;
import com.LubieKakao1212.qulib.util.joml.Vector3dUtil;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.joml.Vector3d;

import java.util.List;

public class ShootSimpleRaycast implements IGunComponent {

    private double spread = 0;
    private float damage = 0;
    private double range = 0;
    private int pierce = 1;

    private transient Raycast raycast = null;

    @Override
    public boolean executeAction(ItemStack gun, EntityChain entityChain, GunState state) {
        Entity caster = entityChain.first();
        Vector3d position = Vector3dUtil.of(caster.getEyePosition());

        if(raycast == null) {
            raycast = new Raycast.Builder(Raycast.Target.Both).setSorted(true).addFilter(Raycast.Filters.entitySubClass(LivingEntity.class))
                    .addFilter(Raycast.Filters.entity((hit) -> hit.target() != entityChain.first()))
                    .setPierce(pierce)
                    .addPierceHandler(hit -> hit.target() instanceof BlockStatePos ? 1000 : 1)
                    .build();
        }

        List<RaycastHit> raycastResults = raycast.perform(caster.level, position, AimUtil.calculateForwardWithUniformSpread(
                AimUtil.aimDeg(caster.getXRot(), caster.getYRot()), spread, Vector3dUtil.south()), range);

        for (RaycastHit hit : raycastResults) {
            if(hit.target() instanceof LivingEntity) {
                ((LivingEntity) hit.target()).hurt(DamageSource.mobAttack((LivingEntity) entityChain.first()), damage);
            }
        }
        return true;
    }

}
