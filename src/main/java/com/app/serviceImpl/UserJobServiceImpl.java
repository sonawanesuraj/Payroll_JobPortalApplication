package com.app.serviceImpl;

import com.app.dto.EmailMsg;
import com.app.dto.IUserJobListDto;
import com.app.dto.UserJobDto;
import com.app.entities.JobEntity;
import com.app.entities.UserEntity;
import com.app.entities.UserJobEntity;
import com.app.exception.ResourceNotFoundException;
import com.app.repository.JobRepository;
import com.app.repository.UserJobRepository;
import com.app.repository.UserRepository;
import com.app.serviceInterface.EmailInterface;
import com.app.serviceInterface.UserJobInterface;
import com.app.util.Pagination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserJobServiceImpl implements UserJobInterface {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JobRepository jobRepository;

	@Autowired
	private UserJobRepository userJobRepository;

	@Autowired
	private EmailInterface emailInterface;

	@Override
	public void applyJobs(Long id, UserJobDto userJobDto) throws Exception {

		UserEntity userEntity = this.userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Enter valid user id"));

		JobEntity job = this.jobRepository.findById(userJobDto.getJobId())
				.orElseThrow(() -> new ResourceNotFoundException("Enter job id not found"));

		UserJobEntity userJob = this.userJobRepository.findByUserIdAndJobId(id, userJobDto.getJobId());

		if (userJob != null) {

			throw new ResourceNotFoundException("Already applied ");
		}

		UserJobEntity userJobs = new UserJobEntity();
		userJobs.setUser(userEntity);
		userJobs.setJob(job);

		this.userJobRepository.save(userJobs);

		UserEntity userEntity2 = this.userRepository.findById(id).orElseThrow();
		String email = userEntity2.getEmail();

		JobEntity jobEntity = this.jobRepository.findById(userJobDto.getJobId())
				.orElseThrow(() -> new ResourceNotFoundException("enter valid job id "));

		EmailMsg userEntity1 = userJobRepository.findJobPostedMail(userJobDto.getJobId());

		emailInterface.sendSimpleMessage(userEntity1.getEmail(), "Apply jobs", "Candidate Applied for job"
				+ "Job title    " + jobEntity.getJobName() + "Candidate Email " + userEntity2.getEmail());

		emailInterface.sendSimpleMessage(email, " Apply jobs", "Job applied sucessfully for " + jobEntity.getJobName());

	}

	@Override
	public Page<IUserJobListDto> getAllUserJob(String userId, String jobId, String pageNumber, String pageSize) {
		Page<IUserJobListDto> iUserJobListDto;
		Pageable pageable = new Pagination().getPagination(pageNumber, pageSize);

		iUserJobListDto = userJobRepository.findByOrderByIdDesc123(userId, jobId, pageable, IUserJobListDto.class);

		return iUserJobListDto;

	}

}
