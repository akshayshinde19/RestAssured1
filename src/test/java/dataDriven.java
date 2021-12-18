import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import groovyjarjarpicocli.CommandLine.Help.Column;

public class dataDriven {
	
	public static ArrayList<String> getData(String tcName) throws IOException {

		// TODO Auto-generated method stub
		
		ArrayList<String> al= new ArrayList();
		
		FileInputStream fis= new FileInputStream("C://Users//Shree//Desktop//Rest//ProjectNameAPIFramework_Maven//src//test//java//resources//TestCasesInput.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(fis);		
		
		for(int i=0;i<workbook.getNumberOfSheets();i++) {
			if(workbook.getSheetName(i).equalsIgnoreCase("sheet1")) {			
			XSSFSheet sheetObj= workbook.getSheetAt(i);
			
			Iterator<Row> rowObj=sheetObj.iterator();
			
			Row firstRow=rowObj.next();
			
			Iterator<Cell> cell=firstRow.cellIterator();	
			int j=0;
			int coulmn=0;
			
			while(cell.hasNext()) {
				Cell cellValue=cell.next();
				if(cellValue.getStringCellValue().equalsIgnoreCase("Data2")) {
					coulmn = j;
					break;
				}
				j++;
			}
			
			System.out.println("col "+coulmn);
			
			
			while(rowObj.hasNext()) {
				Row r=rowObj.next();
				
				if(r.getCell(0).getStringCellValue().equalsIgnoreCase(tcName)) {
					Iterator<Cell> cv=r.cellIterator();
					
					while(cv.hasNext()) {
						System.out.println(cv.next().getStringCellValue());
						//al.add(cv.next().getStringCellValue());
					}
					
				}
				
			}
			
			}
			
		}
		
		return al;
		
	}

	public static void main(String[] args) throws IOException {
		getData("Purchase");
	}

}
