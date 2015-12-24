package workform_management.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import workform_management.model.WorkForm;
import workform_management.service.WorkFormService;
import workform_management.util.Util;

@Controller
@RequestMapping("/upload")
public class UploadController {

	private static final Logger logger = LoggerFactory.getLogger(UploadController.class);

	@Autowired
	WorkFormService workFormService;

	@RequestMapping("/workform")
	@ResponseBody
	public String handleUploadWorkForm(@RequestParam("file") MultipartFile file) {
		if (!file.isEmpty()) {
			try {
				// save the file on the server
				byte[] bytes = file.getBytes();
				String filePath = Util.generateFilePath("xlsx");
				File fileOnServer = new File(filePath);
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(fileOnServer));
				stream.write(bytes);
				stream.close();
				logger.debug("save the uploaded file on {}", fileOnServer.getAbsolutePath());

				// parse xlsx file
				List<WorkForm> workForms = parseWorkFormXLSXFile(file);
				logger.debug("starting workFormService.updateAll(workForms)");
				// update database record
				List<WorkForm> savedWorkforms = workFormService.updateAll(workForms);
				logger.debug("ending workFormService.updateAll(workForms), updated {} entities", savedWorkforms.size());

				return String.format("You successfully uploaded workform file %s and processed %d entities!",
						file.getName(), savedWorkforms.size());
			}
			catch (IOException e) {
				return "You failed to process the file: " + file.getName() + ": " + e.getMessage();
			}
		} else {
			return "You failed to upload the file because the file was empty.";
		}
	}

	private List<WorkForm> parseWorkFormXLSXFile(MultipartFile file) throws IOException {
		List<WorkForm> workForms = new ArrayList<WorkForm>();
		WorkForm workForm;
		boolean isProcessedHeader = false;
		Workbook workbook = new XSSFWorkbook(file.getInputStream());
		// get first sheet
		Sheet sheet = workbook.getSheetAt(0);
		Iterator<Row> rowIterator = sheet.iterator();
		while (rowIterator.hasNext()) {
			Row row = (Row) rowIterator.next();
			Iterator<Cell> cellIterator = row.iterator();
			// skip header and empty row
			if (!isProcessedHeader) {
				isProcessedHeader = true;
				continue;
			}
			boolean isBlank = !cellIterator.hasNext();
			if(!isBlank) {
				Cell cell = cellIterator.next();
				isBlank = (cell.getCellType() == Cell.CELL_TYPE_STRING && cell.getStringCellValue().trim().equals("")) ||
						(cell.getCellType() == Cell.CELL_TYPE_NUMERIC && Math.abs(cell.getNumericCellValue() - 0) < 0.001);
			}
			if (isBlank) {
				continue;
			}
			// prefix id with 2015 if the length of that field is equal to 6 digits
			String idString = Util.getRowFieldStringValue(row, 0).trim();
			if(idString.length() <= 6) {
				idString = "2015" + idString;
			}
			try {
				workForm = new WorkForm(Integer.parseInt(idString),
						Util.parseWorkFormDate(Util.getRowFieldStringValue(row, 1)), true, Util.getRowFieldStringValue(row, 2),
						Util.getRowFieldStringValue(row, 3), Util.getRowFieldStringValue(row, 4),
						Util.getRowFieldStringValue(row, 5), Util.getRowFieldStringValue(row, 6),
						Util.getRowFieldStringValue(row, 7), Util.getRowFieldStringValue(row, 8),
						Util.getRowFieldStringValue(row, 9), Util.getRowFieldStringValue(row, 10),
						Util.getRowFieldStringValue(row, 11));
				workForms.add(workForm);
			}
			catch(NumberFormatException e) {
				logger.error(e.getMessage());
			}
		}
		return workForms;
	}

}
