package com.app.serviceInterface;

import com.app.dto.IListUserJobDto;
import com.app.dto.UserJobDto;

import org.springframework.data.domain.Page;

public interface UserJobInterface {

	public void candidateApplyJob(UserJobDto userJobDt) throws Exception;

	Page<IListUserJobDto> candidateAppliedJobList(String search, String pageNumber, String pageSize);

}
