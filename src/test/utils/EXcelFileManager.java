package utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;

public class EXcelFileManager {

    private XSSFWorkbook workbook ;
    private XSSFSheet sheet ;


   public  EXcelFileManager(String filePath , String sheetName) {

       try{
            File file = new File(filePath);
            FileInputStream fileInputStream = new FileInputStream(file);
            workbook = new XSSFWorkbook(fileInputStream);
            sheet = workbook.getSheet(sheetName);

    }catch (Exception e){
        e.getStackTrace();
        }
   }
public  String getCellData(int rowNum , int colNum) {
    Cell cell = sheet.getRow(rowNum).getCell(colNum);
    DataFormatter dataFormatter = new DataFormatter();
    return dataFormatter.formatCellValue(cell);
}
}
