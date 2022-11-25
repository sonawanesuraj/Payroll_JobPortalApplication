package com.app.serviceImpl;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.app.dto.IListUsersJobDto;
import com.app.dto.IListUsersRoleDto;
import com.app.repository.UserJobRepository;
import com.app.repository.UserRoleRepository;
import com.app.util.ExcelExportUtils;
import com.app.util.UserJobExcel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExcelServiceImpl {

	@Autowired
	private UserRoleRepository userRoleRepository;

	@Autowired
	private UserJobRepository userJobRepository;

	public List<IListUsersRoleDto> exportJobToExcel(HttpServletResponse response) throws IOException {
		List<IListUsersRoleDto> iListUsersRoleDto = this.userRoleRepository.getAllUsersRole();
		ExcelExportUtils exportUtils = new ExcelExportUtils(iListUsersRoleDto);
		exportUtils.exportDataToExcel(response);
		return iListUsersRoleDto;
	}

	public List<IListUsersJobDto> exportUserAppliedJobListToExcelFile(HttpServletResponse response) throws IOException {
		List<IListUsersJobDto> iListUsersJobDto = this.userJobRepository.getUserAppliedJobList();
		UserJobExcel exportUtils = new UserJobExcel(iListUsersJobDto);
		exportUtils.exportDataToExcel(response);
		return iListUsersJobDto;

	}

}
