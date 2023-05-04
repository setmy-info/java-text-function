package info.setmy.textfunctions.register;

import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.function.Function;

import static lombok.AccessLevel.PRIVATE;

@SuppressWarnings({"checkstyle:HideUtilityClassConstructor"})
@NoArgsConstructor(access = PRIVATE)
public final class TypeFunctions {

    /**
     * Parses a time string in Estonian format "HH.mm.ss".
     *
     * @param timeString the time string to parse
     * @return a {@link LocalTime} object representing the parsed time
     * @throws IllegalArgumentException if the input string is not in the correct format
     * or if the parsed time is invalid
     * @see <a href="http://www.eki.ee/books/ekk09/index.php?id=74&p=2&p1=10">Estonian time format</a>
     */
    public static final Function<String, Object> TIME_TRANSFORM_FUNCTION = (string) -> {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH.mm.ss");
        return LocalTime.parse(string, formatter);
    };

    /**
     * Parses a date string in Estonian format "dd.MM.yyyy".
     *
     * @param dateString the date string to parse
     * @return a {@link LocalDate} object representing the parsed date
     * @throws IllegalArgumentException if the input string is not in the correct format
     * or if the parsed date is invalid
     * @see <a href="http://www.eki.ee/books/ekk09/index.php?id=74&p=2&p1=10">Estonian date format</a>
     */
    public static final Function<String, Object> DATE_TRANSFORM_FUNCTION = (string) -> {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return LocalDate.parse(string, formatter);
    };

    /**
     * Parses a datetime string in Estonian format "dd.MM.yyyy HH.mm.ss".
     *
     * @param datetimeString the datetime string to parse
     * @return a {@link LocalDateTime} object representing the parsed datetime
     * @throws IllegalArgumentException if the input string is not in the correct format
     * or if the parsed datetime is invalid
     * @see <a href="http://www.eki.ee/books/ekk09/index.php?id=74&p=2&p1=10">Estonian datetime format</a>
     */
    public static final Function<String, Object> DATE_TIME_TRANSFORM_FUNCTION = (string) -> {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH.mm.ss");
        return LocalDateTime.parse(string, formatter);
    };

    /**
     * Comma separated list.
     */
    public static final Function<String, Object> LIST_TRANSFORM_FUNCTION = (string) -> {
        // TODO : into concrete detected types, so list can contain elements in any type.
        final String[] elements = string.split(",");
        return Arrays.asList(elements);
    };


    /**
     * Comma separated list.
     */
    public static final Function<String, Object> SET_TRANSFORM_FUNCTION = (string) -> {
        // TODO : into concrete detected types, so set can contain elements in any type.
        final String[] elements = string.split(",");
        return new HashSet<>(Arrays.asList(elements));
    };
}
