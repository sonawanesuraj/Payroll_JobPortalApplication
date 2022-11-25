package com.app.serviceImpl;

import java.util.List;

import com.app.dto.IListPermissionDto;
import com.app.dto.PermissionDto;
import com.app.entities.PermissionEntity;
import com.app.exception.ResourceNotFoundException;
import com.app.repository.PermissionRepository;
import com.app.serviceInterface.PermissionInterface;
import com.app.util.CacheOperation;
import com.app.util.Pagination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PermissionServiceImpl implements PermissionInterface {

	@Autowired
	private PermissionRepository permissionRepository;

	@Autowired
	private CacheOperation cache;

	@Override
	public PermissionDto addPermission(PermissionDto permissionDto) {
		PermissionEntity permissionEntity = new PermissionEntity();
		permissionEntity.setActionName(permissionDto.getActionName());
		permissionEntity.setDescription(permissionDto.getDescription());
		permissionEntity.setMethod(permissionDto.getMethod());
		permissionEntity.setPath(permissionDto.getPath());
		permissionEntity.setBaseUrl(permissionDto.getBaseUrl());
		permissionRepository.save(permissionEntity);
		cache.removeAllFromRedisCache();

		return permissionDto;
	}

	@Override
	public PermissionDto updatePermission(Long id, PermissionDto permissionDto) {
		PermissionEntity permissionEntity = this.permissionRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Permission Id Not Found"));
		permissionEntity.setActionName(permissionDto.getActionName());
		permissionEntity.setDescription(permissionDto.getDescription());
		permissionEntity.setMethod(permissionDto.getMethod());
		permissionEntity.setPath(permissionDto.getPath());
		permissionEntity.setBaseUrl(permissionDto.getBaseUrl());
		permissionRepository.save(permissionEntity);
		cache.removeAllFromRedisCache();
		return permissionDto;
	}

	@Override
	public void deletePermission(Long id) {
		this.permissionRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Resource Not Found For this id"));
		this.permissionRepository.deleteById(id);
		cache.removeAllFromRedisCache();

	}

	@Override
	public List<IListPermissionDto> getPermissionById(Long id) {
		List<IListPermissionDto> iListPermissionDto;

		return iListPermissionDto = this.permissionRepository.findById(id, IListPermissionDto.class);
	}

	@Override
	public Page<IListPermissionDto> getAllPermission(String search, String pageNumber, String pageSize) {
		Pageable paging = new Pagination().getPagination(pageNumber, pageSize);

		Page<IListPermissionDto> iListPermissionDto;
		iListPermissionDto = permissionRepository.findByOrderByIdAsc(paging, IListPermissionDto.class);

		return iListPermissionDto;
	}

}
