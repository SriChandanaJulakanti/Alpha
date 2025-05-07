package com.ocean.alpha.domain;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class Task {

	private int id;
	private String name;
	private int parentTaskId;
	private int epicId;
	private int sprintId;
	private Status status;
	private int statusId;
	private List<Task> childTask;
	private String description;
	private String assignee;
	private String assignor;
	private Date dueDate;
	private Date startDate;
	private String createdBy;
	private String modifiedBy;
	private Date dateCreated;
	private Date dateModified;
		
}
