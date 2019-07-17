package com.demo.spring.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*; // anyLong(). anyString(), ...
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.RepeatedTest;
import org.mockito.Mock;

import com.demo.spring.dao.TaskDAO;
import com.demo.spring.model.Task;

@TestMethodOrder(OrderAnnotation.class)
public class TaskServiceTest {

	private TaskServiceImpl taskServiceImpl;
	
	@Mock
	TaskDAO taskDAO;
	
	Task expectedTask = new Task();
	
	@BeforeEach
	public void setUp() {
		
		expectedTask.setTaskId(50L);
		expectedTask.setTaskType(1002L);
		expectedTask.setTaskDescription("Provide a task");
		expectedTask.setTaskStatus("Pending");
		expectedTask.setTaskStatuscd("PE");
		expectedTask.setTaskComment("Accessing a task");
		
		taskDAO = mock(TaskDAO.class);
		taskServiceImpl = new TaskServiceImpl();
		taskServiceImpl.setTaskDAO(taskDAO);
	}
	
	/*---------- Testing POST Methods ---------- */
	@Test
	@Order(1)
	@RepeatedTest(3)
	@DisplayName("Testing POST method")
	public void addNewTaskTest() {
		
		// Given the return value for method addNewTask()
		when(taskDAO.addNewTask(any(Task.class))).thenReturn(expectedTask.getTaskId());

		// Get the actual value from calling addNewTask() method
		long actualValue = taskServiceImpl.addNewTask(expectedTask);
		
		// Then the expected value should equal to the actual value
		assertEquals(expectedTask.getTaskId(), actualValue, "Adding a new task record should return its Taskid");

		// Verify that the save() method of the bookDAO interface is invoked exactly once.
		verify(taskDAO, times(1)).addNewTask(any(Task.class));
	}
	
	/*---------- Testing GET Methods ---------- */
	@Test
	@Order(2)
	@DisplayName("Testing GET method (Get all tasks)")
	public void getAllTasksTest() {
		
		// Casting expectedTask to a list
		List<Task> expectedTasks = new ArrayList<>();
		expectedTasks.add(expectedTask);
		
		when (taskDAO.getAllTasks()).thenReturn(expectedTasks);
		
		List<Task> actualTasks = taskServiceImpl.getAllTasks();
		
		assertEquals(expectedTasks, actualTasks, "Getting all tasks should return all tasks");
		
		verify(taskDAO, times(1)).getAllTasks();
	}
	
	@Test
	@Order(3)
	@DisplayName("Testing GET method (Get by taskId)")
	public void getTaskRecordByIdTest() {
		
		when (taskDAO.getTaskRecordById(anyLong())).thenReturn(expectedTask);
		
		Task actualTask = taskServiceImpl.getTaskRecordById(expectedTask.getTaskId());
		
		assertEquals(expectedTask, actualTask, "Getting a task by taskId should return the corresponding task");
		
		verify(taskDAO, times(1)).getTaskRecordById(anyLong());
	}
	
	@Test
	@Order(4)
	@DisplayName("Testing GET method (Get by testType)")
	public void getTaskRecordByTypeTest() {
		
		when (taskDAO.getByType(anyLong())).thenReturn(expectedTask);
		
		Task actualTask = taskServiceImpl.getByType(expectedTask.getTaskType());
		
		assertEquals(expectedTask, actualTask, "Getting a task by taskType should return the corresponding task");

		verify(taskDAO, times(1)).getByType(anyLong());
	}
	
	@Test
	@Order(5)
	@DisplayName("Testing GET method (Get by taskStatus and taskId)")
	public void getTaskByStatusAndId() {
		
		when (taskDAO.getTaskByStatusAndId(anyString(), anyLong())).thenReturn(expectedTask);
		
		Task actualTask = taskServiceImpl.getTaskByStatusAndId(expectedTask.getTaskStatus(), expectedTask.getTaskId());
				
		assertEquals(expectedTask, actualTask, "Getting a task by taskStatus and taskId should return the corresponding task");

		verify(taskDAO, times(1)).getTaskByStatusAndId(expectedTask.getTaskStatus(), expectedTask.getTaskId());
		
	}
	
	/*---------- Testing PUT Methods ---------- */
	@Test
	@DisplayName("Testing PUT method")
	public void updateBookTest() throws Exception {
		
		Task updatedTask = new Task();
		
		long expectedValue = 0;
		
		// Given the return value for method update()
		when(taskDAO.updateTaskById(anyLong(), any(Task.class))).thenReturn(expectedValue);
		
		// Get the actual value from calling update() method
		long actualValue = taskServiceImpl.updateTaskById(expectedTask.getTaskId(), updatedTask);
		
		// Then the expected book should equal to the actual value
		assertEquals(expectedValue, actualValue, "Updating task record should return 0");
		
		verify(taskDAO, times(1)).updateTaskById(anyLong(), any(Task.class));
	}

}
