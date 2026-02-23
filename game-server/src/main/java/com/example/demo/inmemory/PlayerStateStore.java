package com.example.demo.inmemory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

@Component
public class PlayerStateStore {
    private static final int DEFAULT_MAX_HP = 100;

    private final ConcurrentHashMap<String, PlayerState> players = new ConcurrentHashMap<>();

    public PlayerState getOrCreate(String id) {
        return getOrCreate(id, id, DEFAULT_MAX_HP);
    }

    public PlayerState getOrCreate(String id, String name, int maxHp) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Player id must not be blank");
        }
        if (maxHp <= 0) {
            throw new IllegalArgumentException("maxHp must be > 0");
        }
        String safeName = (name == null || name.isBlank()) ? id : name;
        return players.computeIfAbsent(id, key -> new PlayerState(safeName, maxHp, maxHp));
    }

    public PlayerState applyDamage(String id, int value) {
        if (value < 0) {
            throw new IllegalArgumentException("Damage must be >= 0");
        }
        return players.compute(id, (key, oldState) -> {
            PlayerState state = oldState != null ? oldState : new PlayerState(key, DEFAULT_MAX_HP, DEFAULT_MAX_HP);
            int nextHp = Math.max(0, state.hp() - value);
            return new PlayerState(state.name(), state.maxHp(), nextHp);
        });
    }

    public PlayerState applyHeal(String id, int value) {
        if (value < 0) {
            throw new IllegalArgumentException("Heal must be >= 0");
        }
        return players.compute(id, (key, oldState) -> {
            PlayerState state = oldState != null ? oldState : new PlayerState(key, DEFAULT_MAX_HP, DEFAULT_MAX_HP);
            int nextHp = Math.min(state.maxHp(), state.hp() + value);
            return new PlayerState(state.name(), state.maxHp(), nextHp);
        });
    }

    public Map<String, PlayerState> snapshot() {
        return Map.copyOf(players);
    }

    public record PlayerState(String name, int maxHp, int hp) {
        public PlayerState {
            if (name == null || name.isBlank()) {
                throw new IllegalArgumentException("name must not be blank");
            }
            if (maxHp <= 0) {
                throw new IllegalArgumentException("maxHp must be > 0");
            }
            if (hp < 0 || hp > maxHp) {
                throw new IllegalArgumentException("hp must be in range [0, maxHp]");
            }
        }
    }
}
