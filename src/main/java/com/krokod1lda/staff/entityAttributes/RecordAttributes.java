package com.krokod1lda.staff.entityAttributes;
public enum RecordAttributes {
    RECORDS_AND_TIME("recordsAndTime"),
    ADDIND_RECORD("Добавление записи"),
    RECORDS_BY_DATES_LIST("recordsByDatesList");
    private final String value;
    RecordAttributes(String value) {this.value = value;}
    public String getValue() {return value;}
}
