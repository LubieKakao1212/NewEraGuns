package com.LubieKakao1212.neguns.event;

import com.LubieKakao1212.neguns.NewEraGunsMod;
import com.LubieKakao1212.neguns.client.particle.BeamParticle;
import com.LubieKakao1212.neguns.init.Particles;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber (value = Dist.CLIENT, modid = NewEraGunsMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientInitEventHandler {

    @SubscribeEvent
    public static void registerParticleFactories(ParticleFactoryRegisterEvent event) {

        ParticleEngine manager = Minecraft.getInstance().particleEngine;

        manager.register(Particles.REF.BEAM_PARTICLE, new BeamParticle.Factory());
    }

}
