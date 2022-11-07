package com.app.controller;

import com.app.dto.ErrorResponseDto;
import com.app.dto.ExcelHelper;
import com.app.serviceInterface.ExcelInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

public class ExcelController {

	@Autowired
	private ExcelInterface excelInterface;

	@PostMapping("uploadExcel-file")
	public ResponseEntity<?> uploadExcelFile(@RequestParam("file") MultipartFile multipartFile) {

		if (ExcelHelper.hasExcelFormat(multipartFile)) {
			try {
				excelInterface.addExcelFile(multipartFile);

				return new ResponseEntity<>("Excel file Successfully uploaded", HttpStatus.OK);

			} catch (Exception e) {

				return new ResponseEntity<>(new ErrorResponseDto(e.getMessage(), "could not upload the file"),
						HttpStatus.BAD_REQUEST);
			}
		}
		return new ResponseEntity<>("please upload an Excel file", HttpStatus.NOT_ACCEPTABLE);
	}

}
