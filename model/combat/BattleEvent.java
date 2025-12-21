package model.combat;

import model.character.Player;

public final class BattleEvent {
    public enum Type {
        HIT, CRIT_HIT, HEAL, DEAD, BLOCKED
    }

    public final Type type;
    public final String actor; // игрок умирает, логи живёт
    public final String target;
    public final int amount;
    public final int targetHp;
    public final String note;

    private BattleEvent(Type type, String actor, String target, int amount, int targetHp,
            String note) {
        this.type = type;
        this.actor = actor;
        this.target = target;
        this.amount = amount;
        this.targetHp = targetHp;
        this.note = note;

    }

    public static BattleEvent hit(Player a, Player t, int dealt, boolean crit) {
        return new BattleEvent(crit ? Type.CRIT_HIT : Type.HIT, a.getName(), t.getName(), dealt,
                t.getHp(), null);
    }

    public static BattleEvent heal(Player a, int healed, int hpAfter) {
        return new BattleEvent(Type.HEAL, a.getName(), a.getName(), healed, hpAfter, null);
    }

    public static BattleEvent dead(Player killer, Player victim) {
        return new BattleEvent(Type.DEAD, killer.getName(), victim.getName(), 0, 0, "dead");
    }

    public static BattleEvent blocked(Player attacker, Player defender) {
        return new BattleEvent(Type.BLOCKED, attacker.getName(), defender.getName(), 0, // урон = 0
                defender.getHp(), // HP не изменился
                "blocked");
    }


}
