package tasks.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import static org.junit.jupiter.api.Assertions.*;

class TaskTest {
    private Task task;
    private Date start;
    private Date end;
    private int interval;
    private final long oneSecond = 1000; // 1000 de milisecunde într-o secundă

    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    @Test
    void testTaskCreation_ValidTime() throws ParseException {
        String title = "Buy groceries";
        Date time = SDF.parse("2024-05-05 10:00");
        Task task = new Task(title, time);

        assertEquals(title, task.getTitle());
        assertEquals(time, task.getTime());
        assertEquals(time, task.getStartTime());
        assertEquals(time, task.getEndTime());
        assertEquals(0, task.getRepeatInterval());
    }

    @Test
    void testTaskCreation_TimeBelowBound_ThrowsException() {
        String title = "Water the plants";
        Date invalidTime = new Date(-1); // Negative timestamp

        assertThrows(IllegalArgumentException.class, () -> new Task(title, invalidTime));
    }

    @BeforeEach
    void setUp() {
        // Setăm timpul de start și end pentru test
        start = new Date(System.currentTimeMillis() + 1000 * 60 * 60); // Start peste 1 oră
        end = new Date(start.getTime() + 1000 * 60 * 60 * 2); // End peste 3 ore de la acum
        interval = 10 * 60; // Interval de 10 minute
        // Creăm un task activ repetitiv
        task = new Task("Test Task", start, end, interval);
        task.setActive(true);
    }

    @Test
    void shouldReturnNullWhenCurrentAfterEnd() {
        Date current = new Date(end.getTime() + oneSecond);
        assertNull(task.nextTimeAfter(current), "Should return null when current is after end.");
    }

    @Test
    void shouldReturnNullWhenCurrentEqualsEnd() {
        assertNull(task.nextTimeAfter(end), "Should return null when current is equal to end.");
    }

    @Test
    void shouldReturnStartWhenCurrentBeforeStart() {
        Date current = new Date(start.getTime() - oneSecond);
        assertEquals(start, task.nextTimeAfter(current), "Should return start when current is before start.");
    }

    @Test
    void shouldReturnNextTimeWhenCurrentEqualsStart() {
        Date nextTime = new Date(start.getTime() + interval * oneSecond);
        assertEquals(nextTime, task.nextTimeAfter(start), "Should return next time when current equals start.");
    }

    @Test
    void shouldReturnNextTimeWhenCurrentBetweenStartAndEnd() {
        Date current = new Date(start.getTime() + oneSecond);
        Date nextTime = new Date(start.getTime() + interval * oneSecond);
        assertEquals(nextTime, task.nextTimeAfter(current), "Should return next time when current is between start and end.");
    }

    @Test
    void shouldReturnNextTimeInLoopWhenCurrentEqualsTimeAfter() {
        Date current = new Date(start.getTime() + interval * oneSecond);
        Date nextTime = new Date(current.getTime() + interval * oneSecond);
        assertEquals(nextTime, task.nextTimeAfter(current), "Should return next time in the loop when current equals timeAfter.");
    }

    @Test
    void shouldReturnNextTimeInLoopWhenCurrentBetweenTimeBeforeAndTimeAfter() {
        // Setăm timpul curent între două timpi consecutivi din bucla de interval
        Date current = new Date(start.getTime() + interval * oneSecond - 500);
        Date expected = new Date(start.getTime() + interval * oneSecond);
        assertEquals(expected, task.nextTimeAfter(current), "Should return next time when current is between timeBefore and timeAfter in the loop.");
    }

    @Test
    void shouldReturnTimeWhenNonRepeatedBeforeTimeAndActive() {
        // Setăm task-ul ca nerepetitiv și activ
        task.setTime(start);
        task.setActive(true);
        Date current = new Date(start.getTime() - oneSecond);
        assertEquals(start, task.nextTimeAfter(current), "Should return time when non-repeated, before time, and active.");
    }

    @Test
    void shouldReturnNullWhenTaskIsInactive() {
        Date current = new Date(start.getTime() + oneSecond);
        task.setActive(false);
        assertNull(task.nextTimeAfter(current), "Should return null when task is inactive.");
    }
}
