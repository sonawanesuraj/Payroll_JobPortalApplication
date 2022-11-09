package com.app.controller;

import java.util.List;

import com.app.dto.ErrorResponseDto;
import com.app.dto.IJobListDto;
import com.app.dto.JobDto;
import com.app.dto.SuccessResponseDto;
import com.app.exception.ResourceNotFoundException;
import com.app.repository.JobRepository;
import com.app.serviceImpl.JobServiceImpl;
import com.app.serviceInterface.JobInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/job")
public class JobController {

	@Autowired
	private JobRepository jobRepository;

	@Autowired
	private JobInterface jobInterface;

	@Autowired
	private JobServiceImpl jobServiceImpl;

	// @PreAuthorize("hasRole('recruiterJobAdd')")
	@PostMapping("/recruiterJobAdd")
	public ResponseEntity<?> addJobs(@RequestBody JobDto jobDto) {

		try {
			jobDto = this.jobInterface.addJob(jobDto);
			return new ResponseEntity<>(new SuccessResponseDto("job Added Successfully", "job Added", "Data added"),
					HttpStatus.ACCEPTED);
		} catch (ResourceNotFoundException e) {

			return new ResponseEntity<>(new ErrorResponseDto("job already exist", "Please add new job"),
					HttpStatus.BAD_REQUEST);
		}

	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateJob(@RequestBody JobDto jobDto, @PathVariable long id) {
		try {
			jobDto = this.jobInterface.updateJob(jobDto, id);
			return new ResponseEntity<>(new SuccessResponseDto("Job updated sucessfully", "job updated !!", jobDto),
					HttpStatus.CREATED);
		} catch (ResourceNotFoundException e) {
			return new ResponseEntity<>(new ErrorResponseDto("job Id Not Found  ", "Something went wrong"),
					HttpStatus.BAD_REQUEST);
		}

	}

	@PreAuthorize("hasRole('JobDelete')")

	@DeleteMapping("/JobDelete/{id}")
	public ResponseEntity<?> deleteJob(@PathVariable(name = "id") Long id) throws ResourceNotFoundException {
		try {
			jobInterface.deleteJob(id);

			return ResponseEntity.ok(new SuccessResponseDto("Deleted Succesfully", "Deleted", id));
		} catch (Exception e) {

			return ResponseEntity.ok(new ErrorResponseDto(e.getMessage(), "Enter Valid Id"));
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getJobById(@PathVariable("id") Long id) {
		try {
			List<IJobListDto> jobDto = this.jobInterface.getJobById(id);
			return new ResponseEntity<>(new SuccessResponseDto("Job Get Successfully", "Success", jobDto),
					HttpStatus.OK);
		} catch (ResourceNotFoundException e) {
			return new ResponseEntity<>(new ErrorResponseDto("job Not found", "Please Enter Correct Id "),
					HttpStatus.NOT_FOUND);
		}

	}

	@PreAuthorize("hasRole('getAllJob')")
	@GetMapping("/getAllJob")
	public ResponseEntity<?> getAllJob(@RequestParam(defaultValue = "") String search,
			@RequestParam(defaultValue = "1") String pageNo, @RequestParam(defaultValue = "5") String pageSize) {
		Page<IJobListDto> job = jobServiceImpl.getAllJobs(search, pageNo, pageSize);

		if (job.getTotalElements() != 0) {
			return new ResponseEntity<>(new SuccessResponseDto("All job get Successfully", "Success", job.getContent()),
					HttpStatus.OK);

		}

		return new ResponseEntity<>(new ErrorResponseDto("Data Not Found", "Data Not Found"), HttpStatus.NOT_FOUND);
	}
}
