package com.csse.healthSphere.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateUtil {

    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    private static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";
    private static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * Formats a LocalDate to a string using the default format (yyyy-MM-dd).
     *
     * @param date the LocalDate to format
     * @return the formatted date as a string
     */
    public static String formatDate(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT));
    }

    /**
     * Formats a LocalDateTime to a string using the default format (yyyy-MM-dd HH:mm:ss).
     *
     * @param dateTime the LocalDateTime to format
     * @return the formatted date and time as a string
     */
    public static String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern(DEFAULT_DATETIME_FORMAT));
    }

    /**
     * Parses a string to a LocalDate using the default format (yyyy-MM-dd).
     *
     * @param dateStr the string to parse
     * @return the parsed LocalDate
     * @throws DateTimeParseException if the string cannot be parsed
     */
    public static LocalDate parseDate(String dateStr) throws DateTimeParseException {
        return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT));
    }

    /**
     * Parses a string to a LocalTime using the default format (yyyy-MM-dd).
     *
     * @param dateStr the string to parse
     * @return the parsed LocalDate
     * @throws DateTimeParseException if the string cannot be parsed
     */
    public static LocalTime parseTime(String dateStr) throws DateTimeParseException {
        return LocalTime.parse(dateStr, DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT));
    }

    /**
     * Parses a string to a LocalDateTime using the default format (yyyy-MM-dd HH:mm:ss).
     *
     * @param dateTimeStr the string to parse
     * @return the parsed LocalDateTime
     * @throws DateTimeParseException if the string cannot be parsed
     */
    public static LocalDateTime parseDateTime(String dateTimeStr) throws DateTimeParseException {
        return LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern(DEFAULT_DATETIME_FORMAT));
    }

    /**
     * Formats a LocalDate with a custom format.
     *
     * @param date    the LocalDate to format
     * @param pattern the custom format pattern
     * @return the formatted date as a string
     */
    public static String formatDateCustom(LocalDate date, String pattern) {
        return date.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * Parses a string to a LocalDate using a custom format.
     *
     * @param dateStr the string to parse
     * @param pattern the custom format pattern
     * @return the parsed LocalDate
     * @throws DateTimeParseException if the string cannot be parsed
     */
    public static LocalDate parseDateCustom(String dateStr, String pattern) throws DateTimeParseException {
        return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(pattern));
    }
}

