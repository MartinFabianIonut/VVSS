package tasks.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import tasks.model.ArrayTaskList;

import java.time.LocalDate;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class DateServiceTest {

    private DateService dateService;

    @BeforeEach
    void setUp() {
        dateService = new DateService(new TasksService(new ArrayTaskList()));
    }
    @Test
    @DisplayName("Valid time and date merge")
    void testValidDateAndTimeMergeECP() {
        // Arrange
        Date date = new Date();
        String time = "5:15";

        // Act
        Date mergedDate = dateService.getDateMergedWithTime(time, date);

        // Assert
        assertNotNull(mergedDate, "Merged date should not be null");
    }

    @ParameterizedTest
    @ValueSource(strings = { "2:-1", "-1:45", "25:-1"})
    @DisplayName("Invalid time throws IllegalArgumentException")
    void testValidDateAndInvalidTimeMergeECP(String time) {
        // Arrange
        Date date = new Date();

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> dateService.getDateMergedWithTime(time, date),
            "Expected an IllegalArgumentException for invalid time");
    }

    @Test
    @DisplayName("Valid LocalDate from Date")
    void testValidLocalDateFromDateECP() {
        // Arrange
        Date date = new Date();

        // Act
        LocalDate localDate = DateService.getLocalDateValueFromDate(date);

        // Assert
        assertNotNull(localDate, "LocalDate should not be null");
    }

    @ParameterizedTest
    @CsvSource({
        "2023-03-30, 2023-03-30",
        "2023-12-31, 2023-12-31"
    })
    @DisplayName("Convert LocalDate to Date successfully")
    void convertLocalDateToDate_SuccessECP(LocalDate input, LocalDate expected) {
        // Act
        Date date = dateService.getDateValueFromLocalDate(input);

        // Assert
        assertEquals(expected, DateService.getLocalDateValueFromDate(date),
            "Conversion to Date should match expected LocalDate");
    }

    @ParameterizedTest
    @CsvSource({
        "-1:00, false",
        "00:00, true",
        "00:01, true",
        "23:59, true",
        "24:00, false",
        "25:00, false"
    })
    @DisplayName("Merge Date and Time with boundary values")
    void mergeDateAndTime_BoundaryValuesBVA(String time, boolean isValid) {
        // Arrange
        Date noTimeDate = new Date();

        // Act & Assert
        if (isValid) {
            assertDoesNotThrow(() -> dateService.getDateMergedWithTime(time, noTimeDate), "Time should be valid");
        } else {
            assertThrows(IllegalArgumentException.class, () ->
                dateService.getDateMergedWithTime(time, noTimeDate), "Time should be invalid");
        }
    }

    @ParameterizedTest
    @CsvSource({
        "00:00, 00:00", // Start of the day
        "23:59, 23:59", // End of the day
        "12:00, 12:00", // Noon
        "11:59, 11:59", // One minute before noon
        "12:01, 12:01", // One minute after noon
    })
    @DisplayName("Valid input times for getTimeOfTheDayFromDate")
    void validInputTimes_GetTimeOfTheDayFromDateBVA(String input, String expected) {
        // Arrange
        Date date = dateService.getDateMergedWithTime(input, new Date());

        // Act
        String result = dateService.getTimeOfTheDayFromDate(date);

        // Assert
        assertEquals(expected, result, "The extracted time should match the input time");
    }

    @ParameterizedTest
    @CsvSource({
        "2020-02-29, true", // Ultima zi într-un an bisect
        "2021-02-28, true", // Ultima zi într-un an nebisect
        "2020-03-01, true", // Prima zi după un an bisect
        "2021-03-01, true"  // Prima zi după un an nebisect
    })
    @DisplayName("Validate LocalDate conversion at leap year boundaries")
    void testLocalDateConversionAtLeapYearBoundariesBVA(LocalDate input, boolean isValid) {
        // Act
        Date result = dateService.getDateValueFromLocalDate(input);
        LocalDate backConvertedResult = DateService.getLocalDateValueFromDate(result);

        // Assert
        if (isValid) {
            assertEquals(input, backConvertedResult, "The converted LocalDate should match the original input" +
                " at leap year boundaries");
        } else {
            assertNotEquals(input, backConvertedResult, "The conversion should not match for invalid dates at" +
                " leap year boundaries");
        }
    }

    @ParameterizedTest
    @CsvSource({
        "23, 59, true", // Ultimul minut valid al zilei
        "0, 0, true",   // Primul minut valid al zilei
        "24, 0, false", // Ora după ultima oră validă
        "23, 60, false",// Minutul după ultimul minut valid
        "-1, 0, false", // Ora înainte de prima oră validă
        "0, -1, false"  // Minutul înainte de primul minut valid
    })
    @DisplayName("Boundary value testing for hour and minute in date and time merging")
    void testHourAndMinuteBoundariesInDateAndTimeMergingBVA(int hour, int minute, boolean isValid) {
        // Arrange
        String time = String.format("%d:%d", hour, minute);
        Date noTimeDate = new Date();

        // Act & Assert
        if (isValid) {
            assertDoesNotThrow(() -> dateService.getDateMergedWithTime(time, noTimeDate),
                "Hour and minute within valid range should not throw");
        } else {
            assertThrows(IllegalArgumentException.class, () -> dateService.getDateMergedWithTime(time, noTimeDate),
                "Hour or minute outside valid range should throw IllegalArgumentException");
        }
    }

}