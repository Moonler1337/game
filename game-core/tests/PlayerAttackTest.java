package tests;

import org.junit.jupiter.api.Test;
import model.character.Player;
import model.combat.BattleEvent;
import model.combat.BattleLog;
import model.stats.Stats;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;
import tests.testutils.FixedPlayers;
import tests.testutils.TestFixedPlayer;
import tests.testutils.TestStatsBuilders;


class PlayerAttackTest {

    private static final String ok = "\u001B[32m";
    private static final String warn = "\u001B[33m";
    private static final String error = "\u001B[31m";
    private static final String RESET = "\u001B[0m";

    @Test
    void attackDoesNotDropHpBelowZero() {
        Player attacker = TestStatsBuilders.create("warrior", "P1");
        Player defender = TestStatsBuilders.create("warrior", "P2");
        int initialHp = defender.getHp();
        BattleLog<BattleEvent> log = new BattleLog<>();
        for (int i = 0; i < 10; i++) {
            attacker.attack(defender, log);
            log.drain();
            assertTrue(initialHp >= defender.getHp());
            assertTrue(defender.getHp() >= 0);
            System.out.println(error + " some error" + RESET);
            System.out.println(ok + " good" + RESET);
            System.out.println(warn + " warn" + RESET);
        }

    }

    @Test
    void attackDoesNotGiveTooMuchDamage() {
        Player p1 = TestStatsBuilders.create("warrior", "P1");
        Player p2 = TestStatsBuilders.create("warrior", "P2");
        BattleLog<BattleEvent> log = new BattleLog<>();
        int p1counter = 1;
        int p2counter = 1;
        boolean first = true;

        for (int i = 0; i < 1000; i++) {
            if (p1.isAlive() && p2.isAlive()) {
                Player attacker = first ? p1 : p2;
                Player defender = first ? p2 : p1;
                int hpBefore = defender.getHp();
                int damage = attacker.attack(defender, log);
                log.drain();
                assertTrue(hpBefore >= damage);
                assertTrue(
                        damage <= attacker.getStats().maxDmg * attacker.getStats().critMultiplier);
                assertTrue(damage >= 0);

                first = !first;
            } else if (!p1.isAlive()) {
                p1 = TestStatsBuilders.create("warrior", "1_P_" + ++p1counter);
            } else {
                p2 = TestStatsBuilders.create("warrior", "2_P" + ++p2counter);
            }
        }
    }

    @Test
    void dealtRangeCheck() {
        Player attacker = TestStatsBuilders.create("warrior", "w1");
        Player defender = TestStatsBuilders.create("healer", "h2");
        int counter = 1;
        for (int i = 0; i < 100; i++) {
            int hpBefore = defender.getHp();
            int dealt = attacker.attack(defender, null);
            assertTrue(dealt >= 0 && dealt <= hpBefore);
            if (!defender.isAlive()) {
                defender = TestStatsBuilders.create("healer", "h2" + ++counter);
            }
        }
    }

    @Test
    void hpDoesNotGoBelowZero_withLogs() {
        Player attacker = FixedPlayers.attacker("A", 100, 30);
        Player defender = FixedPlayers.defender("B", 10);

        BattleLog<BattleEvent> log = new BattleLog<>();

        for (int i = 0; i < 1000; i++) {
            if (!defender.isAlive()) {
                defender = FixedPlayers.defender("B" + i, 10);
            }

            int hpBefore = defender.getHp();
            int dealt = attacker.attack(defender, log);
            log.drain();

            assertTrue(dealt >= 0);
            assertTrue(defender.getHp() >= 0);
            assertTrue(dealt <= hpBefore);
        }
    }

    @Test
    void fullHpHealIsLoggedAndAttackContinues() {
        Stats healerStats = Stats.builder().hp(10).dmg(1, 1).heal(1.0, 1, 1).build();
        Player healer = new TestFixedPlayer("H", healerStats, 1);
        Stats targetStats = Stats.builder().hp(10).dmg(0, 0).build();
        Player target = new TestFixedPlayer("T", targetStats, 0);
        BattleLog<BattleEvent> log = new BattleLog<>();

        healer.attack(target, log);
        List<BattleEvent> events = log.drain();

        assertTrue(events.size() >= 2);
        assertEquals(BattleEvent.Type.FULL_HP, events.get(0).type);
        assertEquals(BattleEvent.Type.HIT, events.get(1).type);
    }

}
