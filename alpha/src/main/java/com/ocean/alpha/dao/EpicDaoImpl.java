package com.ocean.alpha.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ocean.alpha.domain.AlphaSearchCriteria;
import com.ocean.alpha.domain.Epic;
import com.ocean.alpha.helper.EmptyAndNullCheck;

@Repository
public class EpicDaoImpl {

	@Autowired
	private NamedParameterJdbcTemplate npJdbctemplate;

	private static final String INSERT_EPIC_QUERY = "INSERT INTO `epic_ocean`(`name`,`parent_id`,`summary`,`start_time`,`due_time`,`status`,`created_by`) VALUES (:name, :parent_id, :summary, :start_time, :due_time, :status, :created_by)";
	private static final String UPDATE_EPIC_QUERY = "UPDATE `epic_ocean`  SET `name` = :name,`summary` = :summary,`start_time` = :start_time,`due_time` = :due_time,`status` = :status,`modified_by` = :modified_by WHERE `id` = :id";
	private static final String GET_EPIC_QUERY = "SELECT `name`,`status_id`,`description`,`assignee`,`assignor`,`due_date`,`start_date`,`created_by`,`date_created`,`modified_by`,`date_modified` FROM `epic` WHERE `project_id`=:project_id;";

	private static final String NAME = "name";
	private static final String PROJECT_ID = "projectId";
	private static final String START_DATE = "startDate";
	private static final String DUE_DATE = "dueDate";
	private static final String STATUS_ID = "statusId";
	private static final String DESCRIPTION = "description";
	private static final String ASSIGNEE = "assignee";
	private static final String ASSIGNOR = "assignor";
	private static final String DATE_MODIFIED = "dateModified";
	private static final String MODIFIED_BY = "modifiedBy";
	private static final String CREATED_BY = "createdBy";
	private static final String DATE_CREATED = "dateCreated";

	public void createEpic(Epic epic) {

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue(NAME, epic.getName());
		params.addValue(DESCRIPTION, EmptyAndNullCheck.isEmpty(epic.getDescription()) ? null : epic.getDescription());
		params.addValue(STATUS_ID, epic.getStatusId());
		params.addValue(ASSIGNOR, EmptyAndNullCheck.isEmpty(epic.getAssignee()) ? null : epic.getAssignee());
		params.addValue(ASSIGNEE, epic.getAssignee());
		params.addValue(DUE_DATE, EmptyAndNullCheck.isEmpty(epic.getDueDate()) ? null : epic.getDueDate());
		params.addValue(START_DATE, EmptyAndNullCheck.isEmpty(epic.getStartDate()) ? null : epic.getStartDate());
		params.addValue(CREATED_BY, epic.getCreatedBy());
		params.addValue(DATE_CREATED, LocalDateTime.now());
		params.addValue(MODIFIED_BY, EmptyAndNullCheck.isEmpty(epic.getCreatedBy()) ? null : epic.getCreatedBy());
		params.addValue(DATE_MODIFIED, EmptyAndNullCheck.isEmpty(epic.getDateCreated()) ? null : epic.getDateCreated());
		params.addValue(PROJECT_ID, epic.getProjectId());

		npJdbctemplate.update(INSERT_EPIC_QUERY, params);
	}

	public void updateEpic(Epic epic) {

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue(NAME, EmptyAndNullCheck.isEmpty(epic.getName()) ? null : epic.getName());
		params.addValue(STATUS_ID, EmptyAndNullCheck.isEmpty(epic.getStatusId()) ? null : epic.getStatusId());
		params.addValue(DESCRIPTION, EmptyAndNullCheck.isEmpty(epic.getDescription()) ? null : epic.getDescription());
		params.addValue(ASSIGNEE, EmptyAndNullCheck.isEmpty(epic.getAssignee()) ? null : epic.getAssignee());
		params.addValue(ASSIGNOR, EmptyAndNullCheck.isEmpty(epic.getAssignee()) ? null : epic.getAssignee());
		params.addValue(DUE_DATE, EmptyAndNullCheck.isEmpty(epic.getDueDate()) ? null : epic.getDueDate());
		params.addValue(START_DATE, EmptyAndNullCheck.isEmpty(epic.getStartDate()) ? null : epic.getStartDate());

		npJdbctemplate.update(UPDATE_EPIC_QUERY, params);

	}

	public List<Epic> getEpic(AlphaSearchCriteria criteria) {

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue(PROJECT_ID, criteria.getProjectId());
		return npJdbctemplate.query(GET_EPIC_QUERY, params, new ResultSetExtractor<List<Epic>>() {

			@Override
			public List<Epic> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<Epic> epics = new ArrayList<>();
				while (rs.next()) {
					Epic epic = new Epic();
					epic.setName(rs.getString(NAME));
					epic.setStatusId((rs.getInt("status_id")));
					epic.setDescription(rs.getString(DESCRIPTION));
					epic.setAssignee(rs.getString(ASSIGNEE));
					epic.setAssignor(rs.getString(ASSIGNOR));
					epic.setDueDate(rs.getDate("due_date"));
					epic.setStartDate(rs.getDate("start_date"));
					epic.setCreatedBy(rs.getString("created_by"));
					epic.setDateCreated(rs.getDate("date_created"));
					epic.setModifedBy(rs.getString("modified_by"));
					epic.setDateModified(rs.getDate("date_modified"));
					epics.add(epic);
				}
				return epics;
			}
		});
	}
}
