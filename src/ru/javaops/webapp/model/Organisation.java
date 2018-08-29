package ru.javaops.webapp.model;

import java.time.LocalDate;
import java.util.Objects;

public class Organisation {
    private final String name;
    private final String url;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final String head;
    private final String description;

    public Organisation(String name, String url, LocalDate startDate, LocalDate endDate, String head, String description) {
        Objects.requireNonNull(name, "name can't be NULL");
        Objects.requireNonNull(startDate, "startDate can't be NULL");
        Objects.requireNonNull(head, "head can't be NULL");
        this.name = name;
        this.url = url;
        this.startDate = startDate;
        this.endDate = endDate;
        this.head = head;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organisation that = (Organisation) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(url, that.url) &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(endDate, that.endDate) &&
                Objects.equals(head, that.head) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, url, startDate, endDate, head, description);
    }

    @Override
    public String toString() {
        return "Organisation{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", head='" + head + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
