package com.LubieKakao1212.neguns.gun.component.components.actions;

import com.LubieKakao1212.neguns.gun.component.IGunComponent;
import com.LubieKakao1212.neguns.gun.dummy.EntityChain;
import com.LubieKakao1212.neguns.gun.state.GunState;
import com.LubieKakao1212.neguns.physics.RaycastHit;
import com.LubieKakao1212.neguns.physics.RaycastUtil;
import com.LubieKakao1212.qulib.math.AimUtil;
import com.LubieKakao1212.qulib.util.Vector3dUtil;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.joml.Vector3d;

import java.util.Comparator;
import java.util.List;

public class ShootSimpleRaycast implements IGunComponent {

    private double spread = 0;
    private float damage = 0;
    private double range = 0;

    @Override
    public boolean executeAction(ItemStack gun, EntityChain entityChain, GunState state) {
        Entity caster = entityChain.first();
        Vector3d position = Vector3dUtil.of(caster.getEyePosition());
        List<LivingEntity> raycastResults = RaycastUtil.raycastEntitiesAll(caster.level, position,
                AimUtil.calculateForwardWithUniformSpread(
                        AimUtil.aimDeg(caster.getXRot(), caster.getYRot()), spread, Vector3dUtil.south()), range)
                .stream().sorted(Comparator.comparingDouble(e -> e.intersection().distanceMin())).filter(e -> e.target() instanceof LivingEntity).map(e -> (LivingEntity)e.target()).toList();

        raycastResults.get(0).hurt(DamageSource.mobAttack((LivingEntity) entityChain.first()), damage);
        return true;
    }

}
