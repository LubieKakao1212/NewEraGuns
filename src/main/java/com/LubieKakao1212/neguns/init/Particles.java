package com.LubieKakao1212.neguns.init;

import com.LubieKakao1212.neguns.NewEraGunsMod;
import com.mojang.serialization.Codec;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;

public class Particles {

    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES,  NewEraGunsMod.MODID);

    static {
        PARTICLE_TYPES.register(ID.BEAM_PARTICLE_ID, () -> new SimpleParticleType(false));
    }

    @ObjectHolder(NewEraGunsMod.MODID)
    public static class REF {
        @ObjectHolder(ID.BEAM_PARTICLE_ID)
        public static SimpleParticleType BEAM_PARTICLE;
    }

    public static class ID {
        public static final String BEAM_PARTICLE_ID = "beam";
    }
}
