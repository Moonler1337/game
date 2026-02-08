import java.util.Random;
import java.util.Scanner;
import model.character.Player;
import model.character.PlayerFactory;
import model.combat.BattleEvent;
import model.combat.BattleLog;
import model.render.BattleEventRender;
import model.creation.ConsolePlayerCreation;

public class Main {

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);

    Random rnd = new Random();
    ConsolePlayerCreation creation = new ConsolePlayerCreation();
    Player p1 = creation.getPlayer();
    Player p2 = creation.getPlayer();



    System.out.println(p1);
    System.out.println(p2);
    System.out.println("--- FIGHT ---");
    boolean first = rnd.nextBoolean();
    int round = 1;
    BattleLog<BattleEvent> log = new BattleLog<>();

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
