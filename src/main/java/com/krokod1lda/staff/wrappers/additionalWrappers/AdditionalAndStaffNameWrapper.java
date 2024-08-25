package com.krokod1lda.staff.wrappers.additionalWrappers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AdditionalAndStaffNameWrapper {
    private long id;
    private String type;
    private int price;
    private String staffName;
}