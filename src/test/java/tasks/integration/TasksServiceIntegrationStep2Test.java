package tasks.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tasks.model.ArrayTaskList;
import tasks.model.Task;
import tasks.services.TasksService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TasksServiceIntegrationStep2Test {

    @Mock
    private ArrayTaskList mockTaskList;

    @Mock
    private Task mockTask1, mockTask2;

    private TasksService tasksService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        tasksService = new TasksService(mockTaskList);
    }

    @Test
    void test_getObservableList_withMockedTasks() {
        // Mock repository behavior (populated list with specific tasks)
        List<Task> mockTasks = Arrays.asList(mockTask1, mockTask2);
        when(mockTaskList.getAll()).thenReturn(mockTasks);


        assertEquals(2, tasksService.getObservableList().size());
    }

    @Test
    void test_getObservableList_emptyTaskList() {
        // Mock repository behavior (empty list)
        when(mockTaskList.getAll()).thenReturn(Arrays.asList());

        assertEquals(0, tasksService.getObservableList().size());
    }
}
