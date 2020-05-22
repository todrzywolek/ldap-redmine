package pl.edu.agh.ldap.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {

    private DateTimeUtils() {
    }

    public static LocalDate convertDate(String date) {
        DateTimeFormatter parser = DateTimeFormatter.ofPattern("[yyyy/MM/dd][dd/MM/yyyy][dd.MM.yyyy][yyyy-MM-dd]");
        return LocalDate.parse(date, parser);
    }

    public static String getDateTimeString(LocalDateTime dateTime) {
        return dateTime.toLocalDate().toString() + "_" + getTime(dateTime);

    }

    public static String getTime(LocalDateTime dateTime) {
        return "" + addZeroIfLowerThanTen(dateTime.getHour()) + addZeroIfLowerThanTen(dateTime.getMinute()) + addZeroIfLowerThanTen(dateTime.getSecond());
    }

    private static String addZeroIfLowerThanTen(int number) {
        return number < 10 ? "0" + number : String.valueOf(number);
    }
}
