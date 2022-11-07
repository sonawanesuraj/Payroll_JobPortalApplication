package com.app.repository;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import com.app.dto.IListRolePermission;
import com.app.dto.IPermissionIdList;
import com.app.entities.RolePermissionEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermissionEntity, Long> {
	@Query(value = "select * from role_permission as r where r.role_Id=:roleId AND r.permission_id=:permissionId", nativeQuery = true)
	RolePermissionEntity findByRolePermissionId(@Param("roleId") Long roleId, @Param("permissionId") Long permissionId);

	@Transactional
	@Modifying(flushAutomatically = true, clearAutomatically = true)
	@Query(value = "UPDATE role_permission r SET permission_id=:id2 WHERE r.role_id=:id", nativeQuery = true)
	void updateUserRole(Long id, Long id2);

	List<IListRolePermission> findById(Long id, Class<IListRolePermission> class1);

	Page<IListRolePermission> findByOrderByIdAsc(Pageable paging, Class<IListRolePermission> class1);

	List<IPermissionIdList> findPermissionByRoleIdIn(ArrayList<Long> roles, Class<IPermissionIdList> class1);

}
