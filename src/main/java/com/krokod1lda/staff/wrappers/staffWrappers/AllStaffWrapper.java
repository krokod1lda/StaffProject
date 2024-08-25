package com.krokod1lda.staff.wrappers.staffWrappers;

import com.krokod1lda.staff.models.Staff;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AllStaffWrapper {
    private Iterable<Staff> staffList;
}