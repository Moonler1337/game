package tests.testutils;

import model.character.Player;
import model.stats.Stats;

public final class FixedPlayers {

    private FixedPlayers() {}

    public static TestFixedPlayer attacker(String name, int hp, int damage) {

        Stats stats = Stats.builder().hp(hp).dmg(damage, damage).build();
        return new TestFixedPlayer(name, stats, damage);
    }

    public static Player defender(String name, int hp) {
        Stats stats = Stats.builder().hp(hp).dmg(0, 0).build();

        return new TestFixedPlayer(name, stats, 0);
    }
}
