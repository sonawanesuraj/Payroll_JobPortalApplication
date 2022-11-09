package com.app.repository;

import java.util.List;

import com.app.dto.Email;
import com.app.dto.IListUserJobDto;
import com.app.entities.UserJobEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJobRepository extends JpaRepository<UserJobEntity, Long> {

	Page<IListUserJobDto> findByOrderByIdDesc(Pageable paging, Class<IListUserJobDto> class1);

	// @Query(value = "select u.id,u.job_id ,u.user_id from user_job u where
	// u.user_id And u.job_id =?", nativeQuery = true)
	UserJobEntity findByUserIdAndJobId(@Param(value = "user_id") Long id, @Param("job_id") Long jobId);

	@Query(value = "select u.id as  user_id,u.email ,uu.created_by from users u inner join job uu on u.id=uu.created_by", nativeQuery = true)
	List<Email> findAllUserEmail();

}
