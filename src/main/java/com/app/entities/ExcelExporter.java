package com.app.entities;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelExporter {
	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	private List<UserEntity> listUsers;

	public ExcelExporter(List<UserEntity> listUsers) {
		this.listUsers = listUsers;
		workbook = new XSSFWorkbook();
	}

	private void writeHeaderLine() {
		sheet = workbook.createSheet("Sheet1");

		Row row = sheet.createRow(0);

		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		font.setFontHeight(16);
		style.setFont(font);

		createCell(row, 0, "User ID", style);
		createCell(row, 1, "Name", style);
		createCell(row, 2, "Email", style);
		createCell(row, 3, "Roles", style);

	}

	private void createCell(Row row, int columnCount, Object value, CellStyle style) {
		sheet.autoSizeColumn(columnCount);
		Cell cell = row.createCell(columnCount);
		if (value instanceof Long) {
			cell.setCellValue((Long) value);
		} else if (value instanceof String) {
			cell.setCellValue((String) value);
		} else {
			cell.setCellValue((Long) value);
		}
		cell.setCellStyle(style);

	}

	private void writeDataLines() {
		int rowCount = 1;

		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setFontHeight(14);
		style.setFont(font);

		for (UserEntity user : listUsers) {
			Row row = sheet.createRow(rowCount++);
			int columnCount = 1;

			createCell(row, columnCount++, user.getId(), style);
			createCell(row, columnCount++, user.getName(), style);
			createCell(row, columnCount++, user.getEmail(), style);
			createCell(row, columnCount++, user.getUserRole().toString(), style);
		}
	}

	public void export(HttpServletResponse response) throws IOException {
		writeHeaderLine();
		writeDataLines();

		ServletOutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		workbook.close();

		outputStream.close();

	}
}
