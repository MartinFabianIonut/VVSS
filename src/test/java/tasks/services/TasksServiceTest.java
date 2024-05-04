package tasks.services;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tasks.model.ArrayTaskList;
import tasks.model.Task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TasksServiceTest {

    @Mock
    private ArrayTaskList mockTaskList;

    private TasksService tasksService;
    private SimpleDateFormat dateFormat;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        tasksService = new TasksService(mockTaskList);
        try {
            dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void test_get_tasks() {
        // Create example tasks
        Date task1Time = null;
        Date task2Time = null;
        try {
            task1Time = dateFormat.parse("2020-10-23 13:00");
            task2Time = dateFormat.parse("2020-10-23 14:00");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Task mockTask1 = new Task("t1", task1Time);
        Task mockTask2 = new Task("t2", task2Time);

        // Mock task list behavior
        when(mockTaskList.getAll()).thenReturn(List.of(mockTask1, mockTask2));

        ObservableList<Task> obs;
        obs = tasksService.getObservableList();

        assertEquals(2, obs.size());
        assertTrue(obs.get(0).getTitle().equals("t1"));
    }

    @Test
    void test_form_time_unit() {
        int time = 40;
        String expectedString = "40"; // Assuming formTimeUnit doesn't rely on mocked interactions

        // No mocking needed for this test if formTimeUnit is a simple formatting method
        String actualString = tasksService.formTimeUnit(time);
        assertEquals(expectedString, actualString);
    }
}