package com.LubieKakao1212.modularguns.item;

import com.LubieKakao1212.modularguns.capability.GunCapabilityProvider;
import com.LubieKakao1212.modularguns.capability.GunCaps;
import com.LubieKakao1212.modularguns.data.GunTypeInfo;
import com.LubieKakao1212.modularguns.item.render.GunRenderer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.IItemRenderProperties;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.GeckoLib;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.network.GeckoLibNetwork;
import software.bernie.geckolib3.network.ISyncable;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class GunItem extends Item implements IAnimatable, ISyncable {

    public AnimationFactory factory = new AnimationFactory(this);

    public GunItem(Properties properties) {
        super(properties);
        GeckoLibNetwork.registerSyncable(this);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        if(stack.getTag() != null) {
            ResourceLocation gunTypeId = new ResourceLocation(stack.getTag().getString("GunType"));
            return new GunCapabilityProvider(gunTypeId);
        }
        return null;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flags) {
        if(flags.isAdvanced()){
            stack.getCapability(GunCaps.GUN_TYPE).ifPresent(
                    (gunType) ->
                        gunType.getGunType().ifPresent(
                                (gunInfo) -> {
                                    tooltip.add(new TextComponent(gunInfo.getModel().toString()));
                                }
                        )
                    );
        }
    }

    @Override
    public void initializeClient(Consumer<IItemRenderProperties> consumer) {
        super.initializeClient(consumer);
        consumer.accept(new IItemRenderProperties() {
            private final GunRenderer renderer = new GunRenderer(new ResourceLocation("mdguns:potato"));

            @Override
            public BlockEntityWithoutLevelRenderer getItemStackRenderer() {
                return renderer;
            }
        });
    }

    @Override
    public void registerControllers(AnimationData data) {

    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public void onAnimationSync(int id, int state) {

    }
}
