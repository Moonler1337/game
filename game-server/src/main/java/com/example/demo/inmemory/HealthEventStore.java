package com.example.demo.inmemory;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.springframework.stereotype.Component;
import com.example.demo.api.dto.ValidationLabDto;

@Component
public class HealthEventStore {

    private final List<ValidationLabDto> events = new CopyOnWriteArrayList<>();

    public void add(ValidationLabDto e) {
        events.add(e);
    }

    public List<ValidationLabDto> snapshot() {
        return List.copyOf(events);
    }

}
