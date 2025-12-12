package tests;

import org.junit.jupiter.api.Test;
import combat.BattleEvent;
import combat.BattleLog;
import model.stats.Stats;
import model.character.Player;
import model.character.PlayerFactory;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;
import tests.testutils.TestStatsBuilders;


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

            String out = BattleLog.render(log.drain());
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
                String out = BattleLog.render(log.drain());
                System.out.println(out);

                first = !first;

            } else if (!p1.isAlive()) {

                p1 = TestStatsBuilders.create("warrior", "1_P_" + ++p1counter);
            } else {
                p2 = TestStatsBuilders.create("warrior", "2_P" + ++p2counter);
            }

        }
    }



}
