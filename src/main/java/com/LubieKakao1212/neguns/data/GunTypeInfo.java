package com.LubieKakao1212.neguns.data;

import com.LubieKakao1212.neguns.NewEraGunsMod;
import com.LubieKakao1212.neguns.gun.component.IGunComponent;
import com.LubieKakao1212.neguns.gun.state.GunState;
import com.LubieKakao1212.neguns.resources.NEGunsDataCache;
import com.LubieKakao1212.qulib.util.entity.EntityChain;
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

    private IGunComponent trigger;

    public final List<String> animations = new ArrayList<>();

    public ResourceLocation getModel() {
        if(modelLocation == null) {
            modelLocation = new ResourceLocation(model);
        }
        return modelLocation;
    }

    public boolean trigger(ItemStack gun, EntityChain entityChain, GunState state) {
        NewEraGunsMod.LOGGER.warn(animations);
        trigger.executeAction(gun, entityChain, state);
        return true;
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
