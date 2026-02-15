package com.example.demo.Controller;

import com.example.demo.DTO.ValidationLabDto;
import com.example.demo.inmemory.BattleEventStore;
import com.example.demo.inmemory.HealthEventStore;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    private final HealthEventStore store;

    public HealthController(HealthEventStore store) {
        this.store = store;
    }

    @PostMapping("/validation-lab")
    public void push(@Valid @RequestBody ValidationLabDto dto) {
        store.add(dto);
    }

    @GetMapping("/validation-lab")
    public List<ValidationLabDto> all() {
        return store.snapshot();
    }
}
