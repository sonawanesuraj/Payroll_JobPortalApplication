package com.app.repository;

import com.app.dto.IRoleListDto;
import com.app.entities.RoleEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

	RoleEntity findByRoleNameContainingIgnoreCase(String roleName);

	Page<IRoleListDto> findByOrderByIdAsc(Pageable pageable, Class<IRoleListDto> class1);

	Page<IRoleListDto> findByRoleNameIgnoreCaseContaining(String roleName, Pageable pageable,
			Class<IRoleListDto> class1);

}
