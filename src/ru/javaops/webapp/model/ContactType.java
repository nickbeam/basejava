package ru.javaops.webapp.model;

public enum ContactType {
    PHONE("Тел."),
    MOBILE_PHONE("Мобильный тел."),
    EMAIL("E-mail"),
    TELEGRAM("Telegram"),
    SKYPE("Skype"),
    LINKEDIN("LinkedIn"),
    GITHUB("GitHub"),
    STACKOVERFLOW("Stackoverflow"),
    HOME_PAGE("Домашняя страница");

    private String title;

    ContactType(String title){
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
