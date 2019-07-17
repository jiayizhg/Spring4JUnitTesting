package com.demo.spring.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.demo.spring.model.Task;
import com.demo.spring.service.TaskService;

@CrossOrigin("*")


@RestController
public class TaskController {
	
	private TaskService taskService;
	
	@Autowired
	public TaskController(TaskService taskService) {
		this.taskService = taskService;
	}
	
	// Add a new task record
	@RequestMapping(value = "/api/task", method = RequestMethod.POST)
	public long addNewTask(@RequestBody Task task) {
		
		return taskService.addNewTask(task);
	}

	@RequestMapping(value = "/api/task", method = RequestMethod.GET)
	public List<Task> getAllTasks() {
		
		List<Task> tasks = new ArrayList<>();
		tasks = taskService.getAllTasks();
		
		return tasks;
	}
	
	@RequestMapping(value = "/api/task/getById/{taskId}", method = RequestMethod.GET)
	public Task getTaskRecordById(@PathVariable("taskId") long taskId) {
		
		Task task = taskService.getTaskRecordById(taskId);
		
		return task;
	}
	
	@RequestMapping(value = "/api/task/getByType/{taskType}", method = RequestMethod.GET)
	public Task getByType(@PathVariable("taskType") long taskType) {
		
		Task task = taskService.getByType(taskType);
		
		return task;
	}
	
	@RequestMapping(value = "/api/task/statusAndId/{taskStatus}/{taskId}", method = RequestMethod.GET)
	public Task getTaskByStatusAndId(@PathVariable long taskId, @PathVariable String taskStatus) {
		
		Task task = taskService.getTaskByStatusAndId(taskStatus, taskId);
		
		return task;
	}
	
	@RequestMapping(value = "/api/task/{taskId}", method = RequestMethod.PUT)
	public long updateTaskById(@PathVariable("taskId") long taskId, @RequestBody Task task) {
		
		return taskService.updateTaskById(taskId, task);
	}
	
	// Delete a task record by id
	@RequestMapping(value = "/api/task/{taskId}", method = RequestMethod.DELETE)
	public long deleteTaskRecordById(@PathVariable("taskId") long taskId) {
		taskService.deleteTaskRecordById(taskId);
		return 0;
	}
}
