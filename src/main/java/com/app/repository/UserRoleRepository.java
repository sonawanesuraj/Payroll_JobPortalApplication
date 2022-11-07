package com.app.repository;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import com.app.dto.IListUserRole;
import com.app.entities.UserRoleEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Long> {

	@Query(value = "select * from user_role as u where u.user_id=:userId AND u.role_id=:roleId", nativeQuery = true)
	UserRoleEntity findByUserandRoleId(@Param("userId") Long userId, @Param("roleId") Long roleId);

	@Transactional
	@Modifying(flushAutomatically = true, clearAutomatically = true)
	@Query(value = "UPDATE user_role u SET role_id=:id2 WHERE u.user_id=:id", nativeQuery = true)
	void updateUserRole(Long id, Long id2);

	List<IListUserRole> findById(Long id, Class<IListUserRole> class1);

	Page<IListUserRole> findByOrderByIdAsc(Pageable page, Class<IListUserRole> iListUserRole);

	@Query(value = "SELECT * FROM user_role u WHERE u.user_id=:userId", nativeQuery = true)
	List<UserRoleEntity> findByRole(@Param("userId") Long userId);

	UserRoleEntity findRoleIdByUserId(Long userId);

	UserRoleEntity findByroleId(Long role1);

	@Query(value = "SELECT * FROM user_role u WHERE u.user_id=:user_id", nativeQuery = true)
	ArrayList<UserRoleEntity> getRolesOfUser(@Param("user_id") Long userId);

	// Page<IListUserRole> findByUserRoleName(String UserRoleName, Pageable paging,
	// Class<IListUserRole> class1);

}
