package com.krokod1lda.staff.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StaffRequestDto {
    @NotBlank
    @JsonProperty("name")
    private String name;
    @NotBlank
    @JsonProperty("surname")
    private String surname;
}
