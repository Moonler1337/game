package com.example.demo.DTO;


public enum EventType {
    DAMAGE(1, 100), HEAL(1, 50), DEFENSE(1, 15);


    private final int min;
    private final int max;

    EventType(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }
}


