package com.krokod1lda.staff.wrappers.additionalWrappers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class AllAdditionalsWrapper {
    private List<AdditionalWithStaffNameListAndDateWrapper> list;
}