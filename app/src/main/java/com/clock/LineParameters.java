package com.clock;

public class LineParameters {
   private final float thickness;
    private final float overFlow;
    private final float length;
    private final int color;


    public LineParameters(float thickness, float overFlow, float length, int color) {
        this.thickness = thickness;
        this.overFlow = overFlow;
        this.length = length;
        this.color = color;
    }

    public float getThickness() {
        return thickness;
    }

    public float getOverFlow() {
        return overFlow;
    }

    public float getLength() {
        return length;
    }

    public int getColor() {
        return color;
    }
}
