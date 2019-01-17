package ru.javaops.webapp.model;

public enum SectionType {
    PERSONAL("Личные качества"){
        @Override
        public String toHtml0(String value) {
            return value;
        }
    },
    OBJECTIVE("Позиция"){
        @Override
        public String toHtml0(String value) {
            return value;
        }
    },
    ACHIEVEMENT("Достижения"){
        @Override
        public String toHtml0(String value) {
            return value;
        }
    },
    QUALIFICATIONS("Квалификация"){
        @Override
        public String toHtml0(String value) {
            return value;
        }
    },
    EXPERIENCE("Опыт работы"){
        @Override
        public String toHtml0(String value) {
            return value;
        }
    },
    EDUCATION("Образование"){
        @Override
        public String toHtml0(String value) {
            return value;
        }
    };

    private String title;

    SectionType(String title){
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String toHtml(String value) {
        return (value == null) ? "" : toHtml0(value);
    }

    public String toHtml(TextSection value) {
        return (value == null) ? "" : toHtml0(value.getText());
    }

    public String toHtml(OrganisationSection value) {
        return (value == null) ? "" : toHtml0(value.toString());
    }

    protected String toHtml0(String value) {
        return title + ": " + value;
    }
}
