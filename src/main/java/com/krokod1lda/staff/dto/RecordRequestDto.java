package com.krokod1lda.staff.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RecordRequestDto {
    @NotNull
    @JsonProperty("staffId")
    private long staffId;
    @NotNull
    @JsonProperty("date")
    private Date date;
    @NotBlank
    @JsonProperty("startHours")
    private String startHours;
    @NotBlank
    @JsonProperty("endHours")
    private String endHours;
    @NotNull
    @JsonProperty("workingRate")
    private float workingRate;
}
