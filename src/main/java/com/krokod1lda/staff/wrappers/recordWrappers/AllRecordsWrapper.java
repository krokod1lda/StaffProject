package com.krokod1lda.staff.wrappers.recordWrappers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class AllRecordsWrapper {
    private List<RecordWithStaffNameListAndDateWrapper> list;
}
