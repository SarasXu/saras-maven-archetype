package com.saras.archetype.utils;

import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.UnderlineStyle;
import jxl.write.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExcelUtil {

    public static String ExcelPrintInfo = "操作人：%s        导出日期：%s";
    private final Log log = LogFactory.getLog(getClass());

    /**
     * 导出EXCEL公共类
     *
     * @param res          HttpServletResponse
     * @param request      HttpServletRequest
     * @param dataDetails  需要导出的数据集
     * @param sheetName    sheet名称
     * @param columnsKey   导出字段key
     * @param columnsValue 导出字段中文名称
     * @param excelName    导出excel名称
     * @param userName     导出用户名称
     * @param <T>
     */
    public static <T> void utilExportToExcel(HttpServletResponse res, HttpServletRequest request,
                                             List<T> dataDetails, String sheetName, List<String> columnsKey, List<String> columnsValue,
                                             String excelName, String userName) {
        int[] excludeLockecIndexs = {};
        // 锁定列不能为空且不能大于总列数
        if (null == excludeLockecIndexs) {
            excludeLockecIndexs = new int[]{};
        }
        Set<Integer> indexs = new HashSet<Integer>();
        for (int i : excludeLockecIndexs) {
            indexs.add(i);
        }


        OutputStream os = null;
        try {
            os = res.getOutputStream(); // 取得输出流
            res.reset(); // 清空输出流
            // res.setHeader("Content-disposition", "attachment; filename="
            // + excelName + ".xls"); //设定输出文件头
            res.setHeader("Content-disposition",
                    "attachment; filename=" + new String((excelName + ".xls").getBytes("GB2312"), "ISO8859-1"));
            res.setContentType("application/msexcel"); // 定义输出类型
        } catch (IOException ex) {
            System.out.println("流操作错误:" + ex.getMessage());
        }
        WritableWorkbook book = null;
        try {
            // 打开文件
            book = Workbook.createWorkbook(os);
            WritableSheet sheet = book.createSheet(sheetName, 0);
            if (!indexs.isEmpty()) {
                // 设置保护，并加密码 锁定的Cell才会起作用
                sheet.getSettings().setProtected(true); // 启用保护
                sheet.getSettings().setPassword("dph");
            }
            /** ************设置单元格字体************** */
            WritableFont normalFont = new WritableFont(WritableFont.ARIAL, 10);
            WritableFont boldFont = new WritableFont(WritableFont.ARIAL, 16, WritableFont.BOLD);
            // 用于标题居中
            WritableCellFormat wcfCenter = new WritableCellFormat(boldFont);
            wcfCenter.setBorder(Border.ALL, BorderLineStyle.NONE); // 线条
            wcfCenter.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐
            wcfCenter.setAlignment(Alignment.CENTRE); // 文字水平对齐
            wcfCenter.setWrap(false); // 文字是否换行
            // 用户打印信息显示
            WritableCellFormat user_info = new WritableCellFormat(normalFont);
            user_info.setBorder(Border.ALL, BorderLineStyle.NONE); // 线条
            user_info.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐
            user_info.setAlignment(Alignment.LEFT); // 文字水平对齐
            user_info.setWrap(false); // 文字是否换行

            /** ***************是EXCEL开头大标题********************* */
            sheet.mergeCells(0, 0, columnsValue.size() - 1, 0);
            sheet.addCell(new Label(0, 0, excelName, wcfCenter));
            /** ***************是EXCEL打印信息********************* */
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            // 获取当前日期
            String dateString = formatter.format(new Date());
            sheet.mergeCells(0, 1, columnsValue.size() - 1, 0);
            sheet.addCell(new Label(0, 1, String.format(ExcelPrintInfo, userName, dateString)));
            // 设置每一列的宽度.第一个参数是列,第二个是多宽按厘米记,
            for (int i = 0; i < columnsKey.size(); i++) {
                sheet.setColumnView(i, 30);// 设置列的宽度
            }

            // 设置字体
            WritableFont font = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD, false,
                    UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);
            // 设置非锁定单元格样式
            WritableCellFormat format = new WritableCellFormat(font);
            format.setBorder(Border.ALL, BorderLineStyle.THIN); // 线条
            format.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐
            format.setAlignment(Alignment.CENTRE); // 文字水平对齐
            format.setWrap(false); // 文字是否换行
            format.setLocked(true);
            for (int i = 0; i < columnsValue.size(); i++) {
                Label wlabel1 = new Label(i, 2, columnsValue.get(i), format); // 列、行、单元格中的文本、文本格式
                sheet.addCell(wlabel1);
            }
            font = new WritableFont(WritableFont.createFont("宋体"), 12, WritableFont.NO_BOLD, false,
                    UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);
            format = new WritableCellFormat(font);
            format.setBorder(Border.ALL, BorderLineStyle.THIN); // 线条
            format.setLocked(false);
            // 设置锁定的单元格样式
            WritableCellFormat lockedformat = new WritableCellFormat(font);
            lockedformat.setBorder(Border.ALL, BorderLineStyle.THIN); // 线条
            lockedformat.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐
            lockedformat.setAlignment(Alignment.CENTRE); // 文字水平对齐
            lockedformat.setWrap(false); // 文字是否换行
            lockedformat.setLocked(true);
            lockedformat.setBorder(Border.ALL, BorderLineStyle.THIN); // 线条

            if (dataDetails != null && !dataDetails.isEmpty()) {
                for (int i = 1; i <= dataDetails.size(); i++) {
                    for (int j = 0; j <= columnsKey.size() - 1; j++) {
                        String cellText = String.valueOf(convertBean(dataDetails.get(i - 1)).get(columnsKey.get(j)));
                        if (cellText == null || "null".equals(cellText)) { // 防止为空出现NULL的值
                            cellText = "";
                        }
                        // 不为空表示指定的排除的单元格要锁定
                        if (!indexs.isEmpty()) {
                            if (indexs.contains(j + 1)) {
                                // 不为空表示指定的排除的单元格要锁定
                                sheet.addCell(new Label(j, i + 2, cellText, format));
                            } else {
                                // 不为空表示指定的排除的单元格要锁定
                                sheet.addCell(new Label(j, i + 2, cellText, lockedformat));
                            }
                        } else {
                            // 都不用锁定
                            sheet.addCell(new Label(j, i + 2, cellText));
                        }
                    }
                }
            }
            // 写入数据并关闭文件
            book.write();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (book != null) {
                try {
                    book.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static Map convertBean(Object bean)
            throws IntrospectionException, IllegalAccessException, InvocationTargetException {
        Class type = bean.getClass();
        Map returnMap = new HashMap();
        BeanInfo beanInfo = Introspector.getBeanInfo(type);

        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (int i = 0; i < propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            if (!"class".equals(propertyName)) {
                Method readMethod = descriptor.getReadMethod();
                Object result = readMethod.invoke(bean);
                if (result != null) {
                    returnMap.put(propertyName, result);
                } else {
                    returnMap.put(propertyName, "");
                }
            }
        }
        if ("false".equals(String.valueOf(returnMap.get("empty")))) {
            return (Map) bean;
        }
        return returnMap;
    }
}
