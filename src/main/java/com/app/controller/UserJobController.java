package com.app.controller;

import com.app.dto.ErrorResponseDto;
import com.app.dto.IUserJobListDto;
import com.app.dto.SuccessResponseDto;
import com.app.dto.UserJobDto;
import com.app.exception.ResourceNotFoundException;
import com.app.serviceInterface.UserJobInterface;
import com.app.util.ApiUrls;
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
@RequestMapping(ApiUrls.USERJOB)
public class UserJobController {

	@Autowired
	private UserJobInterface userJobInterface;

	@PreAuthorize("hasRole('candidateJobAdd')")
	@PostMapping()
	public ResponseEntity<?> applyJobs(@RequestAttribute(GlobalFunction.CUSTUM_ATTRIBUTE_USER_ID) Long userId,
			@RequestBody UserJobDto userJobDto) throws Exception {
		try {
			this.userJobInterface.applyJobs(userId, userJobDto);

			return new ResponseEntity<>(new SuccessResponseDto("Job applied sucessfully", "Sucess", "Job Added"),
					HttpStatus.CREATED);
		} catch (ResourceNotFoundException e) {
			return new ResponseEntity<>(new ErrorResponseDto("You already applied for this position", "failed"),
					HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('userJobView')")
	@GetMapping(ApiUrls.GET_ALL)
	public ResponseEntity<?> getUserJob(@RequestParam(defaultValue = "") String userId,
			@RequestParam(defaultValue = "") String jobId, @RequestParam(defaultValue = "1") String pageNumber,
			@RequestParam(defaultValue = "10") String pageSize) {

		Page<IUserJobListDto> iUserJobListDto = this.userJobInterface.getAllUserJob(userId, jobId, pageNumber,
				pageSize);

		return new ResponseEntity<>(new SuccessResponseDto("User Job List", "Success", iUserJobListDto.getContent()),
				HttpStatus.OK);

	}

}
