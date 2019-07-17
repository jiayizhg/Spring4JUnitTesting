package com.demo.spring.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.demo.spring.model.Task;

@Repository
public class TaskDAOImpl implements TaskDAO {
	
	@PersistenceContext
    private EntityManager entityManager;

	private SessionFactory sessionFactory;

	public TaskDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public long addNewTask(Task task) {
		sessionFactory.getCurrentSession().save(task);
		return task.getTaskId();
	}

	@Override
	public Task getComment(long taskId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Task getStatus(long taskId) {
		
		return null;
	}

	@Override
	public Task getStatuscd(long taskId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Task getDate(long taskId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Task getDescription(long taskId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Task getByType(long taskType) {
		Query query= sessionFactory.getCurrentSession().
				createQuery("from task where taskType=:taskType");
		
		query.setParameter("taskType", taskType);
		
		Task task = (Task) query.uniqueResult();

		return task;
	}

	@Override
	public Task getTaskRecordById(long taskId) {
		
		Task task = sessionFactory.getCurrentSession().get(Task.class, taskId);
		
		return task;
	}

	

	@Override
	public List<Task> getAllTasks() {
		
		List <Task> list = sessionFactory.getCurrentSession().createQuery("from task").list();
		
		return list;
	}

	@Override
	public Task getTaskByStatusAndId(String taskStatus, long taskId) {
		
		Query query= sessionFactory.getCurrentSession().
				createQuery("from task where taskStatus=:taskStatus and taskId=:taskId");
		
		query.setParameter("taskStatus", taskStatus);
		query.setParameter("taskId", taskId);
		
		Task task = (Task) query.uniqueResult();

		return task;
	}

	@Override
	public long updateTaskById(long taskId, Task task) {
		
		Session session = sessionFactory.getCurrentSession();
		
		Task oldTask = session.byId(Task.class).load(taskId);
		
		if (oldTask == null)
			return -1;
		
		oldTask.setTaskComment(task.getTaskComment());
		oldTask.setTaskDescription(task.getTaskDescription());
		oldTask.setTaskStatus(task.getTaskStatus());
		oldTask.setTaskStatuscd(task.getTaskStatuscd());
		oldTask.setTaskType(task.getTaskType());
		//oldTask.setTaskCreatedt(newTask.getTaskCreatedt());;
		
		session.flush();
		return 0;
	}

	@Override
	public void deleteTaskRecordById(long taskId) {
		Session session = sessionFactory.getCurrentSession();
		Task task = session.byId(Task.class).load(taskId);
		session.delete(task);
		
	}

}
