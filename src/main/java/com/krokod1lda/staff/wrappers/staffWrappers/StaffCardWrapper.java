package com.krokod1lda.staff.wrappers.staffWrappers;

import com.krokod1lda.staff.models.Staff;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class StaffCardWrapper {
    private Staff staff;
    private List<RecordsAndAdditionalByDateWrapper> list;
}