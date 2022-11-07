package com.app.serviceImpl;

import com.app.dto.IRoleListDto;
import com.app.dto.RoleDto;
import com.app.entities.RoleEntity;
import com.app.exception.ResourceNotFoundException;
import com.app.repository.RoleRepository;
import com.app.serviceInterface.RoleInterface;
import com.app.util.Pagination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleInterface {

	@Autowired
	private RoleRepository roleRepository;

	@Override
	public RoleDto addrole(RoleDto roleDto) {
		RoleEntity roleEntity = new RoleEntity();
		roleEntity.setRoleName(roleDto.getRoleName());
		roleEntity.setDescription(roleDto.getDescription());
		this.roleRepository.save(roleEntity);
		return roleDto;
	}

	@Override
	public RoleDto getRoleById(Long id) {
		RoleDto roleDto = new RoleDto();
		RoleEntity roleEntity = this.roleRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("role id not found, Please enter correct roleId"));
		roleDto.setId(roleEntity.getId());
		roleDto.setRoleName(roleEntity.getRoleName());
		roleDto.setDescription(roleEntity.getDescription());
		return roleDto;
	}

	@Override
	public Page<IRoleListDto> getAllRoles(String roleName, String pageNo, String pageSize) {

		Pageable pageable = new Pagination().getPagination(pageNo, pageSize);
		Page<IRoleListDto> iRoleListDto;
		if ((roleName == "") || (roleName == null) || (roleName.length() == 0)) {

			iRoleListDto = roleRepository.findByOrderByIdAsc(pageable, IRoleListDto.class);
		} else {

			iRoleListDto = roleRepository.findByRoleNameIgnoreCaseContaining(roleName, pageable, IRoleListDto.class);
		}
		return iRoleListDto;
	}

	@Override
	public RoleDto updateRoles(RoleDto roleDto, Long id) {

		RoleEntity roleEntity = this.roleRepository.findById(id).orElseThrow();
		roleEntity.setRoleName(roleDto.getRoleName());
		roleEntity.setDescription(roleDto.getDescription());
		this.roleRepository.save(roleEntity);
		return roleDto;

	}

	@Override
	public void deleteRole(Long id) {
		this.roleRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Resource Not Found for this Id "));
		roleRepository.deleteById(id);

	}

}
