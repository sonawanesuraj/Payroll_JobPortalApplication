package com.app.controller;

import java.util.List;

import com.app.dto.ErrorResponseDto;
import com.app.dto.IListPermissionDto;
import com.app.dto.PermissionDto;
import com.app.dto.SuccessResponseDto;
import com.app.exception.ResourceNotFoundException;
import com.app.serviceInterface.PermissionInterface;
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
@RequestMapping(ApiUrls.PERMISSIONS)
public class PermissionController {

	@Autowired
	private PermissionInterface permissionInterface;

	@PreAuthorize("hasRole('PermissionAdd')")
	@PostMapping()
	public ResponseEntity<?> addPermission(@RequestBody PermissionDto permissionDto) {

		try {
			this.permissionInterface.addPermission(permissionDto);
			return new ResponseEntity<>(
					new SuccessResponseDto("permission Added Successfully", "Permission Added", "Data added"),
					HttpStatus.ACCEPTED);
		} catch (ResourceNotFoundException e) {

			return new ResponseEntity<>(new ErrorResponseDto("Permission already exist", "Please add new Permission"),
					HttpStatus.BAD_REQUEST);
		}

	}

	@PreAuthorize("hasRole('PermissionEdit')")
	@PutMapping("/{id}")
	public ResponseEntity<?> updatePermission(@RequestBody PermissionDto permissionDto, @PathVariable long id) {
		try {
			this.permissionInterface.updatePermission(id, permissionDto);
			return new ResponseEntity<>(
					new SuccessResponseDto(" permission updated sucessfully", "permission updated !!", permissionDto),
					HttpStatus.CREATED);
		} catch (ResourceNotFoundException e) {
			return new ResponseEntity<>(new ErrorResponseDto("permission Id Not Found  ", "Something went wrong"),
					HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('PermissionDelete')")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletePermission(@PathVariable(name = "id") Long id) throws ResourceNotFoundException {
		try {
			permissionInterface.deletePermission(id);
			return ResponseEntity.ok(new SuccessResponseDto("Deleted Succesfully", "Deleted", id));
		} catch (Exception e) {

			return ResponseEntity.ok(new ErrorResponseDto(e.getMessage(), "Enter Valid Id"));
		}
	}

	@PreAuthorize("hasRole('PermissionsView')")
	@GetMapping("/{id}")
	public ResponseEntity<?> getPermissionById(@PathVariable Long id) {
		try {
			List<IListPermissionDto> iListPermissionDto = this.permissionInterface.getPermissionById(id);
			return new ResponseEntity<>(new SuccessResponseDto(" Permission:", "Success", iListPermissionDto),
					HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), "permission not found "),
					HttpStatus.BAD_REQUEST);

		}
	}

	@PreAuthorize("hasRole('PermissionsList')")
	@GetMapping(ApiUrls.GET_ALL)
	public ResponseEntity<?> getAllPermission(@RequestParam(defaultValue = "") String search,
			@RequestParam(defaultValue = "1") String pageNo, @RequestParam(defaultValue = "5") String PageSize) {
		Page<IListPermissionDto> permission = this.permissionInterface.getAllPermission(search, pageNo, PageSize);

		if (permission.getTotalElements() != 0) {
			return new ResponseEntity<>(new SuccessResponseDto("All permissions", "Success", permission.getContent()),
					HttpStatus.OK);

		}

		return new ResponseEntity<>(new ErrorResponseDto("Data Not Found", "Data Not Found"), HttpStatus.NOT_FOUND);
	}
}
