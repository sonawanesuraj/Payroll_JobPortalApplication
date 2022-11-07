package com.app.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import com.app.dto.IListRolePermission;
import com.app.dto.IPermissionIdList;
import com.app.dto.RolePermissionDto;
import com.app.entities.PermissionEntity;
import com.app.entities.RoleEntity;
import com.app.entities.RolePermissionEntity;
import com.app.entities.UserRoleEntity;
import com.app.exception.ResourceNotFoundException;
import com.app.repository.PermissionRepository;
import com.app.repository.RolePermissionRepository;
import com.app.repository.RoleRepository;
import com.app.repository.UserRoleRepository;
import com.app.serviceInterface.RolePermissionInterface;
import com.app.util.CacheOperation;
import com.app.util.Pagination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RolePermissionServiceImpl implements RolePermissionInterface {

	@Autowired
	private RolePermissionRepository rolePermissionRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PermissionRepository permissionRepository;

	@Autowired
	private CacheOperation cache;

	@Autowired
	private UserRoleRepository userRoleRepository;

	@Override
	public RolePermissionDto addRolePermission(RolePermissionDto rolePermissionDto) {
		RolePermissionEntity rolePermissionEntity = this.rolePermissionRepository
				.findByRolePermissionId(rolePermissionDto.getRoleId(), rolePermissionDto.getPermissionId());

		if (rolePermissionEntity == null) {
			RoleEntity roleEntity = this.roleRepository.findById(rolePermissionDto.getRoleId())
					.orElseThrow(() -> new ResourceNotFoundException("Role Not Found"));

			PermissionEntity permissionEntity = permissionRepository.findById(rolePermissionDto.getPermissionId())
					.orElseThrow(() -> new ResourceNotFoundException("permission Not Found"));

			ArrayList<RolePermissionEntity> rolePermissionEntityList = new ArrayList<RolePermissionEntity>();

			RolePermissionEntity newRolePermissionEntity = new RolePermissionEntity();
			newRolePermissionEntity.setRole(roleEntity);
			newRolePermissionEntity.setPermission(permissionEntity);
			rolePermissionEntityList.add(newRolePermissionEntity);
			rolePermissionRepository.saveAll(rolePermissionEntityList);

		} else {
			throw new ResourceNotFoundException("resource already exists");

		}

		return null;
	}

	@Override
	public RolePermissionDto updateRoleOrPermission(Long id, RolePermissionDto rolePermissionDto) {

		RolePermissionEntity rolePermissionEntity = this.rolePermissionRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Resource Not Found"));

		RoleEntity roleEntity = this.roleRepository.findById(rolePermissionDto.getRoleId())
				.orElseThrow(() -> new ResourceNotFoundException("Role Does Not Found"));

		PermissionEntity permissionEntity = this.permissionRepository.findById(rolePermissionDto.getPermissionId())
				.orElseThrow(() -> new ResourceNotFoundException("Permission Not Found"));

		rolePermissionEntity.setRole(roleEntity);
		rolePermissionEntity.setPermission(permissionEntity);
		rolePermissionRepository.save(rolePermissionEntity);

		return rolePermissionDto;
	}

	@Override
	public void deleteRolePermission(Long id) {
		this.rolePermissionRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Resource Not Found For this id"));
		this.rolePermissionRepository.deleteById(id);

	}

	@Override
	public List<IListRolePermission> getRolePermissionById(Long id) {
		List<IListRolePermission> iListRolePermission;
		return iListRolePermission = this.rolePermissionRepository.findById(id, IListRolePermission.class);
	}

	@Override
	public Page<IListRolePermission> getAllRolePermission(String search, String pageNumber, String pageSize) {
		Pageable paging = new Pagination().getPagination(pageNumber, pageSize);

		Page<IListRolePermission> iListRolePermission;
		iListRolePermission = rolePermissionRepository.findByOrderByIdAsc(paging, IListRolePermission.class);

		return iListRolePermission;
	}

	public void removeFromAllCache() {

		this.rolePermissionRepository.findAll();

		cache.removeAllFromRedisCache();
	}

	@Override
	public ArrayList<String> getPermissionByUserId(Long id) {

		ArrayList<UserRoleEntity> userrole = userRoleRepository.getRolesOfUser(id);

		ArrayList<Long> roles = new ArrayList<>();
		for (int i = 0; i < userrole.size(); i++) {
			roles.add(userrole.get(i).getRole().getId());

		}
		List<IPermissionIdList> rolesPermission = rolePermissionRepository.findPermissionByRoleIdIn(roles,
				IPermissionIdList.class);

		ArrayList<String> permissions = new ArrayList<>();
		for (IPermissionIdList element : rolesPermission) {

			permissions.add(element.getPermissionActionName());

		}

		return permissions;

	}

}
