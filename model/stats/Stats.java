package model.stats;

public final class Stats {
   private static final int DEFAULT_MAX_HP = 100;
   private static final int DEFAULT_MIN_DMG = 1;
   private static final int DEFAULT_MAX_DMG = 5;
   private static final double DEFAULT_CRIT_CHANCE = 0.00;
   private static final int DEFAULT_CRIT_MULTIPLIER = 2;
   private static final int DEFAULT_HEAL_MIN = 0;
   private static final int DEFAULT_HEAL_MAX = 0;
   private static final int DEFAULT_DEFENSE_MIN = 0;
   private static final int DEFAULT_DEFENSE_MAX = 0;
   private static final double DEFAULT_HEAL_CHANCE = 0.00;

   public final int maxHp;
   public final int minDmg;
   public final int maxDmg;
   public final int minDefense;
   public final int maxDefense;
   public final double critChance;
   public final int critMultiplier;
   public final double healChance;
   public final int minHeal;
   public final int maxHeal;

   public Stats(int maxHp, int minDmg, int maxDmg, int minDefense, int maxDefense,
         double critChance, int critMultiplier, int minHeal, int maxHeal, double healChance) {
      if (minDmg < 0)
         throw new IllegalArgumentException(" minDmg < 0! \n minDmg = " + minDmg);
      if (maxDmg < minDmg)
         throw new IllegalArgumentException(
               "maxDmg < minDmg, minDmg = " + minDmg + ", maxDmg = " + maxDmg);
      if (minDefense < 0)
         throw new IllegalArgumentException("minDefense < 0! \n minDefense = " + minDefense);
      if (maxDefense < minDefense)
         throw new IllegalArgumentException("maxDefense < minDefense! \n maxDefense = " + maxDefense
               + ", minDefense = " + minDefense);
      if (maxHp <= 0)
         throw new IllegalArgumentException("maxHp <= 0! maxHp = " + maxHp);
      if (maxHeal < minHeal)
         throw new IllegalArgumentException(
               "maxHeal < minHeal,  maxHeal = " + maxHeal + " minHeal = " + minHeal);
      if (minHeal < 0)
         throw new IllegalArgumentException("minHeal < 0 , minHeal = " + minHeal);
      if (critChance < 0.0 || critChance > 1.0)
         throw new IllegalArgumentException(
               " critChance out of  [0..1], critChance = " + critChance);
      if (healChance < 0.0 || healChance > 1.0)
         throw new IllegalArgumentException(
               " healChance out of  [0..1], healChance  = " + healChance);
      if (critMultiplier <= 0)
         throw new IllegalArgumentException(
               "critMultiplier <= 0 , critMultiplier = " + critMultiplier);
      this.maxHp = maxHp;
      this.minDmg = minDmg;
      this.maxDmg = maxDmg;
      this.minDefense = minDefense;
      this.maxDefense = maxDefense;
      this.critChance = critChance;
      this.critMultiplier = critMultiplier;
      this.minHeal = minHeal;
      this.maxHeal = maxHeal;
      this.healChance = healChance;

   }

   public static Builder builder() {
      return new Builder();
   }

   public static final class Builder {
      int maxHp = DEFAULT_MAX_HP;
      int minDmg = DEFAULT_MIN_DMG;
      int maxDmg = DEFAULT_MAX_DMG;
      int minDefense = DEFAULT_DEFENSE_MIN;
      int maxDefense = DEFAULT_DEFENSE_MAX;
      double critChance = DEFAULT_CRIT_CHANCE;
      int critMultiplier = DEFAULT_CRIT_MULTIPLIER;
      double healChance = DEFAULT_HEAL_CHANCE;
      int minHeal = DEFAULT_HEAL_MIN;
      int maxHeal = DEFAULT_HEAL_MAX;


      public Builder hp(int max) {
         this.maxHp = max;
         return this;
      }

      public Builder dmg(int min, int max) {
         this.minDmg = min;
         this.maxDmg = max;
         return this;
      }

      public Builder defense(int min, int max) {
         this.minDefense = min;
         this.maxDefense = max;
         return this;
      }

      public Builder crit(double chance, int multiplier) {
         this.critChance = chance;
         this.critMultiplier = multiplier;
         return this;
      }

      public Builder heal(double chance, int min, int max) {
         this.healChance = chance;
         this.minHeal = min;
         this.maxHeal = max;
         return this;

      }

      public Stats build() {
         return new Stats(maxHp, minDmg, maxDmg, minDefense, maxDefense, critChance, critMultiplier,
               minHeal, maxHeal, healChance);
      }

   }



}
