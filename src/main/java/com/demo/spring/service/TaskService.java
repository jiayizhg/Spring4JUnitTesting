package com.demo.spring.service;

import java.util.List;

import com.demo.spring.model.Task;

public interface TaskService {
	
	Task getComment(long taskId);
	Task getStatus(long taskId);
	Task getStatuscd(long taskId);
	Task getDate(long taskId);
	Task getDescription(long taskId);
	
	void deleteTaskRecordById(long taskId);
	
	long updateTaskById(long taskId, Task task);
	
	Task getByType(long taskType);
	
	Task getTaskByStatusAndId(String taskStatus, long taskId);
	Task getTaskRecordById(long taskType);
	
	long addNewTask(Task task);
	
	List<Task> getAllTasks();
	
}
