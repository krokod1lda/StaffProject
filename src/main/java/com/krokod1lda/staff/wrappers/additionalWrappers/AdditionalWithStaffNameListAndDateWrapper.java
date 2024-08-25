package com.krokod1lda.staff.wrappers.additionalWrappers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class AdditionalWithStaffNameListAndDateWrapper {

    private Date date;
    private List<AdditionalAndStaffNameWrapper> additionals;
}