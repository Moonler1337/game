package com.example.demo.api.validation;

import com.example.demo.api.dto.BattleEventDto;
import com.example.demo.api.dto.EventType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EventTypeValueValidator implements ConstraintValidator<ValidEventValue, BattleEventDto> {

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

        boolean bannedWord = BannedWords.containsBanned(dto.actorName());
        boolean ok =
                switch (type) {
                    case DAMAGE -> value >= 1 && value <= 999;
                    case HEAL -> value >= 1 && value <= 998;
                    case DEFENSE -> value >= 1 && value <= 15;
                };
        boolean selfHit =
                type == EventType.DAMAGE
                        && actorId != null
                        && targetId != null
                        && actorId.equalsIgnoreCase(targetId);

        boolean valid = ok && !selfHit && !bannedWord;
        if (valid) {
            return true;
        }

        ctx.disableDefaultConstraintViolation();
        if (!ok) {
            ctx.buildConstraintViolationWithTemplate("Value out of range - " + type)
                    .addPropertyNode("value")
                    .addConstraintViolation();
        }
        if (selfHit) {
            ctx.buildConstraintViolationWithTemplate("U can't hit yourself! TargetId = actorId - ")
                    .addPropertyNode("targetId")
                    .addConstraintViolation();
        }
        if (bannedWord) {
            ctx.buildConstraintViolationWithTemplate("actorName contains banned word")
                    .addPropertyNode("actorName")
                    .addConstraintViolation();
        }
        return valid;
    }
}
