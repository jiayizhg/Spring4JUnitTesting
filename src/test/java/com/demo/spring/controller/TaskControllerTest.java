package com.demo.spring.controller;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.demo.spring.model.Task;
import com.demo.spring.service.TaskService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TaskControllerTest {
	
	@Mock
	private TaskService taskService;
	
	private TaskController taskController;
	
	Task task = new Task();
	
	String jsonStr = "";
	
	@BeforeEach
	public void setUp() throws JsonProcessingException {
		
		task.setTaskId(50L);
		task.setTaskType(1002L);
		task.setTaskDescription("Provide a task");
		task.setTaskStatus("Pending");
		task.setTaskStatuscd("PE");
		task.setTaskComment("Adding a new task record");
		
		ObjectMapper objectMapper = new ObjectMapper();
		jsonStr = objectMapper.writeValueAsString(task);
		
		// Create a mock object of the taskService interface
		taskService = mock(TaskService.class);
		// Inject mocked taskService interface into taskService
		taskController = new TaskController(taskService);
	}
	
	@AfterEach
	public void cleanUp() {
		// Verify that after each response, no more interactions are made to the taskService
		verifyNoMoreInteractions(taskService);
	}
	
	/*---------- Testing POST Methods ---------- */
	@Test
	@DisplayName("Testing POST method")
	public void addNewTaskTest() throws Exception {
		
		when(taskService.addNewTask(any(Task.class))).thenReturn(task.getTaskId());
		
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(taskController).build();
		
		mockMvc.perform(post("/api/task").content(jsonStr)
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andExpect(content().string("50"));
			
		verify(taskService, times(1)).addNewTask(any(Task.class));
	}
	
	/*---------- Testing GET Methods ---------- */
	@Test
	@DisplayName("Testing GET method (Get all tasks)")
	public void getAllTasksTest() throws Exception {
		
		Task task1 = new Task();
		task1.setTaskId(55L);
		task1.setTaskType(1003L);
		task1.setTaskDescription("Retrieving a task");
		task1.setTaskStatus("In progress");
		task1.setTaskStatuscd("IP");
		task1.setTaskComment("Accessing all tasks");
		
		List<Task> expectedTasks = new ArrayList<>();
		expectedTasks.add(task);
		expectedTasks.add(task1);
		
		when(taskService.getAllTasks()).thenReturn(expectedTasks);
		
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(taskController).build();
		
		mockMvc.perform(get("/api/task"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$", hasSize(2)))
			.andExpect(jsonPath("$[0].taskId").value(50L))
			.andExpect(jsonPath("$[0].taskComment").value("Adding a new task record"))
			.andExpect(jsonPath("$[0].taskDescription").value("Provide a task"))
			.andExpect(jsonPath("$[0].taskType").value(1002L))
			.andExpect(jsonPath("$[0].taskStatus").value("Pending"))
			.andExpect(jsonPath("$[0].taskStatuscd").value("PE"))
			.andExpect(jsonPath("$[1].taskId").value(55L))
			.andExpect(jsonPath("$[1].taskComment").value("Accessing all tasks"))
			.andExpect(jsonPath("$[1].taskDescription").value("Retrieving a task"))
			.andExpect(jsonPath("$[1].taskType").value(1003L))
			.andExpect(jsonPath("$[1].taskStatus").value("In progress"))
			.andExpect(jsonPath("$[1].taskStatuscd").value("IP"));
		
		verify(taskService, times(1)).getAllTasks();
			
	}
	
	@Test
	@DisplayName("Testing GET method (Get by taskId)")
	public void getTaskRecordByIdTest() throws Exception {
		
		when (taskService.getTaskRecordById(anyLong())).thenReturn(task);
		
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(taskController).build();
		
		mockMvc.perform(get("/api/task/getById/{id}", 50).contentType(MediaType.APPLICATION_JSON_UTF8)) 
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$.taskId").value(50))
			.andExpect(jsonPath("$.taskComment").value("Adding a new task record"))
			.andExpect(jsonPath("$.taskDescription").value("Provide a task"))
			.andExpect(jsonPath("$.taskType").value(1002))
			.andExpect(jsonPath("$.taskStatus").value("Pending"))
			.andExpect(jsonPath("$.taskStatuscd").value("PE"));
			
		verify(taskService, times(1)).getTaskRecordById(anyLong());
	}
	
	@Test
	@DisplayName("Testing GET method (Get by testType)")
	public void getTaskRecordByTypeTest() throws Exception {
		
		when (taskService.getByType(anyLong())).thenReturn(task);
		
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(taskController).build();
		
		mockMvc.perform(get("/api/task/getByType/{id}", 1002).contentType(MediaType.APPLICATION_JSON_UTF8)) 
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$.taskId").value(50))
			.andExpect(jsonPath("$.taskComment").value("Adding a new task record"))
			.andExpect(jsonPath("$.taskDescription").value("Provide a task"))
			.andExpect(jsonPath("$.taskType").value(1002))
			.andExpect(jsonPath("$.taskStatus").value("Pending"))
			.andExpect(jsonPath("$.taskStatuscd").value("PE"))
			.andDo(print());
			
		verify(taskService, times(1)).getByType(anyLong());
	}
	
	@Test
	@DisplayName("Testing GET method (Get by taskStatus and taskId)")
	public void getTaskByStatusAndId() {
		
		when (taskService.getTaskByStatusAndId(anyString(), anyLong())).thenReturn(task);
		
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(taskController).build();
		
		try {
			mockMvc.perform(get("/api/task/statusAndId/{taskStatus}/{taskId}", "Pending", 1002).contentType(MediaType.APPLICATION_JSON_UTF8)) 
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.taskId").value(50))
				.andExpect(jsonPath("$.taskComment").value("Adding a new task record"))
				.andExpect(jsonPath("$.taskDescription").value("Provide a task"))
				.andExpect(jsonPath("$.taskType").value(1002))
				.andExpect(jsonPath("$.taskStatus").value("Pending"))
				.andExpect(jsonPath("$.taskStatuscd").value("PE"))
				.andDo(print());
		} catch (Exception e) {
			e.printStackTrace();
		}
			
		verify(taskService, times(1)).getTaskByStatusAndId(anyString(), anyLong());
		
	}
	
	/*---------- Testing PUT Methods ---------- */
	@Test
	@DisplayName("Testing PUT method")
	public void updateBookTest() throws Exception {
		
		long returnValue = 0;
		
		when(taskService.updateTaskById(anyLong(), any(Task.class))).thenReturn(returnValue);
		
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(taskController).build();
		
		mockMvc.perform(put("/api/task/{taskId}", 50).content(jsonStr)
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andExpect(content().string("0"));
				
			
		verify(taskService, times(1)).updateTaskById(anyLong(), any(Task.class));
	}

}
