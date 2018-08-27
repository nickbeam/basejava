package ru.javaops.webapp.model;

import java.util.Objects;

public class TextSection {
    private String text;
    private SectionType type;
    private Resume resume;

    public TextSection(Resume resume, SectionType type, String text) {
        this.resume = resume;
        this.type = type;
        this.text = text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setType(SectionType type) {
        this.type = type;
    }

    public void setResume(Resume resume) {
        this.resume = resume;
    }

    public SectionType getType() {
        return type;
    }

    public Resume getResume() {
        return resume;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextSection that = (TextSection) o;
        return Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text);
    }
}
