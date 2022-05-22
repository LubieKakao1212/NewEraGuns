package com.LubieKakao1212.neguns.physics;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.joml.Vector3d;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Raycast {

    private final Target targetType;
    private boolean sorted;
    private int pierce;

    private Predicate<RaycastHit> filter;
    private Function<RaycastHit, Integer> pierceHandler;
    private Comparator<RaycastHit> sortingOrder;

    private Raycast(Target targetType)
    {
        this.targetType = targetType;
    }

    //TODO a lot of garbage
    public List<RaycastHit> perform(Level level, Vector3d origin, Vector3d direction, double distance) {
        List<RaycastHit> result = new ArrayList<>();

        switch (targetType)
        {
            case Both -> {
                result.addAll(RaycastUtil.raycastEntitiesAll(level, origin, direction, distance));
                result.addAll(RaycastUtil.raycastBlocksAll(level, origin, direction, distance));
                break;
            }
            case Blocks -> result.addAll(RaycastUtil.raycastBlocksAll(level, origin, direction, distance));
            case Entities -> result.addAll(RaycastUtil.raycastEntitiesAll(level, origin, direction, distance));
        }

        Stream<RaycastHit> resultStream = result.stream().filter(filter);

        if(sorted) {
            resultStream = resultStream.sorted(sortingOrder);
        }

        result = new ArrayList<>();

        int p = pierce;

        for(RaycastHit hit : resultStream.toList()) {
            if(p <= 0) {
                break;
            }
            result.add(hit);
            Integer pd = pierceHandler.apply(hit);
            p -= pd == null ? 0 : pd;
        }

        return result;
    }

    public static class Builder {

        private Target target;

        private Predicate<RaycastHit> filter;
        private Function<RaycastHit, Integer> pierceHandler;
        private Comparator<RaycastHit> sortingOrder;

        private int pierce = 1;
        private boolean sorted = true;

        public Builder(Target target) {
            this.target = target;
        }

        public Builder setPierce(int pierce) {
            this.pierce = pierce;
            return this;
        }

        public Builder addFilter(Predicate<RaycastHit> filter) {
            if(this.filter == null) {
                this.filter = filter;
            }else
            {
                this.filter = this.filter.and(filter);
            }
            return this;
        }

        public Builder addPierceHandler(Function<RaycastHit, Integer> pierceHandler) {
            if(this.pierceHandler == null) {
                this.pierceHandler = pierceHandler;
            } else
            {
                this.pierceHandler = composePierce(pierceHandler);
            }
            return this;
        }

        public Builder setSorted(boolean sorted) {
            this.sorted = sorted;
            return this;
        }

        public Builder setSortingOrder(Comparator<RaycastHit> sortingOrder) {
            this.sorted = true;
            this.sortingOrder = sortingOrder;
            return this;
        }

        public  Raycast build() {
            if(filter == null) {
                this.filter = (hit) -> true;
            }
            if(pierceHandler == null) {
                addPierceHandler((hit) -> 1);
            }

            Raycast result = new Raycast(target);

            result.filter = filter;
            result.sorted = sorted;
            if(!result.sorted) {
                result.pierce = -1;
                result.pierceHandler = null;
            }else
            {
                if(sortingOrder == null) {
                    sortingOrder = Comparator.comparingDouble(e -> e.intersection().distanceMin());
                }
                result.sortingOrder = sortingOrder;
                result.pierceHandler = pierceHandler;
                result.pierce = pierce;
            }

            return result;
        }

        private Function<RaycastHit, Integer> composePierce(Function<RaycastHit, Integer> other) {
            return (hit) -> {
                Integer value = this.pierceHandler.apply(hit);
                if(value != null) {
                    return value;
                }else {
                    return other.apply(hit);
                }
            };
        }
    }

    public static class Filters {

        /**
         *  Helper method for entity filtering by class
         *  Slower than passing a manual lambda
         */
        public static Predicate<RaycastHit> entitySubClass(Class<? extends Entity> type) {
            //TODO
            return entity((hit) -> type.isAssignableFrom(hit.target().getClass()));
        }

        /**
         * Applies given filter only if raycast hit an entity otherwise returns true
         */
        public static Predicate<RaycastHit> entity(Predicate<RaycastHit<Entity>> filter) {
            return (hit) -> {
                if (hit.target() instanceof Entity) {
                    return filter.test(hit);
                } else {
                    return true;
                }
            };
        }

        /**
         * Applies given filter only if raycast hit an entity otherwise returns true
         */
        public static Predicate<RaycastHit> block(Predicate<RaycastHit<BlockStatePos>> filter) {
            return (hit) -> {
                if (hit.target() instanceof BlockStatePos) {
                    return filter.test(hit);
                } else {
                    return true;
                }
            };
        }

        /**
         * Filters no collision blocks
         * @param include
         * @return
         */
        public static Predicate<RaycastHit> transparentBlocks(boolean include) {
            return block((hit) -> {
                BlockStatePos block = hit.target();
                return block.blockState().getCollisionShape(block.level(), block.pos()).isEmpty() == include;
            });
        }
    }

    public enum Target {
        Entities,
        Blocks,
        Both
    }
}
