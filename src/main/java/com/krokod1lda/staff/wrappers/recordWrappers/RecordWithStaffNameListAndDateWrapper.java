package com.krokod1lda.staff.wrappers.recordWrappers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class RecordWithStaffNameListAndDateWrapper {
    private Date date;
    private List<RecordAndStaffNameWrapper> records;
}