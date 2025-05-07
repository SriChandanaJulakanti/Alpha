package com.ocean.alpha.domain;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class Sprint {
	
	private int id;
	private String summary;
	private List<Task> tasks;
	private List<Status> statuses;
	private Date startDate;
	private Date dueDate;
	private String createdBy;
	private Date dateCreated;
	private String modifiedBy;
	private String dateModified;
	
}
