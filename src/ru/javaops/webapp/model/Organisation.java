package ru.javaops.webapp.model;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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



    public class Position{
        private final LocalDate startDate;
        private final LocalDate endDate;
        private final String head;
        private final String description;

        public Position(int year, Month month, String head, String description) {
            this(of(year, month), LocalDate.now(), head, description);
        }

        public Position(LocalDate startDate, LocalDate endDate, String head, String description) {
            this(startDate, endDate, head, description);
        }

        public Position(LocalDate startDate, LocalDate endDate, String head, String description) {
            Objects.requireNonNull(startDate, "startDate can't be NULL");
            Objects.requireNonNull(head, "head can't be NULL");
            this.startDate = startDate;
            this.endDate = endDate;
            this.head = head;
            this.description = description;
        }

    }
}
