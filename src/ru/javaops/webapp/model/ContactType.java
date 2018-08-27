package ru.javaops.webapp.model;

public enum ContactType {
    MOBILEPHONE("Тел."),
    EMAIL("E-mail"),
    SKYPE("Skype"),
    LINKEDIN("LinkedIn"),
    GITHUB("GitHub"),
    STACKOVERFLOW("Stackoverflow"),
    HOMEPAGE("Домашняя страница");

    private String title;

    ContactType(String title){
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
