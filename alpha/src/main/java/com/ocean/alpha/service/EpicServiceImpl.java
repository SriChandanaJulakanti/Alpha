package com.ocean.alpha.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ocean.alpha.dao.EpicDaoImpl;
import com.ocean.alpha.domain.AlphaSearchCriteria;
import com.ocean.alpha.domain.Epic;
import com.ocean.alpha.exception.AlphaException;
import com.ocean.alpha.helper.EmptyAndNullCheck;

@Service
public class EpicServiceImpl {

	@Autowired
	EpicDaoImpl epicDaoImpl;

	public void createEpic(Epic epic) throws AlphaException {
		validateEpic(epic);
		epicDaoImpl.createEpic(epic);
	}

	public void updateEpic(Epic epic) throws AlphaException {
		epicDaoImpl.updateEpic(epic);
	}

	public List<Epic> getEpic(AlphaSearchCriteria criteria) throws AlphaException {
		return epicDaoImpl.getEpic(criteria);
	}

	public void validateEpic(Epic epic) throws AlphaException {
		if (EmptyAndNullCheck.isEmpty(epic)) {
			throw new AlphaException("Epic is invalid");
		}
		if (EmptyAndNullCheck.isEmpty(epic.getName())) {
			throw new AlphaException("Name is invalid");
		}
		if (EmptyAndNullCheck.isEmpty(epic.getStatusId())) {
			throw new AlphaException("Status is invalid");
		}
		if (EmptyAndNullCheck.isEmpty(epic.getCreatedBy())) {
			throw new AlphaException("CreatedBy is invalid");
		}
		if (EmptyAndNullCheck.isEmpty(epic.getProjectId())) {
			throw new AlphaException("ProjectId is invalid");
		}
	}

}
