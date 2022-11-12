package com.LubieKakao1212.neguns.data;

import com.LubieKakao1212.neguns.capability.gun.IGun;
import com.LubieKakao1212.neguns.expression.MultiTypeEvaluator;
import com.LubieKakao1212.neguns.gun.component.IGunComponent;
import com.LubieKakao1212.neguns.resources.NEGunsDataCache;
import com.LubieKakao1212.qulib.util.entity.EntityChain;
import com.fathzer.soft.javaluator.AbstractEvaluator;
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

    private boolean useJs = false;

    private String model = "";

    private transient ResourceLocation modelLocation;

    @SerializedName(value = "trigger", alternate = { "trigger-press" })
    private IGunComponent trigger;

    @SerializedName("trigger-hold")
    private IGunComponent triggerHold = null;

    @SerializedName("trigger-release")
    private IGunComponent triggerRelease;

    public final List<String> animations = new ArrayList<>();

    private transient AbstractEvaluator evaluator;

    public ResourceLocation getModel() {
        if(modelLocation == null) {
            modelLocation = new ResourceLocation(model);
        }
        return modelLocation;
    }

    public AbstractEvaluator getEvaluator() {
        if(evaluator != null) {
            return evaluator;
        }
        return evaluator = (useJs ? null : new MultiTypeEvaluator());
    }

    public void trigger(ItemStack gunStack, EntityChain entityChain, IGun gun) {
        gun.applyProvidedState();
        trigger.executeAction(gunStack, entityChain, gun);
    }

    public void triggerHold(ItemStack gunStack, EntityChain entityChain, IGun gun) {
        if(triggerHold == null) {
            trigger(gunStack, entityChain, gun);
        }else
        {
            gun.applyProvidedState();
            triggerHold.executeAction(gunStack, entityChain, gun);
        }
    }

    public void setTriggerRelease(ItemStack gunStack, EntityChain entityChain, IGun gun) {
        gun.applyProvidedState();
        triggerRelease.executeAction(gunStack, entityChain, gun);
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
