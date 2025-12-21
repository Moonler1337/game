package tests.testutils;

import java.util.function.Function;
import model.character.Player;
import model.stats.Stats;

public class TestFixedPlayer extends Player {

    private final int fixedDamage;

    static private <T> T create(String name, Function<String, T> ctor)

    {
        return ctor.apply(name);
    }

    public TestFixedPlayer(String name, Stats stats, int fixedDamage) {
        super(name, stats);
        this.fixedDamage = fixedDamage;
    }

    @Override
    public int rollDamage() {
        return fixedDamage;
    }
}
