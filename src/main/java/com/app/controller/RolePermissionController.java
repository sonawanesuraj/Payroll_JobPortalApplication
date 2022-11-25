package com.app.controller;

import java.util.List;

import com.app.dto.ErrorResponseDto;
import com.app.dto.IListRolePermission;
import com.app.dto.RolePermissionDto;
import com.app.dto.SuccessResponseDto;
import com.app.exception.ResourceNotFoundException;
import com.app.serviceInterface.RolePermissionInterface;
import com.app.util.ApiUrls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(ApiUrls.ROLEPERMISSION)
public class RolePermissionController {

	@Autowired
	private RolePermissionInterface rolePermissionInterface;

	@PreAuthorize("hasRole('RolePermissionAdd')")
	@PostMapping()
	public ResponseEntity<?> addRoleToPermission(@RequestBody RolePermissionDto rolePermissionDto) {
		try {

			RolePermissionDto dto = this.rolePermissionInterface.addRolePermission(rolePermissionDto);

			return new ResponseEntity<>(new SuccessResponseDto("Assign Succefully", "Assigned", "added"),
					HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), "Please Enter Valid Role OR Permission"),
					HttpStatus.BAD_REQUEST);
		}

	}

	@PreAuthorize("hasRole('RolePermissionEdit')")
	@PutMapping("/{id}")
	public ResponseEntity<?> updateRolePermission(@PathVariable Long id,
			@RequestBody RolePermissionDto rolePermissionDto) {

		try {

			this.rolePermissionInterface.updateRoleOrPermission(id, rolePermissionDto);

			return new ResponseEntity<>(new SuccessResponseDto("update Succefully", "success", rolePermissionDto),
					HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), "Please Enter Valid Role OR Permission"),
					HttpStatus.BAD_REQUEST);
		}

	}

	@PreAuthorize("hasRole('RolePermissionDelete')")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteRolePermission(@PathVariable(name = "id") Long id) throws ResourceNotFoundException {
		try {
			rolePermissionInterface.deleteRolePermission(id);

			return ResponseEntity.ok(new SuccessResponseDto("Deleted Succesfully", "Deleted", id));
		} catch (Exception e) {

			return ResponseEntity.ok(new ErrorResponseDto(e.getMessage(), "Enter Valid Id"));
		}
	}

	@PreAuthorize("hasRole('RolePermissionView')")
	@GetMapping("/{id}")
	public ResponseEntity<?> getRolePermissionById(@PathVariable Long id) {
		try {

			List<IListRolePermission> iListRolePermission = this.rolePermissionInterface.getRolePermissionById(id);
			return new ResponseEntity<>(iListRolePermission, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

	@PreAuthorize("hasRole('RolePermissionList')")
	@GetMapping(ApiUrls.GET_ALL)
	public ResponseEntity<?> getAllRolePermission(@RequestParam(defaultValue = "") String search,
			@RequestParam(defaultValue = "1") String pageNo, @RequestParam(defaultValue = "5") String PageSize) {
		Page<IListRolePermission> rolePermission = this.rolePermissionInterface.getAllRolePermission(search, pageNo,
				PageSize);

		if (rolePermission.getTotalElements() != 0) {
			return new ResponseEntity<>(
					new SuccessResponseDto("All UserQuestion", "Success", rolePermission.getContent()), HttpStatus.OK);

		}

		return new ResponseEntity<>(new ErrorResponseDto("Data Not Found", "Data Not Found"), HttpStatus.NOT_FOUND);
	}
}
