package com.app.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import com.app.dto.IListUserRole;
import com.app.dto.IRoleListDto;
import com.app.dto.UserRoleDto;
import com.app.entities.RoleEntity;
import com.app.entities.UserEntity;
import com.app.entities.UserRoleEntity;
import com.app.exception.ResourceNotFoundException;
import com.app.repository.RoleRepository;
import com.app.repository.UserRepository;
import com.app.repository.UserRoleRepository;
import com.app.serviceInterface.UserRoleInterface;
import com.app.util.Pagination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl implements UserRoleInterface {

	@Autowired
	private UserRoleRepository userRoleRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public UserRoleDto addUserRole(UserRoleDto userRoleDto) {
		UserRoleEntity userRoleEntity = this.userRoleRepository.findByUserandRoleId(userRoleDto.getUserId(),
				userRoleDto.getRoleId());

		if (userRoleEntity == null) {
			UserEntity userEntity = this.userRepository.findById(userRoleDto.getUserId())
					.orElseThrow(() -> new ResourceNotFoundException("User Not Found"));

			RoleEntity roleEntity = this.roleRepository.findById(userRoleDto.getRoleId())
					.orElseThrow(() -> new ResourceNotFoundException("Role Does Not Found"));

			ArrayList<UserRoleEntity> userRoleEntityList = new ArrayList<UserRoleEntity>();

			UserRoleEntity newUserRoleEntity = new UserRoleEntity();
			newUserRoleEntity.setUser(userEntity);
			newUserRoleEntity.setRole(roleEntity);
			userRoleEntityList.add(newUserRoleEntity);
			userRoleRepository.saveAll(userRoleEntityList);

		} else {
			throw new ResourceNotFoundException("resource already exists");

		}
		return userRoleDto;

	}

	@Override
	public UserRoleDto updateRoleOrUser(Long id, UserRoleDto userRoleDto) {

		UserRoleEntity userRoleEntity = this.userRoleRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Resource Not Found"));
		UserEntity userEntity = this.userRepository.findById(userRoleDto.getUserId())
				.orElseThrow(() -> new ResourceNotFoundException("User Not Found"));

		RoleEntity roleEntity = this.roleRepository.findById(userRoleDto.getRoleId())
				.orElseThrow(() -> new ResourceNotFoundException("Role Does Not Found"));
		userRoleEntity.setUser(userEntity);
		userRoleEntity.setRole(roleEntity);
		this.userRoleRepository.save(userRoleEntity);
		return userRoleDto;
	}

	@Override
	public UserRoleEntity deleteUserRole(Long id) {

		UserRoleEntity userRoleEntity = this.userRoleRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Resource Not Found "));
		this.userRoleRepository.deleteById(id);
		return userRoleEntity;

	}

	@Override
	public List<IListUserRole> getUserRoleById(Long id) {
		List<IListUserRole> iListUserRoles;
		return iListUserRoles = this.userRoleRepository.findById(id, IListUserRole.class);
	}

	@Override
	public Page<IListUserRole> getAllUserRole(String userId, String roleId, String pageNumber, String pageSize) {
		Pageable paging = new Pagination().getPagination(pageNumber, pageSize);

		Page<IListUserRole> iListUserRole;

		iListUserRole = userRoleRepository.findByOrderByIdDesc(userId, roleId, paging, IListUserRole.class);

		return iListUserRole;

	}

	@Override
	public List<String> getRoleByUserId(Long roleId) {

		List<String> roleNames = new ArrayList<>();
		List<IRoleListDto> iListRoles;

		iListRoles = this.userRoleRepository.findRoleByUserId(roleId);

		for (int i = 0; i < iListRoles.size(); i++) {
			roleNames.add(iListRoles.get(i).getRoleName());
		}

		return roleNames;

	}

}
