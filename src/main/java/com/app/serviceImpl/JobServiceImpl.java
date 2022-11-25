package com.app.serviceImpl;

import com.app.dto.IJobListDto;
import com.app.dto.JobDto;
import com.app.entities.JobEntity;
import com.app.exception.ResourceNotFoundException;
import com.app.repository.JobRepository;
import com.app.serviceInterface.JobInterface;
import com.app.util.Pagination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class JobServiceImpl implements JobInterface {

	@Autowired
	private JobRepository jobRepository;

	public Page<IJobListDto> getAllJobs(String userId, String jobId, String search, String pageNo, String pageSize) {

		Pageable pageable = new Pagination().getPagination(pageNo, pageSize);
		Page<IJobListDto> iJobListDto;
		if ((search == "") || (search == null) || (search.length() == 0)) {

			iJobListDto = jobRepository.findByOrderByIdDesc(userId, jobId, pageable, IJobListDto.class);
		} else {

			iJobListDto = jobRepository.findByJobNameIgnoreCaseContaining(search, pageable, IJobListDto.class);
		}
		return iJobListDto;

	}

	@Override
	public JobDto addJob(Long id, JobDto jobDto) {
		JobEntity jobEntity = new JobEntity();
		jobEntity.setJobName(jobDto.getJobName());
		jobEntity.setDescription(jobDto.getDescription());
		jobEntity.setCreatedBy(id);
		jobRepository.save(jobEntity);
		return jobDto;
	}

	@Override
	public JobDto updateJob(JobDto jobDto, Long id) {
		JobEntity jobEntity = this.jobRepository.findById(id).orElseThrow();
		jobEntity.setJobName(jobDto.getJobName());
		jobEntity.setDescription(jobDto.getDescription());
		jobRepository.save(jobEntity);
		return jobDto;

	}

	@Override
	public void deleteJob(Long id) {
		this.jobRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Resource Not Found for this Id "));
		jobRepository.deleteById(id);

	}

}
