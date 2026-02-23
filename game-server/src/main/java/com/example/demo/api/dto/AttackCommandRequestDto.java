package com.example.demo.api.dto;

public record AttackCommandRequestDto(Long battleId, String actorId, String targetId) {
}
