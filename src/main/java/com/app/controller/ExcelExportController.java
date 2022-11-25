package com.app.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.app.serviceImpl.ExcelServiceImpl;
import com.app.util.ApiUrls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiUrls.EXCEL)
public class ExcelExportController {

	@Autowired
	private ExcelServiceImpl excelServiceImpl;

	@PreAuthorize("hasRole('ExcelView')")
	@GetMapping(ApiUrls.GET_ALL)
	public void exportToExcel(HttpServletResponse response) throws IOException {
		response.setContentType("application/octet-stream");
		String headerkey = "Content-Disposition";
		String headerValue = "attachment,filename=JobPortal_Information.xlsx";
		response.setHeader(headerkey, headerValue);
		excelServiceImpl.exportJobToExcel(response);
	}

	@PreAuthorize("hasRole('ExcelListView')")
	@GetMapping()
	public void exportUserAppliedJobList(HttpServletResponse response) throws IOException {
		response.setContentType("application/octet-stream");
		String headerkey = "Content-Disposition";
		String headerValue = "attachment,filename=Users And Aplied Jobs";
		response.setHeader(headerkey, headerValue);
		excelServiceImpl.exportUserAppliedJobListToExcelFile(response);
	}
}
