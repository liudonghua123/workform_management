package workform_management.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Util {
	
	private static final Logger logger = LoggerFactory.getLogger(Util.class);

	private static SimpleDateFormat fileNameDatePattern = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
	private static SimpleDateFormat workFormDateFormat1 = new SimpleDateFormat("yyyy.M.d");
	private static SimpleDateFormat workFormDateFormat2 = new SimpleDateFormat("yyyy-M-d HH:mm");
	private static SimpleDateFormat workFormDateFormat3 = new SimpleDateFormat("yyyy-M-d HH:mm:ss");

	public static String generateFilePath(String fileExtension) {
		String fileName = fileNameDatePattern.format(new Date());
		return String.format("%s.%s", fileName, fileExtension);
	}
	
	public static Date parseWorkFormDate(String workFormDateString) {
		Date parsedDate = null;
		try {
			int dateStringLength = workFormDateString.length();
			// empty date field
			if(dateStringLength == 0) {
				parsedDate = new Date();
				logger.error("parseWorkFormDate(\"{}\") error, empty workFormDateString use current date", workFormDateString);
			}
			// M.d format, like 10.10 or 9.9
			else if(dateStringLength <= 5) {
				workFormDateString = "2015." + workFormDateString;
				parsedDate = workFormDateFormat1.parse(workFormDateString);
			}
			// yyyy.M.d format, like 2015.10.10 10:10 or 2015.9.9 9:9
			else if(dateStringLength <= 16) {
				parsedDate = workFormDateFormat2.parse(workFormDateString);
			}
			// yyyy.M.d format, like 2015.10.10 10:10:10 or 2015.9.9 9:9:9
			else if(dateStringLength <= 19) {
				parsedDate = workFormDateFormat3.parse(workFormDateString);
			}
			else {
				logger.error("parseWorkFormDate(\"{}\") error", workFormDateString);
			}
			return parsedDate;
		} catch (ParseException e) {
			logger.error("parseWorkFormDate(\"{}\") error: {}", workFormDateString, e.getMessage());
		}
		return parsedDate;
	}

	/**
	 * get the string representation of column <code>i</code> in <code>row</code>
	 * @param row
	 * @param i
	 * @return
	 */
	public static String getRowFieldStringValue(Row row, int i) {
		Cell cell = row.getCell(i);
		// the cell may be null
		if(cell == null) {
			return "";
		}
		String content = cell.getCellType() == Cell.CELL_TYPE_NUMERIC ? String.valueOf((long)cell.getNumericCellValue())
				: cell.getStringCellValue().trim();
		return content;
	}

	/**
	 * convert iterator to list
	 * @param iter
	 * @return
	 */
	public static <T> List<T> copyIterator(Iterator<T> iter) {
		List<T> copy = new ArrayList<T>();
		while (iter.hasNext())
			copy.add(iter.next());
		return copy;
	}

}
