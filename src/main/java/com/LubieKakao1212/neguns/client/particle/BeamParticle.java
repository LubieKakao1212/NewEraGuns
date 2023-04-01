package com.LubieKakao1212.neguns.client.particle;

import com.LubieKakao1212.neguns.NewEraGunsMod;
import com.LubieKakao1212.qulib.client.rendering.RenderingUtils;
import com.LubieKakao1212.qulib.client.rendering.Transformation;
import com.LubieKakao1212.qulib.libs.joml.Quaterniond;
import com.LubieKakao1212.qulib.libs.joml.Vector3d;
import com.LubieKakao1212.qulib.util.joml.Vector3dUtil;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Quaternion;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.scores.criteria.ObjectiveCriteria;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

import java.awt.image.renderable.RenderContext;

@OnlyIn(Dist.CLIENT)
public class BeamParticle extends Particle {

    private Vector3d startPos;
    private Vector3d endPos;

    private Vector3d midpointPos;

    private Vector3d deltaPos;

    public BeamParticle(ClientLevel pLevel, double startX, double startY, double startZ, double deltaX, double deltaY, double deltaZ, int layers) {
        super(pLevel, startX, startY, startZ);

        this.startPos = new Vector3d(startX, startY, startZ);
        setDeltaPos(new Vector3d(deltaX, deltaY, deltaZ));

        this.xd = deltaX;
        this.yd = deltaY;
        this.zd = deltaZ;
        this.lifetime = 1000;

        AABB aabb = Vector3dUtil.toAABB(startPos, endPos);

        aabb.inflate(5f);

        setBoundingBox(aabb);
    }

    @Override
    public void tick() {
        if (this.age++ >= this.lifetime) {
            this.remove();
        }
        deltaPos.y += 1. / 15.;
        setDeltaPos(deltaPos);
    }

    @Override
    public void render(VertexConsumer pBuffer, Camera pRenderInfo, float pPartialTicks) {
        Vector3d pos = Vector3dUtil.of(pRenderInfo.getPosition());

        Vector3d midpoint = midpointPos.sub(pos, new Vector3d());

        MultiBufferSource.BufferSource bufferSource = Minecraft.getInstance().renderBuffers().bufferSource();
        VertexConsumer vertexconsumer = bufferSource.getBuffer(TestRenderType.rt);

        vertexconsumer.defaultColor(255,25,25,50);

        Quaternion quat = pRenderInfo.rotation();

        Quaterniond quaterniond = new Quaterniond().rotateTo(Vector3dUtil.up(), deltaPos);

        Vector3d scale = new Vector3d(1., deltaPos.length(), 1.);
        Vector3d oneFourth = new Vector3d(1. / 4. );

        Transformation rotPos = Transformation.rotated(quaterniond).translate(midpoint);

        RenderingUtils.CUBE.render(vertexconsumer, Transformation.scaled(scale).compose(rotPos));
        RenderingUtils.CUBE.render(vertexconsumer, Transformation.scaled(scale.sub(oneFourth)).compose(rotPos));
        RenderingUtils.CUBE.render(vertexconsumer, Transformation.scaled(scale.sub(oneFourth)).compose(rotPos));
        RenderingUtils.CUBE.render(vertexconsumer, Transformation.scaled(scale.sub(oneFourth)).compose(rotPos));

        vertexconsumer.unsetDefaultColor();
        //bb.end();

        bufferSource.endBatch();
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.CUSTOM;
    }

    private void setDeltaPos(Vector3d deltaPos) {
        this.deltaPos = deltaPos;
        this.endPos = startPos.add(deltaPos, new Vector3d());
        this.midpointPos = deltaPos.div(2, new Vector3d()).add(startPos);
    }


    @OnlyIn(Dist.CLIENT)
    public static class Factory implements ParticleProvider<SimpleParticleType> {
        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType pType, ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            return new BeamParticle(pLevel, pX, pY, pZ, 2., 1., 3., 1);
        }
    }
}