package com.mohe.nanjinghaiguaneducation.common.utils;

import cn.hutool.core.date.DateUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.dto.ApplyPersonExcelDto;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;


/**
 * @author ymn
 * excel操作工具类
 * @date 2020/2/5 11:06
 */
public class EasyExcelUtils {
    private static final Logger logger = LoggerFactory.getLogger(EasyExcelUtils.class);
    private static final int MAX_USER_IMPORT = 1000;

    /**
     * 单sheet导出
     *
     * @param response  HttpServletResponse
     * @param list      数据 list
     * @param fileName  导出的文件名
     * @param sheetName 导入文件的 sheet 名
     * @param model     Excel 模型class对象
     */
    public static void writeExcel(HttpServletResponse response, List<?> list,
                                  String fileName, String sheetName, Class model) {
        try {
            //表头样式
            WriteCellStyle headWriteCellStyle = new WriteCellStyle();
            //设置表头居中对齐
            headWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
            //内容样式
            WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
            //设置内容靠左对齐
            contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.LEFT);
            HorizontalCellStyleStrategy horizontalCellStyleStrategy = new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
            EasyExcel.write(getOutputStream(fileName, response), model)
                    .excelType(ExcelTypeEnum.XLSX)
                    .sheet(sheetName)
                    .registerWriteHandler(new ExcelWidthStyleStrategy())
                    .doWrite(list);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("excel导出失败");
        }
    }

    //自动合并单元格
    public static void writeExcelByIndex(HttpServletResponse response, List<?> list,
                                  String fileName, String sheetName, Class model,int[] mergeColumeIndex,int mergeRowIndex) {
        try {
            //表头样式
            WriteCellStyle headWriteCellStyle = new WriteCellStyle();
            //设置表头居中对齐
            headWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
            //内容样式
            WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
            //设置内容靠左对齐
            contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
//            HorizontalCellStyleStrategy horizontalCellStyleStrategy = new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
            EasyExcel.write(getOutputStream(fileName, response), model)
                    .excelType(ExcelTypeEnum.XLSX)
                    .sheet(sheetName)
                    .registerWriteHandler(new ExcelFillCellMergeStrategy(mergeRowIndex, mergeColumeIndex))
                    .registerWriteHandler(new ExcelWidthStyleStrategy())
                    .doWrite(list)
                    ;
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("excel导出失败");
        }
    }
    /**
     * 导出,sheet和fileName相同
     * */
    public static void writeExcelWithSameName(HttpServletResponse response, List<?> list,
                                              String name, Class model) {
        String date = DateUtil.format(new Date(), "yyyyMMddHHmmss");
        String fileName = name + date;
        writeExcel(response, list, fileName, name, model);
    }
    /**
     * 单sheet导出
     *
     * @param response  HttpServletResponse
     * @param list      数据 list
     * @param fileName  导出的文件名
     * @param sheetName 导入文件的 sheet 名
     * @param head      表头
     */
    public static void writeHeadExcel(HttpServletResponse response, List<?> list,
                                  String fileName, String sheetName,List<List<String>> head) {
        try {
            //表头样式
            WriteCellStyle headWriteCellStyle = new WriteCellStyle();
            //设置表头居中对齐
            headWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
            //内容样式
            WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
            //设置内容靠左对齐
            contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.LEFT);
            HorizontalCellStyleStrategy horizontalCellStyleStrategy = new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
            EasyExcel.write(getOutputStream(fileName, response))
                    .excelType(ExcelTypeEnum.XLSX)
                    .sheet(sheetName)
                    .head(head)
                    .registerWriteHandler(horizontalCellStyleStrategy)
                    .doWrite(list);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("excel导出失败");
        }
    }

    /**
     * 导出文件时为Writer生成OutputStream
     *
     * @param fileName
     * @param response
     * @return
     */
    public static OutputStream getOutputStream(String fileName, HttpServletResponse response) {
        try {
            fileName = URLEncoder.encode(fileName, "UTF-8");
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf8");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".xlsx");
            response.setHeader("Pragma", "public");
            response.setHeader("Cache-Control", "no-store");
            response.addHeader("Cache-Control", "max-age=0");
            return response.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("创建头文件失败");
        }
        return null;
    }


    /**
     * 导入：同步读，单sheet
     *  @param file excel文件
     * @param t    excel导入的model对象*/
    public static <T> List<T> importData(MultipartFile file, Class<ApplyPersonExcelDto> t) {
        List<T> userExcelList = null;
        // 1.excel同步读取数据
        try {
            userExcelList = EasyExcel.read(new BufferedInputStream(file.getInputStream()))
                    .head(t)
                    .sheet()
                    .doReadSync();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 2.检查是否大于1000条
        if (userExcelList.size() > MAX_USER_IMPORT) {
        }
        return userExcelList;
    }



}


