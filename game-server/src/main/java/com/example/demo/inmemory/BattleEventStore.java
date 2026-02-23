package com.example.demo.inmemory;

import com.example.demo.api.dto.BattleEventDto;
import com.example.demo.api.dto.EventType;
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

    public Integer findLastDamageValueByActorId(String actorId) {
        if (actorId == null || actorId.isBlank()) {
            return null;
        }

        for (int i = events.size() - 1; i >= 0; i--) {
            BattleEventDto event = events.get(i);
            if ((event.type() == EventType.HIT || event.type() == EventType.CRIT_HIT)
                    && actorId.equalsIgnoreCase(event.actorId())) {
                return event.value();
            }
        }
        return null;
    }
}
