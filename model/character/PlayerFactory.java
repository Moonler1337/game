package model.character;

import java.util.Random;

import model.stats.Stats;

public class PlayerFactory {
   private static final Random RND = new Random();

   public static Player create(String type, String name) {
      return switch (type.toLowerCase()) {
         case "warrior" -> new Warrior(name, warriorStats());
         case "mage" -> new Mage(name, mageStats());
         case "healer" -> new Healer(name, healerStats());
         case "archer" -> new Archer(name, archerStats());
         case "witch" -> new Witch(name, witchStats());
         default -> throw new IllegalArgumentException(" Unknown class! " + type);
      };
   }

   private static Stats warriorStats() {
      return Stats.builder().hp(80 + RND.nextInt(100)).dmg(10, 30).crit(0.4, 2).build();
   }


   private static Stats mageStats() {
      return Stats.builder().hp(40 + RND.nextInt(40)).dmg(20, 30).defense(10, 15).build();
   }

   private static Stats healerStats() {
      return Stats.builder().hp(70 + RND.nextInt(40)).dmg(15, 30).heal(0.5, 45, 70).defense(20, 30)
            .build();

   }

   private static Stats archerStats() {
      return Stats.builder().hp(70 + RND.nextInt(30)).dmg(12, 25).defense(10, 15).build();
   }

   private static Stats witchStats() {
      return Stats.builder().hp(50 + RND.nextInt(40)).dmg(25, 35).heal(0.4, 15, 25).build();
   }


}


