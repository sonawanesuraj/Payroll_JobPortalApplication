package com.app.serviceInterface;

import com.app.dto.JobDto;

public interface JobInterface {

	public JobDto addJob(Long id, JobDto jobDto);

	public JobDto updateJob(JobDto jobDto, Long id);

	public void deleteJob(Long id);

}
