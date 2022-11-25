package com.app.repository;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import com.app.dto.IListUserRole;
import com.app.dto.IListUsersRoleDto;
import com.app.dto.IRoleListDto;
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

	@Query(value = "select u.id as UserId,u.user_name as UserName,ur.id as UserRoleId,r.role_name as RoleName,r.description as Description,r.id as RoleId from users u \r\n"
			+ "join user_role ur on ur.user_id=u.id and ur.is_active='true'\r\n"
			+ "join roles r on ur.role_id=r.id and r.is_active='true'" + "where ur.is_active='true'\r\n"
			+ "AND (:userId = '' OR ur.user_id IN (select unnest(cast(string_to_array(:userId, '') as bigint[]))))\r\n"
			+ "AND (:roleId ='' OR  ur.role_id IN (select unnest(cast(string_to_array(:roleId,'') as bigint[])))) order by ur.id desc", nativeQuery = true)

	Page<IListUserRole> findByOrderByIdDesc(@Param("userId") String userId, @Param("roleId") String roleId,
			Pageable page, Class<IListUserRole> iListUserRole);

	@Query(value = "SELECT * FROM user_role u WHERE u.user_id=:userId", nativeQuery = true)
	List<UserRoleEntity> findByRole(@Param("userId") Long userId);

	UserRoleEntity findRoleIdByUserId(Long userId);

	UserRoleEntity findByroleId(Long role1);

	@Query(value = "SELECT * FROM user_role u WHERE u.user_id=:user_id", nativeQuery = true)
	ArrayList<UserRoleEntity> getRolesOfUser(@Param("user_id") Long userId);

	ArrayList<UserRoleEntity> findUserIdByRoleId(Long userId);

	@Query(value = "select ur.user_id as userId,ur.role_id,r.role_name as roleName from user_role ur inner join roles r on ur.role_id=r.id  where ur.user_id=?", nativeQuery = true)
	List<IRoleListDto> findRoleByUserId(Long roleId);

	@Query(value = "select u.id as Id,u.email as Email, u.user_name as Users , r.role_name as Role from user_role ur \r\n"
			+ "join users u on u.id=ur.user_id and u.is_active=true\r\n"
			+ "join roles r on r.id=ur.role_id and r.is_active=true and ur.is_active=true\r\n", nativeQuery = true)
	public List<IListUsersRoleDto> getAllUsersRole();

}
