package com.app.repository;

import java.util.List;

import com.app.dto.IJobListDto;
import com.app.entities.JobEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<JobEntity, Long> {

	JobEntity findByJobNameContainingIgnoreCase(String jobName);

	// List<IJobListDto> findById(Long id, Class<IJobListDto> class1);

	@Query(value = "select u.id as userId,u.user_name as Username,j.id as jobId,j.job_title as JobTitle,j.description as Description,j.created_by as RecruiterId from users u \r\n"
			+ "join job j on u.id=j.id and u.is_active ='true'\r\n"
			+ "AND (:userId = '' OR j.created_by IN (select unnest(cast(string_to_array(:userId, '') as bigint[]))))\r\n"
			+ "AND (:jobId = '' OR j.id IN (select unnest(cast(string_to_array(:jobId, '') as bigint[]))))order by j.id desc", nativeQuery = true)
	Page<IJobListDto> findByOrderByIdDesc(@Param("userId") String userId, @Param("jobId") String jobId,
			Pageable pageable, Class<IJobListDto> class1);

	Page<IJobListDto> findByJobNameIgnoreCaseContaining(String search, Pageable pageable, Class<IJobListDto> class1);

	JobEntity findByCreatedAt(Long userId);

	JobEntity findByIdIn(List<Long> jobId);

}
