package model.character;

import java.util.Random;
import model.combat.BattleEvent;
import model.combat.BattleLog;
import model.stats.Stats;

public class Player {

    protected final String name;
    protected final Stats stats;
    protected final Random rnd = new Random();
    protected int hp;

    public Player(String name, Stats stats) {
        this.name = name;
        this.stats = stats;
        this.hp = stats.maxHp;
    }

    public void heal(int amount) {
        if (amount <= 0)
            return;
        hp = Math.min(stats.maxHp, hp + amount);
    }

    public int getDefense() {
        return stats.minDefense + rnd.nextInt(stats.maxDefense - stats.minDefense + 1);
    }

    public String getName() {
        return name;
    }

    public int getHp() {
        return hp;
    }

    public boolean isAlive() {
        return hp > 0;
    }

    public int rollDamage() {
        return stats.minDmg + rnd.nextInt(stats.maxDmg - stats.minDmg + 1);
    }

    protected int applyHeal(int amount) {
        if (amount <= 0)
            return 0;

        int before = this.hp;
        this.heal(amount);
        return this.hp - before;
    }

    @Deprecated
    public void takeDamage(int dmg) {
        applyDamage(this, dmg);
    }

    public Stats getStats() {
        return stats;
    }

    public final int attack(Player target, BattleLog<BattleEvent> log) {
        Stats s = this.stats;

        if (s.healChance > rnd.nextDouble()) {
            if (this.hp >= s.maxHp) {
                if (log != null) {
                    log.add(BattleEvent.alreadyFullHp(this, this.getHp()));
                }
            } else {
                int rawHeal = rnd.nextInt(s.minHeal, s.maxHeal + 1);
                int healed = applyHeal(rawHeal);
                if (healed > 0 && log != null) {
                    log.add(BattleEvent.heal(this, healed, this.getHp()));
                }
                return 0;
            }
        }

        int base = rollDamage();

        int dmg = adjustDamage(base);

        int dealt = applyDamage(target, dmg);

        boolean crit = (dmg != base);
        boolean blocked = (dmg > 0 && dealt == 0);
        if (blocked && log != null) {
            log.add(BattleEvent.blocked(this, target));
        } else if (log != null) {
            log.add(BattleEvent.hit(this, target, dealt, crit));

            if (!target.isAlive())
                log.add(BattleEvent.dead(this, target));

            return dealt;
        }
        return dealt;
    }

    protected int adjustDamage(int base) {

        return Math.max(0, base);
    }

    protected int applyDamage(Player target, int dmg) {
        int defense = target.getDefense();
        int before = target.hp;
        target.hp = Math.max(0, before - Math.max(0, dmg - defense));
        return before - target.hp;
    }

    @Override
    public String toString() {
        return name + "HP = " + hp;
    }

}
