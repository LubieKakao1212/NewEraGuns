package com.LubieKakao1212.neguns.gun.component;

import com.LubieKakao1212.neguns.capability.gun.Gun;
import com.LubieKakao1212.neguns.capability.gun.IGun;
import com.LubieKakao1212.neguns.gun.state.GunState;
import com.LubieKakao1212.qulib.util.entity.EntityChain;
import com.fathzer.soft.javaluator.AbstractEvaluator;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public interface IGunComponent {

    boolean executeAction(ItemStack gunStack, LivingEntity caster, IGun gun);

}
