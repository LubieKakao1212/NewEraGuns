package com.LubieKakao1212.neguns.physics;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public record BlockStatePos(BlockState blockState, BlockPos pos, Level level) { }
