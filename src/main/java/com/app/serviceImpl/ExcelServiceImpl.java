package com.app.serviceImpl;

import java.util.List;

import com.app.dto.ExcelHelper;
import com.app.entities.ExcelEntity;
import com.app.repository.ExcelRepository;
import com.app.serviceInterface.ExcelInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

public class ExcelServiceImpl implements ExcelInterface {

	@Autowired
	private ExcelRepository excelRepository;

	@Override
	public void addExcelFile(MultipartFile multipartfile) {
		try {
			List<ExcelEntity> demo1 = ExcelHelper.excelTodemo(multipartfile.getInputStream());
			excelRepository.saveAll(demo1);

		} catch (Exception e) {

			throw new RuntimeException("fail to store excel data: " + e.getMessage());
		}
		return;

	}

}
