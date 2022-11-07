package com.app.dto;

public class JobDto {

	private String jobName;

	private String description;

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public JobDto() {
		super();
	}

	public JobDto(String jobName, String description) {
		super();
		this.jobName = jobName;
		this.description = description;
	}

}
