package com.app.repository;

import java.util.List;

import com.app.dto.IListUserJobDto;
import com.app.entities.UserJobEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJobRepository extends JpaRepository<UserJobEntity, Long> {

	Page<IListUserJobDto> findByOrderByIdDesc(Pageable paging, Class<IListUserJobDto> class1);

	List<IListUserJobDto> findById(Long id, Class<IListUserJobDto> class1);

}
