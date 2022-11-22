package com.LubieKakao1212.neguns.init;

import com.LubieKakao1212.neguns.NewEraGunsMod;
import com.LubieKakao1212.neguns.gun.state.StateVariable;
import com.LubieKakao1212.qulib.nbt.JomlNBT;
import net.minecraft.nbt.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryBuilder;
import org.joml.Quaterniond;
import org.joml.Vector3d;

import java.util.UUID;

public class StateVariables {

    public static final DeferredRegister<StateVariable.Type> STATE_VARIABLE_TYPES = DeferredRegister.create(NEGunsRegistries.Keys.STATE_VARIABLE_TYPES, NewEraGunsMod.MODID);

    static {
        NEGunsRegistries.stateVariableRegistry = STATE_VARIABLE_TYPES.makeRegistry(StateVariable.Type.class, () -> new RegistryBuilder<StateVariable.Type>()
                .onAdd((_0,_1,_2, obj, _4) -> StateVariable.Type.CLASS_TYPE_MAP.put((obj).getVariableType(), obj))
                .onClear((_0, _1) -> StateVariable.Type.CLASS_TYPE_MAP.clear())
        );

        STATE_VARIABLE_TYPES.register("double", () -> StateVariable.makeType(Double.class,
                o -> DoubleTag.valueOf((Double)o),
                tag -> tag instanceof DoubleTag ? ((DoubleTag) tag).getAsDouble() : null));

        STATE_VARIABLE_TYPES.register("boolean", () -> StateVariable.makeType(Boolean.class,
                o -> ByteTag.valueOf((Boolean) o),
                tag -> tag instanceof ByteTag ? !tag.equals(ByteTag.ZERO) : null));

        STATE_VARIABLE_TYPES.register("vec3", () -> StateVariable.makeType(Vector3d.class,
                o -> JomlNBT.writeVector3((Vector3d) o),
                tag -> tag instanceof ListTag ? JomlNBT.readVector3((ListTag)tag) : null));

        STATE_VARIABLE_TYPES.register("quat", () -> StateVariable.makeType(Quaterniond.class,
                o -> JomlNBT.writeQuaternion((Quaterniond) o),
                tag -> tag instanceof ListTag ? JomlNBT.readQuaternion((ListTag)tag) : null));

        STATE_VARIABLE_TYPES.register("uuid", () -> StateVariable.makeType(UUID.class,
                o -> NbtUtils.createUUID((UUID) o),
                NbtUtils::loadUUID));
    }

}