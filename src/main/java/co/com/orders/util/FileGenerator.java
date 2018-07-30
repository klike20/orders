package co.com.orders.util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class FileGenerator {
	
	private static final String FILE_PREFIX = "/tmp/";
	private static final String EXTENSION = ".xlsx";
	
	 public static File generateFile(String[] products, String office) {
		 
		 String fileName = FILE_PREFIX+office+"-"+(new Date()).toString()+EXTENSION;

	        XSSFWorkbook workbook = new XSSFWorkbook();
	        XSSFSheet sheet = workbook.createSheet("Productos del pedido");

	        int rowNum = 0;
	        int colNum = 0;
	        System.out.println("Creating excel");
	        
	        Row row = sheet.createRow(rowNum++);
	        Cell cell = row.createCell(colNum++);
	        cell.setCellValue("productId");
	        cell = row.createCell(colNum++);
	        cell.setCellValue("unidad");
	        cell = row.createCell(colNum++);
	        cell.setCellValue("cantidad");
	        cell = row.createCell(colNum++);
	        cell.setCellValue("observaciones");
	        
	        int i = 0;
			
			while (i < products.length) {
				
				System.out.println("filegenerator products[i]: " + products[i]);
				
				String[] product = products[i].split(",");
				
				colNum = 0;
				row = sheet.createRow(rowNum++);
				cell = row.createCell(colNum++);
		        cell.setCellValue(product[0]);
		        cell = row.createCell(colNum++);
		        cell.setCellValue(product[1]);
		        cell = row.createCell(colNum++);
		        cell.setCellValue(Integer.valueOf(product[2]));
		        cell = row.createCell(colNum++);
		        cell.setCellValue(product[3]);
				
				i++;
			}

	        try {
	            FileOutputStream outputStream = new FileOutputStream(fileName);
	            workbook.write(outputStream);
	            workbook.close();
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	        System.out.println("Done");
	        
	        return new File (fileName);
	    }

}
