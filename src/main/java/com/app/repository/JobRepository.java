package com.app.repository;

import java.util.List;

import com.app.dto.IJobListDto;
import com.app.entities.JobEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<JobEntity, Long> {

	JobEntity findByJobNameContainingIgnoreCase(String jobName);

	// List<IJobListDto> findById(Long id, Class<IJobListDto> class1);

	Page<IJobListDto> findByOrderByIdDesc(Pageable pageable, Class<IJobListDto> class1);

	Page<IJobListDto> findByJobNameIgnoreCaseContaining(String search, Pageable pageable, Class<IJobListDto> class1);

	JobEntity findByCreatedAt(Long userId);

	JobEntity findByIdIn(List<Long> jobId);

	List<IJobListDto> findById(Long id, Class<IJobListDto> class1);

}
