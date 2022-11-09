package com.app.controller;

import com.app.dto.ErrorResponseDto;
import com.app.dto.IListUserJobDto;
import com.app.dto.ListResponseDto;
import com.app.dto.SuccessResponseDto;
import com.app.dto.UserJobDto;
import com.app.exception.ResourceNotFoundException;
import com.app.serviceInterface.UserJobInterface;
import com.app.util.GlobalFunction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/userJob")
public class UserJobController {
	@Autowired
	private UserJobInterface userJobInterface;

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

	@PreAuthorize("hasRole('userJobAdd')")
	@PostMapping("/userJobAdd")
	public ResponseEntity<?> applyJobs(@RequestAttribute(GlobalFunction.CUSTUM_ATTRIBUTE_USER_ID) Long userId,
			@RequestBody UserJobDto userJobDto) throws Exception {
		try {
			System.out.println("token user id" + userId);
			this.userJobInterface.candidateApplyJobs(userId, userJobDto);

			return new ResponseEntity<>(new SuccessResponseDto("Job applied sucessfully", "Sucess"),
					HttpStatus.CREATED);
		} catch (ResourceNotFoundException e) {
			return new ResponseEntity<>(new ErrorResponseDto("You already applied for this position", "failed"),
					HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/userId")
	public ResponseEntity<?> getAppliedJobList(@RequestAttribute(GlobalFunction.CUSTUM_ATTRIBUTE_USER_ID) Long userId,
			@RequestParam(defaultValue = "1") String pageNo, @RequestParam(defaultValue = "5") String pageSize) {

		Page<IListUserJobDto> page = this.userJobInterface.getUsersAppliedJobList(userId, pageNo, pageSize);

		return new ResponseEntity<>(new SuccessResponseDto("Job list", "Successfull", page.getContent()),
				HttpStatus.OK);

	}

}
