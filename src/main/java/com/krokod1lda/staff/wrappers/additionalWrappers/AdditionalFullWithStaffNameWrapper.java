package com.krokod1lda.staff.wrappers.additionalWrappers;

import com.krokod1lda.staff.models.Additional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AdditionalFullWithStaffNameWrapper {
    private Additional additional;
    private String staffName;
}