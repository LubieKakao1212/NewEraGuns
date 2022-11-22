package com.LubieKakao1212.neguns.data;

import com.LubieKakao1212.neguns.capability.gun.IGun;
import com.LubieKakao1212.neguns.expression.MultiTypeEvaluator;
import com.LubieKakao1212.neguns.gun.component.IGunComponent;
import com.LubieKakao1212.neguns.item.GunItem;
import com.LubieKakao1212.neguns.resources.NEGunsDataCache;
import com.LubieKakao1212.qulib.util.entity.EntityChain;
import com.fathzer.soft.javaluator.AbstractEvaluator;
import com.google.gson.annotations.SerializedName;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
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

    public boolean trigger(ItemStack gunStack, EntityChain entityChain, IGun gun) {
        return trigger(trigger, gunStack, entityChain, gun);
    }

    public boolean triggerHold(ItemStack gunStack, EntityChain entityChain, IGun gun) {
        if(triggerHold == null) {
            return trigger(trigger, gunStack, entityChain, gun);
        }else
        {
            return trigger(triggerHold, gunStack, entityChain, gun);
        }
    }

    public boolean triggerRelease(ItemStack gunStack, EntityChain entityChain, IGun gun) {
        return trigger(triggerRelease, gunStack, entityChain, gun);
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

    private boolean trigger(IGunComponent rootComponent, ItemStack gunStack, EntityChain entityChain, IGun gun) {
        CompoundTag stateTag = null;
        if(gunStack.hasTag()) {
            stateTag = gunStack.getTag();
        }

        if(stateTag != null &&stateTag.contains(GunItem.STATE_NBT_KEY, Tag.TAG_COMPOUND)) {
                gun.getState().deserializeNBT(gunStack.getTag().getCompound(GunItem.STATE_NBT_KEY));
            }
        else {
            gun.getState().clear();
        }

        gun.applyProvidedState();
        rootComponent.executeAction(gunStack, entityChain, gun);

        //TODO Constantify key
        Object result = gun.getState().get("continue");

        if(stateTag != null) {
            stateTag.put(GunItem.STATE_NBT_KEY, gun.getState().serializeNBT());
        }

        return result instanceof Boolean ? ((Boolean)result) : true;
    }

}
