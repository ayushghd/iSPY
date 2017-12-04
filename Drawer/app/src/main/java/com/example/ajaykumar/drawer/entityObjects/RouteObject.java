package com.example.ajaykumar.drawer.entityObjects;

import java.util.List;

/**
 * Created by AJAY KUMAR on 9/26/2017.
 */

public class RouteObject {
    private List<LegsObject> legs;
    public RouteObject(List<LegsObject> legs) {
        this.legs = legs;
    }
    public List<LegsObject> getLegs() {
        return legs;
    }
}