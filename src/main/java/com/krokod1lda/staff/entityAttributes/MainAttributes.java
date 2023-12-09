package com.krokod1lda.staff.entityAttributes;

public enum MainAttributes {
    MAIN_PAGE("Главная страница"), TITLE("title");
    private final String value;
    MainAttributes(String value) {this.value = value;}
    public String getValue() {return value;}
}
