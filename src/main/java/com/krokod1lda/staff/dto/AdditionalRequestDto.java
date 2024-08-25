package com.krokod1lda.staff.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

import java.sql.Date;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AdditionalRequestDto {
    @NotNull
    @JsonProperty("staffId")
    private long staffId;
    @NotBlank
    @JsonProperty("type")
    private String type;
    @NotNull
    @JsonProperty("price")
    private int price;
    @NotNull
    @JsonProperty("date")
    private Date date;
    @NotBlank
    @JsonProperty("description")
    private String description;
}