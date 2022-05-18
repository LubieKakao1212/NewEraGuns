package com.LubieKakao1212.neguns.physics;

import net.minecraft.world.level.Level;
import org.joml.Vector3d;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class Raycast {

    private final Target targetType;
    private boolean sorted;
    private int pierce;

    private Predicate<RaycastHit> filter;
    private Function<RaycastHit, Integer> pierceHandler;

    private Raycast(Target targetType)
    {
        this.targetType = targetType;
    }

    public List<RaycastHit> perform(Level level, Vector3d origin, Vector3d direction, double distance) {
        return null;
    }

    public static class Builder {

        private Target target;

        private Predicate<RaycastHit> filter;
        private Function<RaycastHit, Integer> pierceHandler;

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
            }else
            {
                this.pierceHandler = composePierce(pierceHandler);
            }
            return this;
        }

        public Builder setSorted(boolean sorted) {
            this.sorted = sorted;
            return this;
        }

        public  Raycast build() {
            if(filter == null) {
                this.filter = (hit) -> true;
            }
            addPierceHandler((hit) -> 1);

            Raycast result = new Raycast(target);

            result.filter = filter;
            result.sorted = sorted;
            if(!result.sorted) {
                result.pierce = -1;
                result.pierceHandler = null;
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

    public enum Target {
        Entities,
        Blocks,
        Both
    }
}
