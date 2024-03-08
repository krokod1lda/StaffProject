package com.krokod1lda.staff;

import com.krokod1lda.staff.models.Record;
import com.krokod1lda.staff.models.Staff;

public class RecordAndHoursWrapper {
    private Record record;
    private Float hours;

    public Record getRecord() {
        return record;
    }

    public void setRecord(Record record) {
        this.record = record;
    }

    public Float getHours() {
        return hours;
    }

    public void setHours(Float hours) {
        this.hours = hours;
    }

    public RecordAndHoursWrapper(Record record, Float hours) {
        this.record = record;
        this.hours = hours;
    }

    public RecordAndHoursWrapper() {
    }
}
