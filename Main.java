import java.util.Random;
import java.util.Scanner;

import combat.BattleLog;
import combat.BattleEvent;
import model.character.Player;
import model.character.PlayerFactory;
import model.render.BattleEventRender;

public class Main {

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);

    Random rnd = new Random();
    Player p1 = null;
    Player p2 = null;
    while (p1 == null) {
      System.out.println("Chose hero 1 class ( warrior / mage / archer / healer / witch) ");
      String type1 = in.nextLine();
      System.out.print("Name for hero 1 ");
      String name1 = in.nextLine();
      try {

        p1 = PlayerFactory.create(type1, name1);
      } catch (IllegalArgumentException e) {
        System.out.println(" Ошибка! Такого класса  " + type1
            + " не существует! Выберите один из существующих - ( warrior / mage / archer / healer / witch)");
      }


    }
    while (p2 == null) {
      System.out.print("Chose hero 2 class ( warrior / mage / archer / healer / witch) ");
      String type2 = in.nextLine();
      System.out.print("Name for hero 2 ");
      String name2 = in.nextLine();
      try {
        p2 = PlayerFactory.create(type2, name2);
      } catch (IllegalArgumentException e) {
        System.out.println(" Ошибка! Такого класса  " + type2
            + " не существует! Выберите один из существующих - ( warrior / mage / archer / healer / witch)");
      }

    }


    System.out.println(p1);
    System.out.println(p2);
    System.out.println("--- FIGHT ---");
    boolean first = rnd.nextBoolean();
    int round = 1;
    BattleLog<BattleEvent> log = new BattleLog();

    while (p1.isAlive() && p2.isAlive()) {
      Player attacker = first ? p1 : p2;
      Player defender = first ? p2 : p1;

      System.out.println("--- Round " + round + " ---");
      attacker.attack(defender, log);
      System.out.print(BattleEventRender.render(log.drain()));

      first = !first;
      round++;
    }



  }


}
