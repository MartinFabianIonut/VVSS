package tasks.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.model.ArrayTaskList;
import tasks.model.Task;
import tasks.services.TasksService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TasksServiceIntegrationStep3Test {

    private TasksService service;

    private ArrayTaskList mockTaskList;

    private Task task1, task2, task3;

    private Date start, end;
    private SimpleDateFormat sdf;

    @BeforeEach
    void setUp() {
        sdf = Task.getDateFormat();

        try {
            // Create sample tasks (similar to previous test)
            start = sdf.parse("2024-05-08 00:00");
            end = sdf.parse("2024-05-08 23:59");
            task1 = new Task("Task 1", start, end, 1);
            task3 = new Task("Task 2", start, end, 1);
            start = sdf.parse("2024-05-09 00:00");
            end = sdf.parse("2024-05-09 23:59");
            task2 = new Task("Task 3", start, end, 2);
            task1.setActive(true);
            task2.setActive(true);
            task3.setActive(true);

        } catch (ParseException e) {
            fail(e.getMessage());
        }

        mockTaskList = new ArrayTaskList();
        service = new TasksService(mockTaskList);
        mockTaskList.add(task1);
        mockTaskList.add(task2);
        mockTaskList.add(task3);
    }

    @Test
    void test_FilterTasks_ExistentTaskBetweenDates() {
        try{
            start = sdf.parse("2024-05-07 21:00");
            end = sdf.parse("2024-05-08 22:00");
        } catch (ParseException e){
            fail(e.getMessage());
        }

        List<Task> filteredTasks = (List<Task>) service.filterTasks(start, end);
        assertEquals(2, filteredTasks.size());
        assertTrue(filteredTasks.contains(task1));
        assertTrue(filteredTasks.contains(task3));
    }

    @Test
    void test_FilterTasks_NonExistentTaskBetweenDates() {
        try{
            start = sdf.parse("2024-05-07 23:00");
            end = sdf.parse("2024-05-07 23:59");
        } catch (ParseException e){
            fail(e.getMessage());
        }

        List<Task> filteredTasks = (List<Task>) service.filterTasks(start, end);
        assertEquals(0, filteredTasks.size());
    }
}
