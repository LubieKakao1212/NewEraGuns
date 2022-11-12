package com.LubieKakao1212.neguns.init;

import com.LubieKakao1212.neguns.NewEraGunsMod;
import com.LubieKakao1212.neguns.capability.gun.IGun;
import com.LubieKakao1212.neguns.gun.component.GunComponentType;
import com.LubieKakao1212.neguns.gun.component.components.actions.FEDrain;
import com.LubieKakao1212.neguns.gun.component.components.actions.ShootSimpleRaycast;
import com.LubieKakao1212.neguns.gun.component.components.conditions.Condition;
import com.LubieKakao1212.neguns.gun.component.components.conditions.FEAmountCondition;
import com.LubieKakao1212.neguns.gun.component.components.helper.Conditional;
import com.LubieKakao1212.neguns.gun.component.components.helper.NonTerminalSequence;
import com.LubieKakao1212.neguns.gun.component.components.helper.debug.PrintMessage;
import com.LubieKakao1212.neguns.gun.component.components.visual.Animate;
import com.LubieKakao1212.neguns.item.DebugItem;
import com.LubieKakao1212.neguns.item.GunItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryBuilder;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class Register {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, NewEraGunsMod.MODID);
    public static final DeferredRegister<GunComponentType> GUN_COMPONENTS = DeferredRegister.create(NEGunsRegistries.Keys.GUN_COMPONENT_TYPES, NewEraGunsMod.MODID);

    static {
        NEGunsRegistries.gunComponentRegistry = GUN_COMPONENTS.makeRegistry(GunComponentType.class, () -> new RegistryBuilder<>());
        GUN_COMPONENTS.register("debug", () -> GunComponentType.create(PrintMessage.class));

        GUN_COMPONENTS.register("trigger_animation", () -> GunComponentType.create(Animate.class));

        GUN_COMPONENTS.register("fe_amount", () -> GunComponentType.create(FEAmountCondition.class));
        GUN_COMPONENTS.register("fe_drain", () -> GunComponentType.create(FEDrain.class));

        GUN_COMPONENTS.register("condition", () -> GunComponentType.create(Condition.class));
        GUN_COMPONENTS.register("cnd", () -> GunComponentType.create(Condition.class));

        GUN_COMPONENTS.register("conditional", () -> GunComponentType.create(Conditional.class));
        GUN_COMPONENTS.register("sequence", () -> GunComponentType.create(NonTerminalSequence.class));

        GUN_COMPONENTS.register("raycast_simple", () -> GunComponentType.create(ShootSimpleRaycast.class));

        ITEMS.register("debug", () -> new DebugItem(new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)));
        ITEMS.register("gun", () -> new GunItem(new Item.Properties().tab(CreativeModeTab.TAB_TOOLS).stacksTo(1)));
    }

    public static void init() {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        GUN_COMPONENTS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.register(IGun.class);
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void modelRegistry(ModelRegistryEvent event) {

    }

}
