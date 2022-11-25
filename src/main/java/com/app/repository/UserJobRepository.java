package com.app.repository;

import java.util.List;

import com.app.dto.EmailMsg;
import com.app.dto.IListUserJobDto;
import com.app.dto.IListUsersJobDto;
import com.app.dto.IUserJobListDto;
import com.app.entities.UserJobEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJobRepository extends JpaRepository<UserJobEntity, Long> {

	@Query(value = "select u.id as UserId,u.user_name as UserName,uj.id as UserJobId,j.job_title as JobTitle,j.description as Description,j.id as JobId from users u \r\n"
			+ "join user_job uj on uj.user_id=u.id and uj.is_active='true'\r\n"
			+ "join job j on uj.job_id=j.id and j.is_active='true'\r\n" + "where uj.is_active='true'\r\n"
			+ " AND (:userId = '' OR uj.user_id IN (select unnest(cast(string_to_array(:userId, '') as bigint[]))))\r\n"
			+ "AND (:jobId = '' OR  uj.job_id IN (select unnest(cast(string_to_array(:jobId,'') as bigint[])))) order by uj.id desc ", nativeQuery = true)
	Page<IUserJobListDto> findByOrderByIdDesc123(@Param("userId") String userId, @Param("jobId") String jobId,
			Pageable pageable, Class<IUserJobListDto> class1);

	UserJobEntity findByUserIdAndJobId(@Param(value = "user_id") Long id, @Param("job_id") Long jobId);

	Page<IUserJobListDto> findJobIdByUserIdOrderByIdDesc(Long userId, Pageable pageable, Class<IUserJobListDto> class1);

	@Query(value = "select j.id , j.created_by,u.email  from job j\r\n"
			+ "join users u on u.id=j.created_by where j.id=?", nativeQuery = true)
	EmailMsg findJobPostedMail(Long id);

	Page<IUserJobListDto> findByJobIdOrderByIdDesc(Long jobId, Pageable pageable, Class<IUserJobListDto> class1);

	@Query(value = "select uj.user_id,uj.job_id,j.job_title ,s.user_name as user_name,j.description from user_job uj  inner join job j on uj.job_id=j.id inner join users s on uj.user_id=s.id", nativeQuery = true)
	Page<IListUserJobDto> findByJobIdAndUserIdOrderByIdDesc(Long jobId, Pageable pageable,
			Class<IListUserJobDto> class1);

	@Query(value = "select u.id as userId,u.user_Name as UserName,j.id as JobId,j.job_title,uj.id as user_JobId from user_job uj inner join job j on uj.id =j.id inner join users u on u.id=uj.id", nativeQuery = true)
	Page<IUserJobListDto> findByOrderByIdDesc(Pageable pageable, Class<IUserJobListDto> class1);

	@Query(value = "select u.id as Id, u.user_name as UserName,j.job_title as JobTitle  from user_job uj \r\n"
			+ "join users u on u.id=uj.user_id and u.is_active= true\r\n"
			+ "join job j on j.id = uj.job_id and j.is_active =true and uj.is_active=true\r\n", nativeQuery = true)
	List<IListUsersJobDto> getUserAppliedJobList();

}
