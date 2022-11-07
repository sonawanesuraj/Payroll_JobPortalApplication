package com.app.repository;

import java.util.List;

import com.app.dto.IListPermissionDto;
import com.app.entities.PermissionEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<PermissionEntity, Long> {

	List<IListPermissionDto> findById(Long id, Class<IListPermissionDto> class1);

	Page<IListPermissionDto> findByOrderByIdAsc(Pageable paging, Class<IListPermissionDto> class1);

}
