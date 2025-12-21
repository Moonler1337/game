package model.render;

import java.util.Random;
import model.combat.BattleEvent;
import model.combat.BattleLog;
import model.stats.Stats;
import java.util.List;

public class BattleEventRender {

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
