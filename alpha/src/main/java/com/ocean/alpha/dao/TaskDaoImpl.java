package com.ocean.alpha.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.ocean.alpha.domain.AlphaSearchCriteria;
import com.ocean.alpha.domain.Task;
import com.ocean.alpha.helper.EmptyAndNullCheck;

@Service
public class TaskDaoImpl {

	@Autowired
	private NamedParameterJdbcTemplate nPJtemplate;

	private static final String NAME = "name";
	private static final String START_DATE = "startDate";
	private static final String DUE_DATE = "dueDate";
	private static final String SPRINT_ID = "sprintId";
	private static final String STATUS = "status";
	private static final String DESCRIPTION = "description";
	private static final String ASSIGNEE = "assignee";
	private static final String ASSIGNOR = "assignor";

	private static final String CREATE_TASK = "INSERT INTO `task`(`name`,`epic_id`,`sprint_id`,`status`,`description`,`assignee`,`assignor`,`due_date`,`start_date`,`created_by`,`date_created`,`modified_by`,`date_modified`) VALUES (:name, :epicId, :sprintId, :status, :description, :assignee, :assignor, :dueDate, :startDate, :createdBy, :dateCreated, :modifiedBy, :dateModified)";
	private static final String CREATE_CHILD_TASK = "INSERT INTO `child_task` (`name`,`parent_task_id`,`status_id`,`created_by`,`date_created`) VALUES (:name,:parentTaskId,:status_id,:createdBy,:dateCreated)";
	private static final String UPDATE_TASK = "UPDATE `task` SET `name` = :name,`epic_id` = :epicId,`sprint_id` = :sprintId,`status` = :status,`description` = :description,`assignee` = :assignee,`assignor` = :assignor,`due_date` = :dueDate,`start_date` = :startDate,`modified_by` = :modifiedBy,`date_modified` = :dateModified WHERE `task_id` = :taskId";
	private static final String GET_TASKS = "SELECT `task_id`,`name`,`epic_id`,`sprint_id`,`status`,`description`,`assignee`,`assignor`,`due_date`,`start_date`,`created_by`,`date_created`,`modified_by`,`date_modified` FROM `task` WHERE project_id = :projectId";

	public void createTask(Task task) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		if (EmptyAndNullCheck.isEmpty(task.getParentTaskId())) {
			params.addValue(NAME, task.getName());
			params.addValue("epicId", EmptyAndNullCheck.isEmpty(task.getEpicId()) ? null : task.getEpicId());
			params.addValue(SPRINT_ID, EmptyAndNullCheck.isEmpty(task.getSprintId()) ? null : task.getSprintId());
			params.addValue(STATUS, task.getStatus());
			params.addValue(DESCRIPTION,
					EmptyAndNullCheck.isEmpty(task.getDescription()) ? null : task.getDescription());
			params.addValue(ASSIGNEE, EmptyAndNullCheck.isEmpty(task.getAssignee()) ? null : task.getAssignee());
			params.addValue(ASSIGNOR, task.getAssignor());
			params.addValue(DUE_DATE, EmptyAndNullCheck.isEmpty(task.getDueDate()) ? null : task.getDueDate());
			params.addValue(START_DATE, EmptyAndNullCheck.isEmpty(task.getStartDate()) ? null : task.getStartDate());
			utility(task, params);
			nPJtemplate.update(CREATE_TASK, params);
		} else {
			params.addValue(NAME, task.getName());
			params.addValue("parentTaskId", task.getParentTaskId());
			params.addValue(STATUS, task.getStatus());
			params.addValue("createdBy", task.getCreatedBy());
			params.addValue("dateCreated", LocalDateTime.now());
			nPJtemplate.update(CREATE_CHILD_TASK, params);
		}
	}

	public void updateTask(Task task) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue(NAME, EmptyAndNullCheck.isEmpty(task.getName()) ? null : task.getName());
		params.addValue(STATUS, EmptyAndNullCheck.isEmpty(task.getStatus()) ? null : task.getStatus());
		params.addValue(DESCRIPTION, EmptyAndNullCheck.isEmpty(task.getDescription()) ? null : task.getDescription());
		params.addValue(ASSIGNEE, EmptyAndNullCheck.isEmpty(task.getAssignee()) ? null : task.getAssignee());
		params.addValue(ASSIGNOR, EmptyAndNullCheck.isEmpty(task.getAssignor()) ? null : task.getAssignor());
		params.addValue("modifiedBy", EmptyAndNullCheck.isEmpty(task.getModifiedBy()) ? null : task.getModifiedBy());
		params.addValue("dateModified",
				EmptyAndNullCheck.isEmpty(task.getDateModified()) ? null : task.getDateModified());
		if (EmptyAndNullCheck.isEmpty(task.getParentTaskId())) {
			isParentTask(task, params);
		}
		nPJtemplate.update(UPDATE_TASK, params);
	}

	public List<Task> getTasks(AlphaSearchCriteria criteria) {
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("projectId", criteria.getProjectId());
		return nPJtemplate.query(GET_TASKS, params, new ResultSetExtractor<List<Task>>() {

			@Override
			public List<Task> extractData(ResultSet rs) throws SQLException {
				List<Task> tasks = new ArrayList<>();
				while (rs.next()) {
					Task task = new Task();
					task.setName(rs.getString(NAME));
					task.setEpicId(rs.getInt("epic_id"));
					task.setSprintId(rs.getInt("sprint_id"));
					task.setStatusId((rs.getInt("status_id")));
					task.setDescription(rs.getString(DESCRIPTION));
					task.setAssignee(rs.getString(ASSIGNEE));
					task.setAssignor(rs.getString(ASSIGNOR));
					task.setDueDate(rs.getDate("due_date"));
					task.setStartDate(rs.getDate("start_date"));
					task.setCreatedBy(rs.getString("created_by"));
					task.setDateCreated(rs.getDate("date_created"));
					task.setModifiedBy(rs.getString("modified_by"));
					task.setDateModified(rs.getDate("date_modified"));
					tasks.add(task);
				}
				return tasks;
			}
		});
	}

	public void utility(Task task, MapSqlParameterSource params) {
		params.addValue("createdBy", task.getCreatedBy());
		params.addValue("dateCreated", task.getDateCreated());
		params.addValue("modifiedBy", EmptyAndNullCheck.isEmpty(task.getCreatedBy()) ? null : task.getCreatedBy());
		params.addValue("dateModified",
				EmptyAndNullCheck.isEmpty(task.getDateCreated()) ? null : task.getDateCreated());
	}

	public void isParentTask(Task task, MapSqlParameterSource params) {
		params.addValue("epicId", EmptyAndNullCheck.isEmpty(task.getEpicId()) ? null : task.getEpicId());
		params.addValue(SPRINT_ID, EmptyAndNullCheck.isEmpty(task.getSprintId()) ? null : task.getSprintId());
		params.addValue(DUE_DATE, EmptyAndNullCheck.isEmpty(task.getDueDate()) ? null : task.getDueDate());
		params.addValue(START_DATE, EmptyAndNullCheck.isEmpty(task.getStartDate()) ? null : task.getStartDate());
	}
}
