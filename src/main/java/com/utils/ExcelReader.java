package com.utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.input.DataFormat;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.russianfeature.model.Student;
import org.russianfeature.model.StudentLoadInfo;
import org.russianfeature.model.TeacherLoadInfo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class ExcelReader {
    public static final String SAMPLE_XLSX_FILE_PATH = "src/main/resources/sample-xlsx-file.xlsx";

    public static List<StudentLoadInfo> getStudentListFromExcel(File loadFile) throws IOException, InvalidFormatException {
        Workbook workbook = WorkbookFactory.create(loadFile);
        Sheet sheet = workbook.getSheetAt(0);
        DataFormatter dataFormatter = new DataFormatter();

        ObservableList<StudentLoadInfo> studentLoadInfoList = FXCollections.observableArrayList();

        int rowIndex = 0;
        for (Row row: sheet) {
            if (rowIndex == 0) {
                rowIndex++;
                continue;
            }
            int cellIndex = 0;
            StudentLoadInfo studentInfo = new StudentLoadInfo();
            //studentInfo.setLoadFlag(false);
            for(Cell cell: row) {
                String cellValue = dataFormatter.formatCellValue(cell);
                if (cellIndex == 0)
                    studentInfo.setFirstName(cellValue);
                else if (cellIndex == 1)
                    studentInfo.setSecondName(cellValue);
                else if (cellIndex == 2)
                    studentInfo.setLastName(cellValue);
                else if (cellIndex == 3)
                    studentInfo.setBirthDate(cellValue);
                else if (cellIndex == 4)
                    studentInfo.setComment(cellValue);

                cellIndex++;
            }
            studentLoadInfoList.add(studentInfo);
        }

        workbook.close();

        return studentLoadInfoList;

    }

    public static void readXLS() throws IOException, InvalidFormatException {

        // Creating a Workbook from an Excel file (.xls or .xlsx)
        File f = new File(SAMPLE_XLSX_FILE_PATH);
        Workbook workbook = WorkbookFactory.create(f);

        // Retrieving the number of sheets in the Workbook
        System.out.println("Workbook has " + workbook.getNumberOfSheets() + " Sheets : ");

        /*
           =============================================================
           Iterating over all the sheets in the workbook (Multiple ways)
           =============================================================
        */

        // 1. You can obtain a sheetIterator and iterate over it
        Iterator<Sheet> sheetIterator = workbook.sheetIterator();
        System.out.println("Retrieving Sheets using Iterator");
        while (sheetIterator.hasNext()) {
            Sheet sheet = sheetIterator.next();
            System.out.println("=> " + sheet.getSheetName());
        }

        // 2. Or you can use a for-each loop
        System.out.println("Retrieving Sheets using for-each loop");
        for(Sheet sheet: workbook) {
            System.out.println("=> " + sheet.getSheetName());
        }

        // 3. Or you can use a Java 8 forEach with lambda
        System.out.println("Retrieving Sheets using Java 8 forEach with lambda");
        workbook.forEach(sheet -> {
            System.out.println("=> " + sheet.getSheetName());
        });

        /*
           ==================================================================
           Iterating over all the rows and columns in a Sheet (Multiple ways)
           ==================================================================
        */

        // Getting the Sheet at index zero
        Sheet sheet = workbook.getSheetAt(0);

        // Create a DataFormatter to format and get each cell's value as String
        DataFormatter dataFormatter = new DataFormatter();

        // 1. You can obtain a rowIterator and columnIterator and iterate over them
        System.out.println("\n\nIterating over Rows and Columns using Iterator\n");
        Iterator<Row> rowIterator = sheet.rowIterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();

            // Now let's iterate over the columns of the current row
            Iterator<Cell> cellIterator = row.cellIterator();

            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                String cellValue = dataFormatter.formatCellValue(cell);
                System.out.print(cellValue + "\t");
            }
            System.out.println();
        }

        // 2. Or you can use a for-each loop to iterate over the rows and columns
        System.out.println("\n\nIterating over Rows and Columns using for-each loop\n");
        for (Row row: sheet) {
            for(Cell cell: row) {
                String cellValue = dataFormatter.formatCellValue(cell);
                System.out.print(cellValue + "\t");
            }
            System.out.println();
        }

        // 3. Or you can use Java 8 forEach loop with lambda
        System.out.println("\n\nIterating over Rows and Columns using Java 8 forEach with lambda\n");
        sheet.forEach(row -> {
            row.forEach(cell -> {
                String cellValue = dataFormatter.formatCellValue(cell);
                System.out.print(cellValue + "\t");
            });
            System.out.println();
        });

        // Closing the workbook
        workbook.close();
    }

    public static List<TeacherLoadInfo> getTeacherListFromExcel(File loadFile) throws IOException, InvalidFormatException {
        Workbook workbook = WorkbookFactory.create(loadFile);
        Sheet sheet = workbook.getSheetAt(0);
        DataFormatter dataFormatter = new DataFormatter();

        ObservableList<TeacherLoadInfo> teacheLoadInfoList = FXCollections.observableArrayList();

        int rowIndex = 0;
        for (Row row: sheet) {
            if (rowIndex == 0) {
                rowIndex++;
                continue;
            }
            int cellIndex = 0;
            TeacherLoadInfo tInfo = new TeacherLoadInfo();
            //studentInfo.setLoadFlag(false);
            for(Cell cell: row) {
                String cellValue = dataFormatter.formatCellValue(cell);
                if (cellIndex == 0)
                    tInfo.setFirstName(cellValue);
                else if (cellIndex == 1)
                    tInfo.setSecondName(cellValue);
                else if (cellIndex == 2)
                    tInfo.setLastName(cellValue);
                else if (cellIndex == 3)
                    tInfo.setBirthDate(cellValue);
                else if (cellIndex == 4)
                    tInfo.setComment(cellValue);

                cellIndex++;
            }
            teacheLoadInfoList.add(tInfo);
        }

        workbook.close();

        return teacheLoadInfoList;

    }
}
