package com.krokod1lda.staff.entityAttributes;

public enum StaffAttributes {
    TITLE("title"),
    ADDING_STAFF("Добавление сотрудника"),
    STAFF("staff"), STAFF_RUS("Сотрудник"),
    STAFFS("staffs"), STAFFS_RUS("Сотрудники"),
    STAFF_BY_ID("staffById");
    private final String value;
    StaffAttributes(String value) {this.value = value;}
    public String getValue() {return value;}
}
