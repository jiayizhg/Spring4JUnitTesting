package com.demo.spring.dao;

import java.util.List;

import com.demo.spring.model.Task;

public interface TaskDAO {
	
	Task getComment(long taskId);
	Task getStatus(long taskId);
	Task getStatuscd(long taskId);
	Task getDate(long taskId);
	Task getDescription(long taskId);
	
	void deleteTaskRecordById(long taskId);
	
	long updateTaskById(long taskId, Task task);
	
	Task getTaskByStatusAndId(String taskStatus, long taskId);
	
	Task getByType(long taskType);
	
	Task getTaskRecordById(long taskId);
	
	long addNewTask(Task task);
	
	List<Task> getAllTasks();
	
}
