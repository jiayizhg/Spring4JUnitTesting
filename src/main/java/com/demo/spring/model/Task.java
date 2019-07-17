package com.demo.spring.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "task")
public class Task {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long taskId;
	private long taskType;
	private String taskDescription;
	private String taskStatus;
	private String taskStatuscd;
	private String taskComment;
	private Timestamp taskCreatedt;
	
	public Task(long taskId, long taskType, String taskDescription, String taskStatus, String taskStatuscd,
			String taskComment, Timestamp taskCreatedt) {
		super();
		this.taskId = taskId;
		this.taskType = taskType;
		this.taskDescription = taskDescription;
		this.taskStatus = taskStatus;
		this.taskStatuscd = taskStatuscd;
		this.taskComment = taskComment;
		this.taskCreatedt = taskCreatedt;
	}
	
	public Task() {
		
	}
   
	public long getTaskId() {
		return taskId;
	}
	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}
	public long getTaskType() {
		return taskType;
	}
	public void setTaskType(long taskType) {
		this.taskType = taskType;
	}
	public String getTaskDescription() {
		return taskDescription;
	}
	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}
	public String getTaskStatus() {
		return taskStatus;
	}
	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}
	public String getTaskStatuscd() {
		return taskStatuscd;
	}
	public void setTaskStatuscd(String taskStatuscd) {
		this.taskStatuscd = taskStatuscd;
	}
	public String getTaskComment() {
		return taskComment;
	}
	public void setTaskComment(String taskComment) {
		this.taskComment = taskComment;
	}
	public Timestamp getTaskCreatedt() {
		return taskCreatedt;
	}
	public void setTaskCreatedt(Timestamp taskCreatedt) {
		this.taskCreatedt = taskCreatedt;
	}

   
}