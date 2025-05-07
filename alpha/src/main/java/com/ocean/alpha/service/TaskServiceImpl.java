package com.ocean.alpha.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ocean.alpha.dao.TaskDaoImpl;
import com.ocean.alpha.domain.AlphaSearchCriteria;
import com.ocean.alpha.domain.Task;
import com.ocean.alpha.exception.AlphaException;
import com.ocean.alpha.helper.EmptyAndNullCheck;

@Service
public class TaskServiceImpl {

	@Autowired
	TaskDaoImpl taskDaoImpl;

	public void createTask(Task task) throws AlphaException {
		validatetask(task);
		taskDaoImpl.createTask(task);
	}

	public void updateTask(Task task) throws AlphaException {
		taskDaoImpl.updateTask(task);
	}

	public List<Task> getTasks(AlphaSearchCriteria criteria) throws AlphaException {
		return taskDaoImpl.getTasks(criteria);
	}

	public void validatetask(Task task) throws AlphaException {

		if (EmptyAndNullCheck.isEmpty(task)) {
			throw new AlphaException("task is invalid");
		}
		if (EmptyAndNullCheck.isEmpty(task.getName())) {
			throw new AlphaException("Name is invalid");
		}
		if (EmptyAndNullCheck.isEmpty(task.getStatusId())) {
			throw new AlphaException("Status is invalid");
		}
		if (EmptyAndNullCheck.isEmpty(task.getCreatedBy())) {
			throw new AlphaException("CreatedBy is invalid");
		}
	}
}
