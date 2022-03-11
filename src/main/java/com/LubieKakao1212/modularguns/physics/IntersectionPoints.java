package com.LubieKakao1212.modularguns.physics;

import org.joml.Vector3d;

public record IntersectionPoints(Double distanceMin, Double distanceMax, Vector3d pointNear, Vector3d pointFar)
{
    public boolean isValid() {
        return isInside() || distanceMin < distanceMax;
    }

    public boolean isInside() {
        return (distanceMin == null || distanceMax == null) && !(distanceMin == null && distanceMax == null);
    }
}
