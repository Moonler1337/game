package com.example.demo.inmemory;

import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class PlayerStateStore {
    private static final int DEFAULT_MAX_HP = 100;

    private final PlayerStateRepository repository;

    public PlayerStateStore(PlayerStateRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public PlayerState getOrCreate(String id) {
        return getOrCreate(id, id, DEFAULT_MAX_HP);
    }

    @Transactional
    public PlayerState getOrCreate(String id, String name, int maxHp) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Player id must not be blank");
        }
        if (maxHp <= 0) {
            throw new IllegalArgumentException("maxHp must be > 0");
        }
        String safeName = (name == null || name.isBlank()) ? id : name;
        return repository.findById(id)
                .map(this::toState)
                .orElseGet(() -> toState(repository.save(new PlayerStateEntity(id, safeName, maxHp, maxHp))));
    }

    @Transactional
    public PlayerState applyDamage(String id, int value) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Player id must not be blank");
        }
        if (value < 0) {
            throw new IllegalArgumentException("Damage must be >= 0");
        }
        PlayerStateEntity state = repository.findById(id)
                .orElseGet(() -> new PlayerStateEntity(id, id, DEFAULT_MAX_HP, DEFAULT_MAX_HP));
        int nextHp = Math.max(0, state.getHp() - value);
        state.setHp(nextHp);
        return toState(repository.save(state));
    }

    @Transactional
    public PlayerState applyHeal(String id, int value) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Player id must not be blank");
        }
        if (value < 0) {
            throw new IllegalArgumentException("Heal must be >= 0");
        }
        PlayerStateEntity state = repository.findById(id)
                .orElseGet(() -> new PlayerStateEntity(id, id, DEFAULT_MAX_HP, DEFAULT_MAX_HP));
        int nextHp = Math.min(state.getMaxHp(), state.getHp() + value);
        state.setHp(nextHp);
        return toState(repository.save(state));
    }

    @Transactional(readOnly = true)
    public Map<String, PlayerState> snapshot() {
        return repository.findAll()
                .stream()
                .collect(Collectors.toUnmodifiableMap(PlayerStateEntity::getId, this::toState));
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

    private PlayerState toState(PlayerStateEntity entity) {
        return new PlayerState(entity.getName(), entity.getMaxHp(), entity.getHp());
    }
}
