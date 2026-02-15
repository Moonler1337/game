package com.example.demo.api.controller;

import com.example.demo.api.dto.BattleEventDto;
import com.example.demo.inmemory.BattleEventStore;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BattleController {

    private final BattleEventStore store;

    public BattleController(BattleEventStore store) {
        this.store = store;
    }

    @PostMapping("/events")
    public void push(@Valid @RequestBody BattleEventDto dto) {
        store.add(dto);
    }

    @GetMapping("/events")
    public List<BattleEventDto> all() {
        return store.snapshot();
    }
}
