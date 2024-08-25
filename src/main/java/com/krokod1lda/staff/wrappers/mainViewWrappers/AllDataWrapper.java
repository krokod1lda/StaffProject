package com.krokod1lda.staff.wrappers.mainViewWrappers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class AllDataWrapper {
    private List<AllObjectsForOneDayWrapper> list;
}