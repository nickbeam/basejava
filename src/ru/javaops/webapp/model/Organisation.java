package ru.javaops.webapp.model;

import ru.javaops.webapp.util.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static ru.javaops.webapp.util.DateUtil.NOW;
import static ru.javaops.webapp.util.DateUtil.of;

@XmlAccessorType(XmlAccessType.FIELD)
public class Organisation implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private String url;
    private List<Position> positions;

    public Organisation() {
    }

    public Organisation(String name, String url, Position... positions){
        this(name, url, Arrays.asList(positions));
    }

    public Organisation(String name, String url, List<Position> positions){
        Objects.requireNonNull(name, "name can't be NULL");
        this.name = name;
        this.url = url == null ? "" : url;
        this.positions = positions;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public List<Position> getPositions() {
        return positions;
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

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Position implements Serializable {
        private static final long serialVersionUID = 1L;

        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate startDate;
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate endDate;
        private String head;
        private String description;

        public Position() {
        }

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
            this.description = description == null ? "" : description;
        }

        public LocalDate getStartDate() {
            return startDate;
        }

        public LocalDate getEndDate() {
            return endDate;
        }

        public String getHead() {
            return head;
        }

        public String getDescription() {
            return description;
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
