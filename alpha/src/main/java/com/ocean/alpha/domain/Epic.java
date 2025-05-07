package com.ocean.alpha.domain;

import java.util.Date;

import lombok.Data;

@Data
public class Epic {

	private int id;
	private String name;
	private String description;
	private int statusId;
	private int projectId;
	private String assignee;
	private String assignor;
	private Date dueDate;
	private Date startDate;
	private String createdBy;
	private String modifedBy;
	private Date dateCreated;
	private Date dateModified;
	
}
