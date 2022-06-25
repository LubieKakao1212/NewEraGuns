package com.LubieKakao1212.neguns.data;

import com.LubieKakao1212.neguns.NewEraGunsMod;
import com.LubieKakao1212.neguns.gun.component.IGunComponent;
import com.LubieKakao1212.neguns.gun.component.components.FallbackComponent;
import com.LubieKakao1212.neguns.gun.state.GunState;
import com.LubieKakao1212.neguns.resources.NEGunsDataCache;
import com.LubieKakao1212.qulib.util.entity.EntityChain;
import com.google.gson.annotations.SerializedName;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.network.ISyncable;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.ArrayList;
import java.util.List;

public class GunTypeInfo implements IAnimatable, ISyncable {

    private AnimationFactory factory = new AnimationFactory(this);

    private String model = "";

    private transient ResourceLocation modelLocation;

    @SerializedName(value = "trigger", alternate = { "trigger-press" })
    private IGunComponent trigger;

    @SerializedName("trigger-hold")
    private IGunComponent triggerHold = null;

    @SerializedName("trigger-release")
    private IGunComponent triggerRelease;

    public final List<String> animations = new ArrayList<>();

    public ResourceLocation getModel() {
        if(modelLocation == null) {
            modelLocation = new ResourceLocation(model);
        }
        return modelLocation;
    }

    public void trigger(ItemStack gun, EntityChain entityChain, GunState state) {
        trigger.executeAction(gun, entityChain, state);
    }

    public void triggerHold(ItemStack gun, EntityChain entityChain, GunState state) {
        if(triggerHold == null) {
            trigger(gun, entityChain, state);
        }else
        {
            triggerHold.executeAction(gun, entityChain, state);
        }
    }

    public void setTriggerRelease(ItemStack gun, EntityChain entityChain, GunState state) {
        triggerRelease.executeAction(gun, entityChain, state);
    }

    public void setModel(ResourceLocation modelLocation) {
        this.modelLocation = modelLocation;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 0, event -> PlayState.CONTINUE));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public void onAnimationSync(int id, int state) {
        final AnimationController controller = GeckoLibUtil.getControllerForID(this.factory, id, "controller");

        if(controller.getAnimationState() == AnimationState.Stopped) {
            controller.markNeedsReload();
            controller.setAnimation(new AnimationBuilder().addAnimation(NEGunsDataCache.GUN_ANIMATIONS.getName(state)));
        }
    }
}
