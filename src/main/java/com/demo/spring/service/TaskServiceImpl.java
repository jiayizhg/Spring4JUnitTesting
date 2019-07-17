package com.demo.spring.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.spring.dao.TaskDAO;
import com.demo.spring.model.Task;

@Service
public class TaskServiceImpl implements TaskService {
	
	private TaskDAO taskDAO;
	
	@Autowired
	public void setTaskDAO(TaskDAO taskDAO) {
		this.taskDAO = taskDAO;
	}

	@Override
	@Transactional
	public Task getComment(long taskId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public Task getStatus(long taskId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public Task getStatuscd(long taskId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public Task getDate(long taskId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public Task getDescription(long taskId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public Task getByType(long taskType) {
		return taskDAO.getByType(taskType);
	}

	@Override
	@Transactional
	public Task getTaskRecordById(long taskId) {
		return taskDAO.getTaskRecordById(taskId);
	}

	@Override
	@Transactional
	public long addNewTask(Task task) {
		return taskDAO.addNewTask(task);
	}

	@Override
	@Transactional
	public List<Task> getAllTasks() {
		return taskDAO.getAllTasks();
	}

	@Override
	@Transactional
	public Task getTaskByStatusAndId(String taskStatus, long taskId) {
		return taskDAO.getTaskByStatusAndId(taskStatus, taskId);
	}

	@Override
	@Transactional
	public long updateTaskById(long taskId, Task task) {
		return taskDAO.updateTaskById(taskId, task);
	}

	@Override
	@Transactional
	public void deleteTaskRecordById(long taskId) {
		taskDAO.deleteTaskRecordById(taskId);
		
	}
	
}
