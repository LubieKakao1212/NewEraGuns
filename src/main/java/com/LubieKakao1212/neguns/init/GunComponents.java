package com.LubieKakao1212.neguns.init;

import com.LubieKakao1212.neguns.NewEraGunsMod;
import com.LubieKakao1212.neguns.gun.component.GunComponentType;
import com.LubieKakao1212.neguns.gun.component.components.actions.*;
import com.LubieKakao1212.neguns.gun.component.components.conditions.Condition;
import com.LubieKakao1212.neguns.gun.component.components.conditions.FEAmountCondition;
import com.LubieKakao1212.neguns.gun.component.components.helper.Conditional;
import com.LubieKakao1212.neguns.gun.component.components.helper.DefaultPierce;
import com.LubieKakao1212.neguns.gun.component.components.helper.NonTerminalSequence;
import com.LubieKakao1212.neguns.gun.component.components.helper.debug.PrintMessage;
import com.LubieKakao1212.neguns.gun.component.components.helper.math.Scatter;
import com.LubieKakao1212.neguns.gun.component.components.visual.Animate;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryBuilder;

public class GunComponents {

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

        GUN_COMPONENTS.register("explode", () -> GunComponentType.create(Explode.class));

        GUN_COMPONENTS.register("scatter", () -> GunComponentType.create(Scatter.class));
        GUN_COMPONENTS.register("default_pierce", () -> GunComponentType.create(DefaultPierce.class));

        GUN_COMPONENTS.register("damage", () -> GunComponentType.create(Damage.class));
        GUN_COMPONENTS.register("ray", () -> GunComponentType.create(RaycastAction.class));
        GUN_COMPONENTS.register("raycast_simple", () -> GunComponentType.create(ShootSimpleRaycast.class));
    }

}
