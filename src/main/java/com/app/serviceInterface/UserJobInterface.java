package com.app.serviceInterface;

import com.app.dto.IUserJobListDto;
import com.app.dto.UserJobDto;

import org.springframework.data.domain.Page;

public interface UserJobInterface {

	void applyJobs(Long id, UserJobDto userJobDto) throws Exception;

	Page<IUserJobListDto> getAllUserJob(String userId, String jobId, String pageNumber, String pageSize);

}
