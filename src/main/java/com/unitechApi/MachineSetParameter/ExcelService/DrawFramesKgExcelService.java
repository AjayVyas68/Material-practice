package com.unitechApi.MachineSetParameter.ExcelService;

import com.unitechApi.MachineSetParameter.model.Drawframesperkg;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class DrawFramesKgExcelService {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<Drawframesperkg> ListUser;
    public DrawFramesKgExcelService(List<Drawframesperkg> listData){
        this.ListUser=listData;
        workbook=new XSSFWorkbook();
    }
    private void writeHeaderLine() {
        sheet=workbook.createSheet("drawFramesPerKg Data");
        Row row=sheet.createRow(5);
        CellStyle style= workbook.createCellStyle();
        XSSFFont font=workbook.createFont();
        font.setBold(true);
        font.setFontHeight(12);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFont(font);
        createCell(row,1,"Machine No",style);
        createCell(row,2,"DELIVERY SPEED (MPM)",style);
        createCell(row,3,"Silver Hank",style);
        createCell(row,4,"Machine Efficiency",style);
        createCell(row,5,"Production Rate (Kg/Df/Delivery/8 Hour)",style);
        createCell(row,6,"Production on Rate (Kg/Df/6 Hour)",style);
        createCell(row,7,"Production on Rate (Kg/Df/24 Hour)",style);

        createCell(row,8,"6 Hours",style);
        createCell(row,9,"Shift A Avg. Difference from Target",style);
        createCell(row,10,"6 Hours",style);
        createCell(row,11,"Shift A Avg. Difference from Target",style);
        createCell(row,12,"total Production Shift A",style);
        createCell(row,13,"6 Hours",style);
        createCell(row,14,"Shift B Avg. Difference from Target",style);
        createCell(row,15,"6 Hours",style);
        createCell(row,16,"Shift B Avg. Difference from Target",style);
        createCell(row,17,"total Production Shift B",style);
        createCell(row,18,"Actual Production",style);
        createCell(row,19,"Efficiency",style);
        createCell(row,20,"Target Production Variance In Kg",style);
        createCell(row,21,"Target Production Variance",style);
        //  createCell(row,2,"total Production Shift A",style);
    }
    private void createCell(Row row, int i, Object value, CellStyle style) {

        sheet.autoSizeColumn(i);
        Cell cell= row.createCell(i);
        if (value instanceof Long){
            cell.setCellValue((Long) value);
        }else if (value instanceof Boolean){
            cell.setCellValue((Boolean) value);
        }else if (value instanceof Float){
            cell.setCellValue(String.valueOf(value));
        }
        else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void writeDataLine() {

        int rowcount=6;

        XSSFFont fontHeader=workbook.createFont();
        Row rowHeader=sheet.createRow(4);
        Row rowHeader2=sheet.createRow(2);
        CellStyle styleHeader=workbook.createCellStyle();
        CellStyle styleHeader2=workbook.createCellStyle();

        for (int i = 1; i <= 21; ++i) {
            Cell cell = rowHeader.createCell(i);
            cell.setCellStyle(styleHeader);
        }
        for (int j =11;j<=21 ;++j)
        {
            Cell cell = rowHeader2.createCell(j);
            cell.setCellStyle(styleHeader2);
        }
        styleHeader2.setBorderBottom(BorderStyle.THICK);
        styleHeader2.setBorderTop(BorderStyle.THICK);
        styleHeader2.setBorderLeft(BorderStyle.THICK);
        styleHeader2.setBorderRight(BorderStyle.THICK);
        sheet.addMergedRegion(CellRangeAddress.valueOf("B5:H5"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("I5:M5"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("N5:R5"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("S5:V5"));

        sheet.addMergedRegion(CellRangeAddress.valueOf("I3:V3"));
        sheet.addMergedRegion(CellRangeAddress.valueOf("G3:H3"));
        //sheet.addMergedRegion(CellRangeAddress.valueOf("1:Y1"));
        styleHeader.setBorderBottom(BorderStyle.MEDIUM);
        styleHeader.setBorderTop(BorderStyle.MEDIUM);
        styleHeader.setBorderLeft(BorderStyle.MEDIUM);
        styleHeader.setBorderRight(BorderStyle.MEDIUM);
        fontHeader.setBold(true);
        fontHeader.setFontHeight(10);
        styleHeader.setAlignment(HorizontalAlignment.CENTER);
        styleHeader.setFont(fontHeader);
        // styleHeader.set
        createCell(rowHeader2,1,"Draw Frames ",styleHeader);
        createCell(rowHeader2,8,"Shift Wise Production Report ",styleHeader);
        createCell(rowHeader,2,"Targeted Production ",styleHeader);
        createCell(rowHeader,8,"Shift A Data (Morning) ",styleHeader);
        createCell(rowHeader,13,"Shift B Data (Evening) ",styleHeader);
        createCell(rowHeader,18,"Original Production ",styleHeader);



        XSSFFont font=workbook.createFont();
        CellStyle style= workbook.createCellStyle();
        font.setFontHeight(14);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFont(font);
        for (Drawframesperkg drawFramesPerKg :ListUser){
            Row row=sheet.createRow(rowcount++);
            int countRow=1;
            createCell(row,countRow++,drawFramesPerKg.getDrawFramesMachine().getName(),style);
            createCell(row,countRow++,drawFramesPerKg.getDeliveryspeed(),style);
            createCell(row,countRow++,drawFramesPerKg.getSilverhank(),style);
            createCell(row,countRow++,drawFramesPerKg.getMachineefficency(),style);
            createCell(row,countRow++,drawFramesPerKg.getProductiononratekgdfper8hour(),style);
            createCell(row,countRow++,drawFramesPerKg.getMachineefficencykgdfper6hours(),style);
            createCell(row,countRow++,drawFramesPerKg.getMachineefficencykgdfperday(),style);
            createCell(row,countRow++,drawFramesPerKg.getShift_a_sixHoursOne(),style);
            createCell(row,countRow++,drawFramesPerKg.getAvervg_difference_a_sixHoursOne(),style);
            createCell(row,countRow++,drawFramesPerKg.getShift_a_sixHoursTwo(),style);
            createCell(row,countRow++,drawFramesPerKg.getAvervg_difference_a_sixHoursTwo(),style);
            createCell(row,countRow++,drawFramesPerKg.getTotal_shift_prod_a(),style);
            createCell(row,countRow++,drawFramesPerKg.getShift_b_sixHoursOne(),style);
            createCell(row,countRow++,drawFramesPerKg.getAvervg_difference_b_sixHoursOne(),style);
            createCell(row,countRow++,drawFramesPerKg.getShift_b_sixHoursTwo(),style);
            createCell(row,countRow++,drawFramesPerKg.getAvervg_difference_b_sixHoursTwo(),style);
            createCell(row,countRow++,drawFramesPerKg.getTotal_shift_prod_b(),style);
            createCell(row,countRow++,drawFramesPerKg.getActual_producation(),style);
            createCell(row,countRow++,drawFramesPerKg.getEfficiency(),style);
            createCell(row,countRow++,drawFramesPerKg.getTarget_prod_variance_kg(),style);
            createCell(row,countRow++,drawFramesPerKg.getTarget_prod_variance(),style);



        }
    }



    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLine();
        ServletOutputStream outputStream=response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}
