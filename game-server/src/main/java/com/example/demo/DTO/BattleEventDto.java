package com.example.demo.DTO;

import com.example.demo.DTO.Validation.ValidEventValue;
import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;


@ValidEventValue
public record BattleEventDto(@NotNull EventType type,
                @NotBlank @Pattern(regexp = ID_REGEX, message = "{actorid.regex}") String actorId,
                @JsonAlias("targetID") @NotBlank @Pattern(regexp = ID_REGEX,
                                message = "{targetid.regex}") String targetId,
                @Min(1) @Max(1000) int value,
                @NotBlank @Size(min = 5, max = 16, message = "{actorname.size}") String actorName,
                @Positive Integer weaponPrice, @NotBlank @Email String email) {
        public static final String ID_REGEX = "^[A-Za-z][a-zA-Z0-9]{2,9}$";
}
