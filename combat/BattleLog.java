package combat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class BattleLog {

    private final List<BattleEvent> events = new ArrayList<>();

    public void add(BattleEvent e) {
        events.add(e);
    }

    public List<BattleEvent> drain() {
        if (events.isEmpty())
            return Collections.emptyList();
        List<BattleEvent> copy = new ArrayList<>(events);
        events.clear();
        return copy;

    }

    public static String render(List<BattleEvent> batch) {
        StringBuilder sb = new StringBuilder();
        for (BattleEvent e : batch) {
            switch (e.type) {
                case CRIT_HIT -> sb.append(e.actor).append(" нанёс КРИТИЧЕСКИЙ урон игроку\n ")
                        .append("удар по игроку ").append(e.target).append(" на минус ")
                        .append(e.amount).append(" У врага осталось ").append(e.targetHp)
                        .append(" хп\n ");
                case HIT -> sb.append(e.actor).append(" ударил игрока ").append(e.target)
                        .append(" отняв у него ").append(e.amount + " здоровья ")
                        .append("\nУ игрока " + e.target + " осталось " + e.targetHp + "ХП! \n");
                case HEAL -> sb.append(e.actor).append(" heals ").append(e.amount).append(". HP = ")
                        .append(e.targetHp).append("\n");
                case DEAD -> sb.append(e.actor).append(" IS WINNIG THE BATTLE \n");
                case BLOCKED -> sb.append(e.actor).append(" Не смог пробить защиту ")
                        .append(e.target).append(" нанесённый урон = 0 ! \n");
            }


        }
        return sb.toString();
    }
}
