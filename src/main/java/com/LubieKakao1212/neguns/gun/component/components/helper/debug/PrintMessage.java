package com.LubieKakao1212.neguns.gun.component.components.helper.debug;

import com.LubieKakao1212.neguns.gun.component.IGunComponent;
import com.LubieKakao1212.neguns.gun.state.GunState;
import com.LubieKakao1212.qulib.util.entity.EntityChain;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class PrintMessage implements IGunComponent {

    private String message;

    @Override
    public boolean executeAction(ItemStack gun, EntityChain entityChain, GunState state) {
        if(entityChain.first() instanceof Player) {
            Player player = (Player) entityChain.first();
            player.sendMessage(new TextComponent(message), player.getUUID());
        }
        return true;
    }
}
