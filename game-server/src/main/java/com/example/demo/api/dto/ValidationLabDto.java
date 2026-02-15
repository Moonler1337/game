package com.example.demo.api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ValidationLabDto(
        @NotNull(message = "{a.notnull}") String aNotNull,
        @NotEmpty(message = "{b.notempty}") String bNotEmpty,
        @NotBlank(message = "{c.notblank}") String cNotBlank,
        @Size(min = 1, message = "{d.size}") String dSizeMin1,
        @Pattern(regexp = "^[A-Za-z]+$", message = "{e.pattern}") String ePatternLetters,
        @NotNull(message = "{f.notnull}") Integer fIntegerNotNull,
        @Min(value = 1, message = "{g.min}") int gIntMin1) {}
