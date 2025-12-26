package utils;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileInputStream;

public class EXcelFileManager {

    private XSSFWorkbook workbook ;
    private XSSFSheet sheet ;


   public  EXcelFileManager(String filePath , String sheetName) {

       try( FileInputStream fileInputStream = new FileInputStream(filePath) ){

            File file = new File(filePath);
            workbook = new XSSFWorkbook(fileInputStream);
            sheet = workbook.getSheet(sheetName);
            if (sheet == null){
                StringBuilder available = new StringBuilder();
                for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                    available.append(workbook.getSheetName(i));
                    if (i < workbook.getNumberOfSheets() - 1) available.append(", ");
                }
            }
    }catch (Exception e){
           throw new RuntimeException("Error initializing EXcelFileManager: " + e.getMessage(), e);
       }
   }





    public Object[][] getAllData(int startRow, int startCol, int colsCount) {

        int lastRow = getLastRowNum();
        List<Object[]> rows = new ArrayList<>();

        for (int r = startRow; r <= lastRow; r++) {
            Object[] rowData = new Object[colsCount];
            boolean allEmpty = true;

            for (int c = 0; c < colsCount; c++) {
                String val = getCellData(r, startCol + c).trim();
                rowData[c] = val;
                if (!val.isEmpty()) allEmpty = false;
            }
            if (!allEmpty) rows.add(rowData);
        }

        return rows.toArray(new Object[0][0]);
    }

    public  String getCellData(int rowNum , int colNum) {
        if (sheet == null || sheet.getRow(rowNum) == null || sheet.getRow(rowNum).getCell(colNum) == null) {
            return "";
        }
        DataFormatter dataFormatter = new DataFormatter();
        return dataFormatter.formatCellValue(sheet.getRow(rowNum).getCell(colNum));
    }

    public int getLastRowNum() {
        if (sheet == null) {
            throw new RuntimeException("Sheet is null. Check sheetName and file path.");
        }
        return sheet.getLastRowNum();
    }

    public int getLastColNum(int rowNum) {
        if (sheet == null || sheet.getRow(rowNum) == null) return 0;
        return sheet.getRow(rowNum).getLastCellNum();
    }
}
