package ru.javaops.webapp.model;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static ru.javaops.webapp.util.DateUtil.NOW;
import static ru.javaops.webapp.util.DateUtil.of;

public class Organisation {
    private final String name;
    private final String url;
    private final List<Position> positions;

    public Organisation(String name, String url, Position... positions){
        Objects.requireNonNull(name, "name can't be NULL");
        this.name = name;
        this.url = url;
        this.positions = Arrays.asList(positions);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organisation that = (Organisation) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(url, that.url) &&
                Objects.equals(positions, that.positions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, url, positions);
    }

    @Override
    public String toString() {
        return "Organisation{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", positions=" + positions +
                '}';
    }

    public static class Position{
        private final LocalDate startDate;
        private final LocalDate endDate;
        private final String head;
        private final String description;

        public Position(int startYear, Month startMonth, String head, String description) {
            this(of(startYear, startMonth), NOW, head, description);
        }

        public Position(int startYear, Month startMonth, int endYear, Month endMonth, String head, String description) {
            this(of(startYear, startMonth), of(endYear, endMonth), head, description);
        }

        public Position(LocalDate startDate, LocalDate endDate, String head, String description) {
            Objects.requireNonNull(startDate, "startDate can't be NULL");
            Objects.requireNonNull(head, "head can't be NULL");
            this.startDate = startDate;
            this.endDate = endDate;
            this.head = head;
            this.description = description;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Position position = (Position) o;
            return Objects.equals(startDate, position.startDate) &&
                    Objects.equals(endDate, position.endDate) &&
                    Objects.equals(head, position.head) &&
                    Objects.equals(description, position.description);
        }

        @Override
        public int hashCode() {
            return Objects.hash(startDate, endDate, head, description);
        }

        @Override
        public String toString() {
            return "Position{" +
                    "startDate=" + startDate +
                    ", endDate=" + endDate +
                    ", head='" + head + '\'' +
                    ", description='" + description + '\'' +
                    '}';
        }
    }
}
