package com.krokod1lda.staff.wrappers.recordWrappers;

import com.krokod1lda.staff.models.Record;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RecordFullWithStaffNameWrapper {
    private Record record;
    private String staffName;
}