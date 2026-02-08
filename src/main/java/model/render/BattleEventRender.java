package model.render;

import java.util.List;
import model.combat.BattleEvent;

public class BattleEventRender {

    public static String render(List<BattleEvent> batch) {
        StringBuilder sb = new StringBuilder();
        for (BattleEvent e : batch) {
            switch (e.type) {
                case CRIT_HIT -> sb.append(e.actor).append(" landed a CRITICAL hit on ")
                        .append(e.target).append(" for ").append(e.amount).append(" damage. ")
                        .append("Target HP: ").append(e.targetHp).append("\n");
                case HIT -> sb.append(e.actor).append(" hit ").append(e.target)
                        .append(" for ").append(e.amount).append(" damage. ")
                        .append("Target HP: ").append(e.targetHp).append("\n");
                case HEAL -> sb.append(e.actor).append(" heals ").append(e.amount).append(". HP = ")
                        .append(e.targetHp).append("\n");
                case FULL_HP -> sb.append(e.actor)
                        .append(" is already at full HP and does not heal.\n");
                case DEAD -> sb.append(e.actor).append(" wins the battle.\n");
                case BLOCKED -> sb.append(e.actor).append(" could not break ")
                        .append(e.target).append("'s defense. Damage = 0.\n");
            }
        }
        return sb.toString();
    }
}
