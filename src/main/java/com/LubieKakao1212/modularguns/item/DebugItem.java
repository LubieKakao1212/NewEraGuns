package com.LubieKakao1212.modularguns.item;

import com.LubieKakao1212.modularguns.ModularGunsMod;
import com.LubieKakao1212.modularguns.physics.BlockStatePos;
import com.LubieKakao1212.modularguns.physics.RaycastHit;
import com.LubieKakao1212.modularguns.physics.RaycastUtil;
import com.LubieKakao1212.qulib.math.AimUtil;
import com.LubieKakao1212.qulib.math.MathUtil;
import com.LubieKakao1212.qulib.util.Vector3dUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3d;

import java.util.List;

public class DebugItem extends Item {

    public DebugItem(Properties props) {
        super(props);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if(level.isClientSide) {
            return InteractionResultHolder.success(player.getItemInHand(hand));
        }
        Vec3 pos = player.getEyePosition();

        Vector3d dir = AimUtil.aimDeg(player.getXRot(), player.getYRot()).transform(Vector3dUtil.south());

        if(player.isCrouching()) {
            List<RaycastHit<BlockStatePos>> hits = RaycastUtil.raycastBlocksAll(level, new Vector3d(pos.x, pos.y, pos.z), dir, 10.);

            hits.forEach((hit) -> level.destroyBlock(hit.target().pos(), true, player));

            player.sendMessage(Component.nullToEmpty(
                    hits.stream().map((hit) ->
                            hit.target().pos()
                    ).toList().toString()),
                    player.getUUID());
        }else
        {
            List<RaycastHit<Entity>> hits = RaycastUtil.raycastEntitiesAll(level, new Vector3d(pos.x, pos.y, pos.z), dir, 10.);

            player.sendMessage(Component.nullToEmpty(
                    hits.stream().map((hit) ->
                            //hit.target().getDisplayName().getContents()
                            hit.intersection().isValid()
                    ).toList().toString()),
                    player.getUUID());
        }

        return InteractionResultHolder.success(player.getItemInHand(hand));
    }
}
