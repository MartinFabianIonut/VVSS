package tasks.repo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tasks.model.ArrayTaskList;
import tasks.model.Task;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class ArrayTaskListTest {

    @Mock
    private Task mockTask1;

    @Mock
    private Task mockTask2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddTask_EmptyList() {
        ArrayTaskList tasks = new ArrayTaskList();
        tasks.add(mockTask1);
        List<Task> allTasks = tasks.getAll();

        assertNotNull(allTasks); // Ensure the returned list is not null
        assertEquals(1, allTasks.size()); // Verify the size matches the number of added tasks
        assertSame(mockTask1, allTasks.get(0));
        assertSame(mockTask1, tasks.getTask(0)); // Assert exact object reference
    }

    @Test
    void testAddTask_ListFull_Resizes() {
        ArrayTaskList tasks = new ArrayTaskList();
        for (int i = 0; i < 10; i++) {
            tasks.add(mockTask1);
        }

        tasks.add(mockTask2);

        List<Task> allTasks = tasks.getAll();
        assertNotNull(allTasks); // Ensure the returned list is not null
        assertEquals(11, tasks.size());
        boolean containsSubstring = tasks.toString().contains("currentCapacity=20");
        assertTrue(containsSubstring); // Verify the size matches the number of added tasks
        assertSame(mockTask2, tasks.getTask(10)); // Assert exact object reference at new index
    }

    @Test
    void testRemoveTask_ExistingTask() {
        ArrayTaskList tasks = new ArrayTaskList();
        tasks.add(mockTask1);
        tasks.add(mockTask2);

        boolean removed = tasks.remove(mockTask1);

        assertTrue(removed);
        assertEquals(1, tasks.size());
        assertSame(mockTask2, tasks.getTask(0)); // Removed element's position is filled by next task
    }

    @Test
    void testRemoveTask_NonexistentTask() {
        ArrayTaskList tasks = new ArrayTaskList();
        tasks.add(mockTask1);

        boolean removed = tasks.remove(mockTask2);

        assertFalse(removed);
        assertEquals(1, tasks.size());
    }
}
