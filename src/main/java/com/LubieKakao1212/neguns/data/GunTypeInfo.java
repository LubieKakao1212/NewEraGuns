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
import net.minecraft.world.entity.LivingEntity;
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

public class GunTypeInfo implements IInvalidatable {

    private boolean useJs = false;

    private boolean isValid = true;

    private String model = "";

    private transient ResourceLocation modelLocation;

    @SerializedName(value = "trigger", alternate = { "trigger-press" })
    private IGunComponent trigger = null;

    @SerializedName("trigger-hold")
    private IGunComponent triggerHold = null;

    @SerializedName("trigger-release")
    private IGunComponent triggerRelease = null;

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

    public boolean trigger(ItemStack gunStack, LivingEntity caster, IGun gun) {
        return trigger(trigger, gunStack, caster, gun);
    }

    public boolean triggerHold(ItemStack gunStack, LivingEntity caster, IGun gun) {
        if(triggerHold == null) {
            return trigger(trigger, gunStack, caster, gun);
        }else
        {
            return trigger(triggerHold, gunStack, caster, gun);
        }
    }

    public boolean triggerRelease(ItemStack gunStack, LivingEntity caster, IGun gun) {
        return trigger(triggerRelease, gunStack, caster, gun);
    }

    public void setModel(ResourceLocation modelLocation) {
        this.modelLocation = modelLocation;
    }

    @Override
    public void invalidate() {
        isValid = false;
    }

    public boolean isValid() {
        return isValid;
    }

    private boolean trigger(IGunComponent rootComponent, ItemStack gunStack, LivingEntity caster, IGun gun) {
        if(rootComponent == null) {
            return false;
        }
        CompoundTag stackTag = null;
        if(gunStack.hasTag()) {
            stackTag = gunStack.getTag();
        }

        if(stackTag != null &&stackTag.contains(GunItem.STATE_NBT_KEY, Tag.TAG_COMPOUND)) {
                gun.getState().deserializeNBT(gunStack.getTag().getCompound(GunItem.STATE_NBT_KEY));
            }
        else {
            gun.getState().clear();
        }

        gun.applyProvidedState(caster);
        rootComponent.executeAction(gunStack, caster, gun);

        //TODO Constantify key
        Object result = gun.getState().get("continue");

        if(stackTag != null) {
            stackTag.put(GunItem.STATE_NBT_KEY, gun.getState().serializeNBT());
        }

        gunStack.setTag(stackTag);

        return result instanceof Boolean ? ((Boolean)result) : true;
    }
}
