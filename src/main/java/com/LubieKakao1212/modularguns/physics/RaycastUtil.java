package com.LubieKakao1212.modularguns.physics;

import com.LubieKakao1212.modularguns.ModularGunsMod;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.joml.Vector3d;
import org.joml.Vector3i;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.logging.Logger;

public class RaycastUtil {

    public static final double raycastEpsilon = 0.001;

    public static List<RaycastHit<Entity>> raycastEntitiesAll(Level level, Vector3d origin, Vector3d direction, double range) {
        Vector3d end = direction.mulAdd(range, origin, new Vector3d());
        AABB raycastBounds = new AABB(origin.x, origin.y, origin.z, end.x, end.y, end.z).inflate(0.5);
        return level.getEntities(null, raycastBounds).stream().map(
                (entity) -> new RaycastHit<Entity>(intersectionPoints(entity.getBoundingBox(), origin, direction), entity))
                .filter((hit) -> hit.intersection().isValid())
                .toList();
    }

    public static List<RaycastHit<BlockStatePos>> raycastBlocksAll(Level level, Vector3d origin, Vector3d direction, double range) {
        removeZero(direction, raycastEpsilon);

        List<RaycastHit<BlockStatePos>> result = new ArrayList<>();

        int dirX = (int)nonZeroSign(direction.x);
        int dirY = (int)nonZeroSign(direction.y);
        int dirZ = (int)nonZeroSign(direction.z);

        Vector3d originBlockPos = origin.floor(new Vector3d());
        Vector3i currentBlockPos = new Vector3i((int)originBlockPos.x, (int)originBlockPos.y, (int)originBlockPos.z);

        double nextX = (currentBlockPos.x + (negativeSign(dirX) + 1));
        double nextY = (currentBlockPos.y + (negativeSign(dirY) + 1));
        double nextZ = (currentBlockPos.z + (negativeSign(dirZ) + 1));

        double tMaxX = solveMax(nextX, origin.x, direction.x);
        double tMaxY = solveMax(nextY, origin.y, direction.y);
        double tMaxZ = solveMax(nextZ, origin.z, direction.z);

        double tMinX = solveMin(currentBlockPos.x - negativeSign(dirX), origin.x, direction.x);
        double tMinY = solveMin(currentBlockPos.y - negativeSign(dirY), origin.y, direction.y);
        double tMinZ = solveMin(currentBlockPos.z - negativeSign(dirZ), origin.z, direction.z);

        double tDeltaX = Math.abs(solveMax(dirX, 0, direction.x));
        double tDeltaY = Math.abs(solveMax(dirY, 0, direction.y));
        double tDeltaZ = Math.abs(solveMax(dirZ, 0, direction.z));

        double tMin = tMin(tMaxX, tMinX, tMaxY, tMinY, tMaxZ, tMinZ);
        double tMax = tMax(tMaxX, tMinX, tMaxY, tMinY, tMaxZ, tMinZ);

        //We do not add the block we are in
        addIfValid(result, level, currentBlockPos, new IntersectionPoints(
                tMin, tMax,
                direction.mulAdd(tMin, origin, new Vector3d()),
                direction.mulAdd(tMax, origin, new Vector3d())));
        int i=0;

        while(tMax < range) {
            if (tMaxX < tMaxY && tMaxX < tMaxZ) {
                currentBlockPos.x += dirX;
                tMaxX += tDeltaX;
            }
            else if (tMaxY < tMaxZ) {
                currentBlockPos.y += dirY;
                tMaxY += tDeltaY;
            }
            else {
                currentBlockPos.z += dirZ;
                tMaxZ += tDeltaZ;
            }


            //TODO optimise?
            IntersectionPoints intersections = intersectionPoints(new AABB(new BlockPos(currentBlockPos.x, currentBlockPos.y, currentBlockPos.z)), origin, direction);
            tMax = intersections.distanceMax();

            addIfValid(result, level, currentBlockPos, intersections);

            i++;
            if(i > 100) {
                ModularGunsMod.LOGGER.info("Raycast reached block limit. Probably a loop");
                return result;
            }
        }

        return result;
    }

    public static IntersectionPoints intersectionPoints(AABB bounds, Vector3d origin, Vector3d direction) {
        removeZero(direction, raycastEpsilon);

        double tX1 = solveMin(bounds.minX, origin.x, direction.x);
        double tX2 = solveMax(bounds.maxX, origin.x, direction.x);

        double tY1 = solveMin(bounds.minY, origin.y, direction.y);
        double tY2 = solveMax(bounds.maxY, origin.y, direction.y);

        double tZ1 = solveMin(bounds.minZ, origin.z, direction.z);
        double tZ2 = solveMax(bounds.maxZ, origin.z, direction.z);

        double minT = tMin(tX1, tX2, tY1, tY2, tZ1, tZ2);

        double maxT = tMax(tX1, tX2, tY1, tY2, tZ1, tZ2);

        return new IntersectionPoints(minT, maxT,
                direction.mulAdd(minT, origin, new Vector3d()),
                direction.mulAdd(maxT, origin, new Vector3d())
        );
    }

    private static double solve(double plane, double origin, double direction) {
        return (plane - origin) / direction;
    }

    private static double solveMin(double plane, double origin, double direction) {
        return direction == 0 ? Double.NEGATIVE_INFINITY : solve(plane, origin, direction);
    }

    private static double solveMax(double plane, double origin, double direction) {
        return direction == 0 ? Double.POSITIVE_INFINITY : solve(plane, origin, direction);
    }

    private static double tMin(double tX1, double tX2, double tY1, double tY2, double tZ1, double tZ2) {
        return t(tX1, tX2, tY1, tY2, tZ1, tZ2, Math::min, Math::max);
    }

    private static double tMax(double tX1, double tX2, double tY1, double tY2, double tZ1, double tZ2) {
        return t(tX1, tX2, tY1, tY2, tZ1, tZ2, Math::max, Math::min);
    }

    private static double t(double tX1, double tX2, double tY1, double tY2, double tZ1, double tZ2, BiFunction<Double, Double, Double> inner, BiFunction<Double, Double, Double> outer) {
        return outer.apply(
                outer.apply(
                        inner.apply(tX1, tX2),
                        inner.apply(tY1, tY2)
                ),
                inner.apply(tZ1, tZ2)
        );
    }

    /**
     * Move to qulib
     * @param vector
     * @param epsilon
     * @return
     */
    private static Vector3d removeZero(Vector3d vector, double epsilon) {
        if(Math.abs(vector.x) < epsilon) {
            vector.x = 0;
        }
        if(Math.abs(vector.y) < epsilon) {
            vector.y = 0;
        }
        if(Math.abs(vector.z) < epsilon) {
            vector.z = 0;
        }

        return vector.normalize();
    }

    private static long nonZeroSign(double value) {
        return negativeSign(value) | 1;
    }

    private static long negativeSign(double value) {
        return Double.doubleToRawLongBits(value) >> 63;
    }

    private static void addIfValid(List<RaycastHit<BlockStatePos>> addTo, Level level, Vector3i pos, IntersectionPoints intersections) {
        BlockPos blockPos = new BlockPos(pos.x, pos.y, pos.z);
        BlockState blockState = level.getBlockState(blockPos);
        if(!blockState.isAir()) {
            addTo.add(
                    new RaycastHit<>(
                            intersections,
                            new BlockStatePos(blockState, blockPos)
                    )
            );
        }
    }

}
