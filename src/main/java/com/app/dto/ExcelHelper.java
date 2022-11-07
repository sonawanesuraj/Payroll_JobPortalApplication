package com.app.dto;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.app.entities.ExcelEntity;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

public class ExcelHelper {
	public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	static String[] HEADERs = { "id", "candidate", "recruiter", "job" };
	static String SHEET = "Sheet1";

	public static boolean hasExcelFormat(MultipartFile file) {

		if (!TYPE.equals(file.getContentType())) {

			return false;
		}

		return true;
	}

	// upload file
	public static List<ExcelEntity> excelTodemo(InputStream inputStream) {

		try {
			Workbook workbook = new XSSFWorkbook(inputStream);

			Sheet sheet = workbook.getSheet(SHEET);

			Iterator<Row> rows = sheet.iterator();

			List<ExcelEntity> demo = new ArrayList<ExcelEntity>();

			int rowNumber = 0;
			while (rows.hasNext()) {

				Row currentRow = rows.next();

				if (rowNumber == 0) {

					rowNumber++;
					continue;
				}

				Iterator<Cell> cellsInRow = currentRow.iterator();

				ExcelEntity demo1 = new ExcelEntity();

				int cellIdx = 0;
				while (cellsInRow.hasNext()) {
					Cell currentCell = cellsInRow.next();

					switch (cellIdx) {

						case 0:
							demo1.setId((long) currentCell.getNumericCellValue());
							break;

						case 1:
							demo1.setCandidate(currentCell.getStringCellValue());
							break;

						case 2:
							demo1.setRecruiter(currentCell.getStringCellValue());
							break;

						case 3:
							demo1.setJob(currentCell.getStringCellValue());
							break;
						default:
							break;
					}

					cellIdx++;
				}

				demo.add(demo1);
			}

			workbook.close();

			return demo;
		} catch (Exception e) {
			throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
		}
	}

}
