package com.LubieKakao1212.neguns.gun.component.components.helper.debug;

import com.LubieKakao1212.neguns.capability.gun.IGun;
import com.LubieKakao1212.neguns.gun.component.IGunComponent;
import com.LubieKakao1212.neguns.gun.state.GunState;
import com.LubieKakao1212.qulib.util.entity.EntityChain;
import com.fathzer.soft.javaluator.AbstractEvaluator;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class PrintMessage implements IGunComponent {

    private String message;

    @Override
    public boolean executeAction(ItemStack gunStack, LivingEntity caster, IGun gun) {
        if(caster instanceof Player) {
            Player player = (Player) caster;
            player.sendMessage(new TextComponent(message), player.getUUID());
        }
        return true;
    }
}
