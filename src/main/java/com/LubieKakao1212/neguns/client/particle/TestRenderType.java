package com.LubieKakao1212.neguns.client.particle;

import com.LubieKakao1212.neguns.NewEraGunsMod;
import com.LubieKakao1212.qulib.client.rendering.RenderingUtils;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import org.joml.Vector3d;

public class TestRenderType extends RenderStateShard {

    public static RenderType rt = RenderType.create(NewEraGunsMod.MODID + ":test", DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.QUADS, 1024, false, true, RenderType.CompositeState.builder().setTransparencyState(TRANSLUCENT_TRANSPARENCY).setOutputState(TRANSLUCENT_TARGET).setShaderState(RenderStateShard.POSITION_COLOR_SHADER).createCompositeState(true));

    public TestRenderType(String pName, Runnable pSetupState, Runnable pClearState) {
        super(pName, pSetupState, pClearState);
    }



}
