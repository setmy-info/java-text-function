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
     * Input should be in Estonian time format "HH.mm.ss".
     * (http://www.eki.ee/books/ekk09/index.php?id=74&p=2&p1=10).
     */
    public static final Function<String, Object> TIME_TRANSFORM_FUNCTION = (string) -> {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH.mm.ss");
        return LocalTime.parse(string, formatter);
    };

    /**
     * Input should be in Estonian date format "dd.MM.yyyy".
     * (http://www.eki.ee/books/ekk09/index.php?id=74&p=2&p1=10).
     */
    public static final Function<String, Object> DATE_TRANSFORM_FUNCTION = (string) -> {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return LocalDate.parse(string, formatter);
    };

    /**
     * Input should be in Estonian date format "dd.MM.yyyy HH.mm.ss".
     * (http://www.eki.ee/books/ekk09/index.php?id=74&p=2&p1=10).
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
