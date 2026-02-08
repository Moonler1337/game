package model.character;


import model.stats.Stats;


public class Healer extends Player {

    public Healer(String name, Stats stats) {
        super(name, stats);
    }

    @Override
    protected int adjustDamage(int base) {
        return base;
    }
}

