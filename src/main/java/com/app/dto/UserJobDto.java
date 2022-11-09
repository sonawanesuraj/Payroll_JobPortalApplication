package com.app.dto;

public class UserJobDto {

	private Long jobId;

	public Long getJobId() {
		return jobId;
	}

	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}

	public UserJobDto(Long jobId) {
		super();
		this.jobId = jobId;
	}

	public UserJobDto() {
		super();
	}

}
