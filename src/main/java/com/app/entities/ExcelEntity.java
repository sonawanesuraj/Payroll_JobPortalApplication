package com.app.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Excel_file")
public class ExcelEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "candidate")
	private String candidate;

	@Column(name = "recruiter")
	private String recruiter;

	@Column(name = "job")
	private String job;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCandidate() {
		return candidate;
	}

	public void setCandidate(String candidate) {
		this.candidate = candidate;
	}

	public String getRecruiter() {
		return recruiter;
	}

	public void setRecruiter(String recruiter) {
		this.recruiter = recruiter;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public ExcelEntity(Long id, String candidate, String recruiter, String job) {
		super();
		this.id = id;
		this.candidate = candidate;
		this.recruiter = recruiter;
		this.job = job;
	}

	public ExcelEntity() {
		super();
	}

}
