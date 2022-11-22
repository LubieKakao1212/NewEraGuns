package com.LubieKakao1212.neguns.item;

import com.LubieKakao1212.neguns.NewEraGunsMod;
import com.LubieKakao1212.neguns.capability.GunCapabilityProvider;
import com.LubieKakao1212.neguns.capability.GunCaps;
import com.LubieKakao1212.neguns.capability.gun.IGun;
import com.LubieKakao1212.neguns.gun.state.GunState;
import com.LubieKakao1212.neguns.item.render.GunRenderer;
import com.LubieKakao1212.neguns.resources.NEGunsDataCache;
import com.LubieKakao1212.qulib.util.entity.EntityChain;
import net.minecraft.ChatFormatting;
import net.minecraft.ResourceLocationException;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.IItemRenderProperties;
import net.minecraftforge.client.RenderProperties;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.network.GeckoLibNetwork;
import software.bernie.geckolib3.network.ISyncable;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.List;
import java.util.function.Consumer;

public class GunItem extends Item implements IAnimatable, ISyncable {

    public static final String STATE_NBT_KEY = "GunState";
    public static final String GUN_TYPE_KEY = "GunType";

    public AnimationFactory factory = new AnimationFactory(this);

    public GunItem(Properties properties) {
        super(properties);
        GeckoLibNetwork.registerSyncable(this);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        if(stack.getTag() != null) {
            try {
                ResourceLocation gunTypeId = new ResourceLocation(stack.getTag().getString(GUN_TYPE_KEY));
                if(nbt != null) {
                    nbt = nbt.getCompound("Parent");
                }
                return new GunCapabilityProvider(stack, gunTypeId, nbt);
            }
            catch(ResourceLocationException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flags) {
        if(!stack.getCapability(GunCaps.GUN).isPresent()) {
            if(stack.hasTag()) {
                tooltip.add(new TextComponent("Could not create gun: " + stack.getTag().getString("GunType")).withStyle(ChatFormatting.RED));
            }else
                tooltip.add(new TextComponent("Could not create gun: missing tag").withStyle(ChatFormatting.RED));
        }
        if(flags.isAdvanced()){
            stack.getCapability(GunCaps.GUN).ifPresent(
                    (gunType) -> tooltip.add(new TextComponent(gunType.getGunType().getModel().toString()))
            );
            stack.getCapability(CapabilityEnergy.ENERGY).ifPresent(
                    (energy) -> tooltip.add(new TextComponent(energy.getEnergyStored() + "/" + energy.getMaxEnergyStored()).setStyle(Style.EMPTY.withColor(TextColor.fromLegacyFormat(ChatFormatting.AQUA))))
            );
        }
    }

    @Override
    public void initializeClient(Consumer<IItemRenderProperties> consumer) {
        super.initializeClient(consumer);
        consumer.accept(new IItemRenderProperties() {
            private final GunRenderer renderer = new GunRenderer();

            @Override
            public BlockEntityWithoutLevelRenderer getItemStackRenderer() {
                return renderer;
            }
        });
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller", 1, event -> PlayState.CONTINUE));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public void onAnimationSync(int id, int state) {
        final AnimationController controller = GeckoLibUtil.getControllerForID(this.factory, id, "controller");

        if (controller.getAnimationState() == AnimationState.Stopped) {

            GunRenderer renderer = (GunRenderer) RenderProperties.get(this).getItemStackRenderer();
            String animName = NEGunsDataCache.GUN_ANIMATIONS.getName(state);
            ResourceLocation modelId = NEGunsDataCache.GUN_ANIMATIONS.getModelFromAnimation(animName);

            renderer.setModel(NEGunsDataCache.getOrCreateModel(modelId));

            controller.markNeedsReload();
            controller.setAnimation(new AnimationBuilder().addAnimation(animName, false));

            renderer.setModel(NEGunsDataCache.FAKE_MODEL);
        }
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return 60;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);

        boolean[] flag = { false };
        if(level.isClientSide) {
            stack.getCapability(GunCaps.GUN).ifPresent((gun) -> {
                //player.startUsingItem(usedHand);
                flag[0] = true;
            });
        }else {
            stack.getCapability(GunCaps.GUN).ifPresent(
                    (gun) -> {
                        player.startUsingItem(usedHand);
                        //gun.getGunType().trigger(stack, new EntityChain().add(player), gun);
                        flag[0] = true;
                    }
            );
        }
        if(flag[0]) {
            return InteractionResultHolder.pass(stack);
        }else
        {
            return InteractionResultHolder.fail(stack);
        }
    }
}
