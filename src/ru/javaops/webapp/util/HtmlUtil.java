package ru.javaops.webapp.util;

import ru.javaops.webapp.model.Organisation;

public class HtmlUtil {

    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static String formatDates(Organisation.Position position) {
        return DateUtil.format(position.getStartDate()) + " - " + DateUtil.format(position.getEndDate());
    }

}
