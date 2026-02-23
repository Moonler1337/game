package com.example.demo.api.validation;

import com.example.demo.api.dto.BattleEventDto;
import com.example.demo.api.dto.EventType;
import com.example.demo.inmemory.BattleEventStore;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EventTypeValueValidator
        implements ConstraintValidator<ValidEventValue, BattleEventDto> {

    private final BattleEventStore store;

    public EventTypeValueValidator(BattleEventStore store) {
        this.store = store;
    }

    @Override
    public boolean isValid(BattleEventDto dto, ConstraintValidatorContext ctx) {
        if (dto == null) {
            return true;
        }

        EventType type = dto.type();
        int value = dto.value();
        String actorId = dto.actorId();
        String targetId = dto.targetId();

        if (type == null) {
            return true;
        }

        boolean healVsDamageOk = true;
        if (type == EventType.HEAL) {
            Integer lastDamage = store.findLastDamageValueByActorId(actorId);
            healVsDamageOk = lastDamage == null || (value * 2 < lastDamage);
        }

        boolean bannedWord = BannedWords.containsBanned(dto.actorName());
        boolean valueOk = switch (type) {
            case HIT -> value >= 1 && value <= 100;
            case CRIT_HIT -> value >= 2 && value <= 200;
            case HEAL -> value >= 1 && value <= 50;
            case DEAD, BLOCKED, FULL_HP -> value == 0;
        };

        boolean targetOk = switch (type) {
            case HIT, CRIT_HIT, DEAD, BLOCKED -> targetId != null && !targetId.isBlank();
            case HEAL -> true;
            case FULL_HP -> true;
        };

        boolean selfHit = (type == EventType.HIT || type == EventType.CRIT_HIT)
                && actorId != null
                && targetId != null
                && actorId.equalsIgnoreCase(targetId);

        boolean valid = valueOk && targetOk && !selfHit && !bannedWord && healVsDamageOk;
        if (valid) {
            return true;
        }

        ctx.disableDefaultConstraintViolation();
        if (!valueOk) {
            ctx.buildConstraintViolationWithTemplate("Value out of range - " + type)
                    .addPropertyNode("value").addConstraintViolation();
        }
        if (!targetOk) {
            ctx.buildConstraintViolationWithTemplate("TargetId is invalid for type " + type)
                    .addPropertyNode("targetId").addConstraintViolation();
        }
        if (selfHit) {
            ctx.buildConstraintViolationWithTemplate("U can't hit yourself! TargetId = actorId")
                    .addPropertyNode("targetId").addConstraintViolation();
        }
        if (bannedWord) {
            ctx.buildConstraintViolationWithTemplate("actorName contains banned word")
                    .addPropertyNode("actorName").addConstraintViolation();
        }
        if (!healVsDamageOk) {
            ctx.buildConstraintViolationWithTemplate(
                    "HEAL value must be less than half of last HIT/CRIT_HIT value for actorId")
                    .addPropertyNode("value").addConstraintViolation();
        }

        return false;
    }
}
