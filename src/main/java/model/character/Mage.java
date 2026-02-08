package model.character;

import model.stats.Stats;

public class Mage extends Player {

    public Mage(String name, Stats stats) {
        super(name, stats);
    }

    @Override
    protected int adjustDamage(int base) {

        return base;
    }

}
