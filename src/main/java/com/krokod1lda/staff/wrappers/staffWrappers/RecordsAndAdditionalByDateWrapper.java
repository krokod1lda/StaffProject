package com.krokod1lda.staff.wrappers.staffWrappers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class RecordsAndAdditionalByDateWrapper {
    private Date date;
    private List<RecordForStaffTableViewWrapper> records;
    private float prizes;
    private float sales;
    private float fines;
    private float result;
}