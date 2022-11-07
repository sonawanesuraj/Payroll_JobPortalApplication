package com.app.serviceInterface;

import java.util.List;

import com.app.dto.IJobListDto;
import com.app.dto.JobDto;

public interface JobInterface {

	public List<IJobListDto> getJobById(Long id);

	public JobDto addJob(JobDto jobDto);

	public JobDto updateJob(JobDto jobDto, Long id);

	public void deleteJob(Long id);

}
