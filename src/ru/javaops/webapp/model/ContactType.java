package ru.javaops.webapp.model;

public enum ContactType {
    PHONE("Тел."),
    MOBILE_PHONE("Мобильный тел."),
    EMAIL("E-mail"){
        @Override
        public String toHtml0(String value) {
            return getTitle() + ": " + toLink("mailto:" + value, value);
        }
    },
    TELEGRAM("Telegram"){
        @Override
        public String toHtml0(String value) {
            return getTitle() + ": " + toLink("tg://resolve?domain=" + value, value);
        }
    },
    SKYPE("Skype"){
        @Override
        public String toHtml0(String value) {
            return getTitle() + ": " + toLink("skype:" + value, value);
        }
    },
    LINKEDIN("LinkedIn"){
        @Override
        public String toHtml0(String value) {
            return getTitle() + ": " + toLink("https://linkedin.com/" + value, value);
        }
    },
    GITHUB("GitHub"){
        @Override
        public String toHtml0(String value) {
            return getTitle() + ": " + toLink("https://github.com/" + value, value);
        }
    },
    STACKOVERFLOW("Stackoverflow"){
        @Override
        public String toHtml0(String value) {
            return getTitle() + ": " + toLink("https://stackoverflow.com/" + value, value);
        }
    },
    HOME_PAGE("Домашняя страница"){
        @Override
        public String toHtml0(String value) {
            return getTitle() + ": " + toLink("https://" + value, value);
        }
    };

    private String title;

    ContactType(String title){
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    protected String toHtml0(String value) {
        return title + ": " + value;
    }

    public String toHtml(String value) {
        return (value == null) ? "" : toHtml0(value);
    }

    public String toLink(String href) {
        return toLink(href, title);
    }

    public static String toLink(String href, String title) {
        return "<a href='" + href + "'>" + title + "</a>";
    }
}
