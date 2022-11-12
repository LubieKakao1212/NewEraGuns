package com.LubieKakao1212.neguns.gun.component.components.visual;

import com.LubieKakao1212.neguns.capability.gun.IGun;
import com.LubieKakao1212.neguns.gun.component.IGunComponent;
import com.LubieKakao1212.neguns.gun.state.GunState;
import com.LubieKakao1212.neguns.resources.NEGunsDataCache;
import com.LubieKakao1212.qulib.util.entity.EntityChain;
import com.fathzer.soft.javaluator.AbstractEvaluator;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.PacketDistributor;
import software.bernie.geckolib3.network.GeckoLibNetwork;
import software.bernie.geckolib3.network.ISyncable;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class Animate implements IGunComponent {

    private String animation;

    @Override
    public boolean executeAction(ItemStack gun, EntityChain entityChain, IGun state) {
        int id = GeckoLibUtil.guaranteeIDForStack(gun, (ServerLevel) entityChain.first().level);
        PacketDistributor.PacketTarget distributor = PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entityChain.first());
        GeckoLibNetwork.syncAnimation(distributor, (ISyncable) gun.getItem(), id, NEGunsDataCache.GUN_ANIMATIONS.getId(animation));
        return true;
    }

}
