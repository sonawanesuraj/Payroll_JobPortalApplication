package com.app.serviceInterface;

import com.app.dto.IListUserJobDto;
import com.app.dto.UserJobDto;

import org.springframework.data.domain.Page;

public interface UserJobInterface {

//	void applyJobs(Long id, UserJobDto userJobDto) throws Exception;

	Page<IListUserJobDto> candidateAppliedJobList(String search, String pageNumber, String pageSize);

	Page<IListUserJobDto> getUsersAppliedJobList(Long jobId, String pageNumber, String pageSize);

	void candidateApplyJobs(Long id, UserJobDto userJobDto) throws Exception;

}
