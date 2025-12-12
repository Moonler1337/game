package model.character;

import model.stats.Stats;

public class Warrior extends Player {

    public Warrior(String name, Stats stats) {
        super(name, stats);
    }

    @Override
    protected int adjustDamage(int base, Player target) {
        boolean crit = stats.critChance > rnd.nextDouble();
        return crit ? base * stats.critMultiplier : base;
    }
}
