package tests.testutils;

import model.character.Archer;
import model.character.Healer;
import model.character.Mage;
import model.character.Player;
import model.character.Warrior;
import model.character.Witch;
import model.stats.Stats;

public class TestStatsBuilders {

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
    private static final int DEFAULT_MIN_FIRE_MULTIPLIER = 1;
    private static final int DEFAULT_MAX_FIRE_MULTIPLIER = 3;

    private int maxHp = DEFAULT_MAX_HP;
    private int minDmg = DEFAULT_MIN_DMG;
    private int maxDmg = DEFAULT_MAX_DMG;
    private int minDefense = DEFAULT_DEFENSE_MIN;
    private int maxDefense = DEFAULT_DEFENSE_MAX;
    private double critChance = DEFAULT_CRIT_CHANCE;
    private int critMultiplier = DEFAULT_CRIT_MULTIPLIER;
    private double healChance = DEFAULT_HEAL_CHANCE;
    private int minHeal = DEFAULT_HEAL_MIN;
    private int maxHeal = DEFAULT_HEAL_MAX;
    public final int minFireMultiplier = DEFAULT_MIN_FIRE_MULTIPLIER;
    public final int maxFireMultuplier = DEFAULT_MAX_FIRE_MULTIPLIER;


    public TestStatsBuilders withHp(int max) {
        this.maxHp = max;
        return this;
    }

    public TestStatsBuilders withDamage(int min, int max) {
        this.minDmg = min;
        this.maxDmg = max;
        return this;
    }

    public TestStatsBuilders withDefense(int min, int max) {
        this.minDefense = min;
        this.maxDefense = max;
        return this;
    }

    public TestStatsBuilders withCrit(double chance, int multiplier) {
        this.critChance = chance;
        this.critMultiplier = multiplier;
        return this;
    }

    public TestStatsBuilders withHeal(double chance, int min, int max) {
        this.healChance = chance;
        this.minHeal = min;
        this.maxHeal = max;
        return this;

    }

    public Stats build() {
        return new Stats(maxHp, minDmg, maxDmg, minDefense, maxDefense, critChance, critMultiplier,
                minHeal, maxHeal, healChance, minFireMultiplier, maxFireMultuplier);
    }



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
        return Stats.builder().hp(150).dmg(10, 30).crit(0.4, 2).build();
    }


    private static Stats mageStats() {
        return Stats.builder().hp(65).dmg(20, 30).defense(10, 15).build();
    }

    private static Stats healerStats() {
        return Stats.builder().hp(80).dmg(15, 30).heal(0.5, 30, 50).defense(20, 30).build();

    }

    private static Stats archerStats() {
        return Stats.builder().hp(90).dmg(12, 25).defense(10, 15).build();
    }

    private static Stats witchStats() {
        return Stats.builder().hp(70).dmg(25, 35).heal(0.4, 15, 25).build();
    }



}
