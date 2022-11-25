package com.app.controller;

import java.util.List;

import com.app.dto.ErrorResponseDto;
import com.app.dto.IUserListDto;
import com.app.dto.ListResponseDto;
import com.app.dto.SuccessResponseDto;
import com.app.dto.UserDto;
import com.app.exception.ResourceNotFoundException;
import com.app.serviceImpl.UserServiceImpl;
import com.app.serviceInterface.UserInterface;
import com.app.util.ApiUrls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
	@Autowired
	private UserInterface userInterface;

	@Autowired
	private UserServiceImpl userServiceImpl;

	@PreAuthorize("hasRole('UserView')")
	@GetMapping("/{id}")
	public ResponseEntity<?> getUserById(@PathVariable("id") Long id) {
		try {
			List<IUserListDto> userDto = this.userInterface.getUserById(id);
			return new ResponseEntity<>(new SuccessResponseDto("User Get Successfully", "Success", userDto),
					HttpStatus.OK);
		} catch (ResourceNotFoundException e) {
			return new ResponseEntity<>(new ErrorResponseDto("user Not found", "Please Enter Correct Id "),
					HttpStatus.NOT_FOUND);
		}
	}

	@PreAuthorize("hasRole('UsersList')")
	@GetMapping(ApiUrls.GET_ALL)
	public ResponseEntity<?> getAllUsers(@RequestParam(defaultValue = "") String search,
			@RequestParam(defaultValue = "1") String pageNo, @RequestParam(defaultValue = "5") String pageSize) {
		Page<IUserListDto> user = userServiceImpl.getAllUsers(search, pageNo, pageSize);

		if (user.getTotalElements() != 0) {
			return new ResponseEntity<>(new SuccessResponseDto("All Users List", "success",
					new ListResponseDto(user.getContent(), user.getTotalElements())), HttpStatus.OK);

		}

		return new ResponseEntity<>(new ErrorResponseDto("Data Not Found", "Data Not Found"), HttpStatus.NOT_FOUND);
	}

	@PreAuthorize("hasRole('UserEdit')")
	@PutMapping("/{id}")
	public ResponseEntity<?> updateUser(@RequestBody UserDto userDto, @PathVariable long id) {
		try {
			this.userInterface.updateUser(id, userDto);
			return new ResponseEntity<>(new SuccessResponseDto("User updated sucessfully", "User updated !!", userDto),
					HttpStatus.CREATED);
		} catch (ResourceNotFoundException e) {
			return new ResponseEntity<>(new ErrorResponseDto("User Id Not Found  ", "Something went wrong"),
					HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('UserDelete')")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable(name = "id") Long id) throws ResourceNotFoundException {
		try {
			userServiceImpl.deleteUser(id);

			return ResponseEntity.ok(new SuccessResponseDto("Deleted Succesfully", "Deleted", id));
		} catch (Exception e) {

			return ResponseEntity.ok(new ErrorResponseDto(e.getMessage(), "Enter Valid Id"));

		}
	}

}
