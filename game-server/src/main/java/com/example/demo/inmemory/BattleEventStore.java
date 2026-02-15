package com.example.demo.inmemory;

import com.example.demo.DTO.BattleEventDto;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.springframework.stereotype.Component;

@Component
public class BattleEventStore {
    private final List<BattleEventDto> events = new CopyOnWriteArrayList<>();

    public void add(BattleEventDto e) {
        events.add(e);
    }

    public List<BattleEventDto> snapshot() {
        return List.copyOf(events);
    }
}
