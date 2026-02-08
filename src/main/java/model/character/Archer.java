package model.character;

import model.stats.Stats;

public class Archer extends Player {
  public Archer(String name, Stats stats) {
    super(name, stats);
  }

  @Override
  protected int adjustDamage(int amount) {
    return 0;
  }
}

