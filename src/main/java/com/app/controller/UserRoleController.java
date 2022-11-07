package com.app.controller;

import java.util.List;

import com.app.dto.ErrorResponseDto;
import com.app.dto.IListUserRole;
import com.app.dto.SuccessResponseDto;
import com.app.dto.UserRoleDto;
import com.app.entities.UserRoleEntity;
import com.app.serviceInterface.UserRoleInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/userRole")
public class UserRoleController {

	@Autowired
	private UserRoleInterface userRoleInterface;

	@PostMapping()
	public ResponseEntity<?> addUserRole(@RequestBody UserRoleDto userRoleDto) {
		try {

			UserRoleDto dto = this.userRoleInterface.addUserRole(userRoleDto);

			return new ResponseEntity<>(new SuccessResponseDto("Assign Succefully", "Assigned", "added"),
					HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), "Please Enter Valid User OR Role"),
					HttpStatus.BAD_REQUEST);
		}

	}

	@PutMapping("/{id}")
	public UserRoleDto updateUserRole(@PathVariable Long id, @RequestBody UserRoleDto userRoleDto) {
		return this.userRoleInterface.updateRoleOrUser(id, userRoleDto);

	}

	@PreAuthorize("hasRole('CandidateRecruiterDelete')")

	@DeleteMapping("/CandidateRecruiterDelete/{id}")
	public ResponseEntity<?> deleteUserTopic(@PathVariable Long id) {

		try {
			UserRoleEntity userTopicEntity = this.userRoleInterface.deleteUserRole(id);

			return new ResponseEntity<>(
					new SuccessResponseDto("Deleted Succesfully", "Deleted", userTopicEntity.getId()), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), "Does Not deleted"),
					HttpStatus.BAD_REQUEST);
		}

	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getUserRoleById(@PathVariable Long id) {
		try {

			List<IListUserRole> iListUserRole = this.userRoleInterface.getUserRoleById(id);
			return new ResponseEntity<>(iListUserRole, HttpStatus.OK);
		} catch (Exception e) {
			System.out.print("Not Found...");

			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

	@PreAuthorize("hasRole('getAllUserRole')")
	@GetMapping("/getAllUserRole")
	public ResponseEntity<?> getAllUserRole(@RequestParam(defaultValue = "") String search,
			@RequestParam(defaultValue = "1") String pageNo, @RequestParam(defaultValue = "5") String PageSize) {
		Page<IListUserRole> userRole = this.userRoleInterface.getAllUserRole(search, pageNo, PageSize);

		if (userRole.getTotalElements() != 0) {
			return new ResponseEntity<>(new SuccessResponseDto("All UserRole", "Success", userRole.getContent()),
					HttpStatus.OK);

		}

		return new ResponseEntity<>(new ErrorResponseDto("Data Not Found", "Data Not Found"), HttpStatus.NOT_FOUND);
	}
}
