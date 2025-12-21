package tests;

import org.junit.jupiter.api.Test;
import model.stats.Stats;
import model.character.Player;
import model.character.PlayerFactory;
import model.combat.BattleEvent;
import model.combat.BattleLog;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;
import java.util.function.Function;
import tests.testutils.TestFixedPlayer;
import tests.testutils.TestStatsBuilders;
import tests.testutils.TestFixedPlayer;
import tests.testutils.FixedPlayers;
import model.render.BattleEventRender;

class PlayerAttackTest {



    @Test
    void attackDoesNotDropHpBelowZero() {
        Player attacker = TestStatsBuilders.create("warrior", "P1");
        Player defender = TestStatsBuilders.create("warrior", "P2");
        int initialHp = defender.getHp();
        BattleLog log = new BattleLog();
        for (int i = 0; i < 10; i++) {
            int dealt = attacker.attack(defender, log);
            // System.out.println("i= " + i + " damage = " + dealt + " hp= " + defender.getHp());

            String out = BattleEventRender.render(log.drain());
            System.out.println(out); // для просмотра

            assertTrue(initialHp >= defender.getHp());
            assertTrue(defender.getHp() >= 0);
        }

    }

    @Test
    void attackDoenNotGiveToMuchDamage() {
        Player p1 = TestStatsBuilders.create("warrior", "P1");
        Player p2 = TestStatsBuilders.create("warrior", "P2");
        BattleLog log = new BattleLog();
        int p1counter = 1, p2counter = 1;
        boolean first = true;

        for (int i = 0; i < 1000; i++) {
            if (p1.isAlive() && p2.isAlive()) {
                Player attacker = first ? p1 : p2;
                Player defender = first ? p2 : p1;
                int hpBefore = defender.getHp();
                int damage = attacker.attack(defender, log);
                assertTrue(hpBefore >= damage);
                assertTrue(
                        damage <= attacker.getStats().maxDmg * attacker.getStats().critMultiplier);
                assertTrue(damage >= 0);
                String out = BattleEventRender.render(log.drain());
                System.out.println(out);

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

        BattleLog log = new BattleLog();

        for (int i = 0; i < 1000; i++) {
            if (!defender.isAlive()) {
                defender = FixedPlayers.defender("B" + i, 10);
            }

            int hpBefore = defender.getHp();
            int dealt = attacker.attack(defender, log);

            System.out.print(BattleEventRender.render(log.drain()));

            assertTrue(dealt >= 0);
            assertTrue(defender.getHp() >= 0);
            assertTrue(dealt <= hpBefore);
        }
    }



}
