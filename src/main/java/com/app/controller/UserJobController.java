package com.app.controller;

import com.app.dto.ErrorResponseDto;
import com.app.dto.IListUserJobDto;
import com.app.dto.ListResponseDto;
import com.app.dto.SuccessResponseDto;
import com.app.dto.UserJobDto;
import com.app.serviceInterface.UserJobInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/userJob")
public class UserJobController {
	@Autowired
	private UserJobInterface userJobInterface;

	@PreAuthorize("hasRole('userJobAdd')")
	@PostMapping("/userJobAdd")
	ResponseEntity<?> candidateApplyJobs(@RequestBody UserJobDto userJobDto) throws Exception {
		try {
			userJobInterface.candidateApplyJob(userJobDto);

			return new ResponseEntity<>(new SuccessResponseDto("Job applied Successfully", "success", userJobDto),
					HttpStatus.ACCEPTED);

		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), "userJob not found "),
					HttpStatus.NOT_FOUND);
		}

	}

	@PreAuthorize("hasRole('getAllUserJob')")
	@GetMapping("/getAllUserJob")
	public ResponseEntity<?> candidateAppliedJobList(@RequestParam(defaultValue = "") String search,
			@RequestParam(defaultValue = "1") String pageNo, @RequestParam(defaultValue = "5") String pageSize) {
		Page<IListUserJobDto> userJob = userJobInterface.candidateAppliedJobList(search, pageNo, pageSize);

		if (userJob.getTotalElements() != 0) {
			return new ResponseEntity<>(new SuccessResponseDto("Job Applied CandidateList", "success",
					new ListResponseDto(userJob.getContent(), userJob.getTotalElements())), HttpStatus.OK);

		}

		return new ResponseEntity<>(new ErrorResponseDto("Data Not Found", "Data Not Found"), HttpStatus.NOT_FOUND);
	}

}
