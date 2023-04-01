package com.LubieKakao1212.neguns.gun.component.components.visual;

import com.LubieKakao1212.neguns.capability.gun.IGun;
import com.LubieKakao1212.neguns.gun.component.IGunComponent;
import com.LubieKakao1212.neguns.init.Particles;
import com.google.gson.annotations.SerializedName;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

public class Particle implements IGunComponent {

    /*@SerializedName("type")
    private String particleType = null;*/

    @Override
    public boolean executeAction(ItemStack gunStack, LivingEntity caster, IGun gun) {
        //ParticleType type = ForgeRegistries.PARTICLE_TYPES.getValue(new ResourceLocation(particleType));
        ((ServerLevel)caster.level).sendParticles(Particles.REF.BEAM_PARTICLE, caster.getX(), caster.getY(), caster.getZ(), 100, 1, 1, 1, 1);
        return false;
    }



}
