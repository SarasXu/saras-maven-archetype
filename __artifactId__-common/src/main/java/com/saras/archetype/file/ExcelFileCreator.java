package com.saras.archetype.file;

import com.saras.archetype.utils.CollectionUtils;
import com.saras.archetype.utils.DateUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.mockito.internal.util.collections.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

/**
 * excel文件 创建
 * Created by zhanghao on 2017/9/13.
 */
@Component
public class ExcelFileCreator {

    /**
     * 特例空文件对象
     */
    public static final ExcelFileCreator EMPTY_FILE = new ExcelFileCreator();
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private Set<Class> numClassTypes = Sets.newSet(int.class, Integer.class,
            Long.class, long.class, double.class, Double.class, float.class, Float.class, Number.class,
            BigDecimal.class);

    /**
     * 默认表格数为1
     */
    private Integer defaultSheetNum = 1;
    /**
     * 默认操作的表格为0
     */
    private Integer defaultSheetIdx = 0;

    private HSSFWorkbook workbook;
    /**
     * 表格 数
     */
    private Integer sheetNum;
    /**
     * 表格
     */
    private Integer sheetIdx;

    public ExcelFileCreator(Integer sheetNum, Integer sheetIdx) {
        workbook = new HSSFWorkbook();
        this.sheetNum = sheetNum;
        this.sheetIdx = sheetIdx;
        if (sheetNum <= 0 || sheetIdx < 0 || sheetIdx >= sheetNum) {
            throw new IllegalArgumentException("sheetIdx out of bound or headers are blank");
        }
    }

    public ExcelFileCreator(Integer sheetNum) {
        workbook = new HSSFWorkbook();
        this.sheetNum = sheetNum;
        this.sheetIdx = this.sheetIdx == null || this.sheetIdx.compareTo(0) != 1
                ? this.sheetIdx = this.defaultSheetIdx
                : this.sheetIdx;
    }

    public ExcelFileCreator() {
        workbook = new HSSFWorkbook();
        this.sheetNum = this.sheetNum == null || this.sheetNum.compareTo(0) != 1
                ? this.sheetNum = this.defaultSheetNum
                : this.sheetNum;
        this.sheetIdx = this.sheetIdx == null || this.sheetIdx.compareTo(0) != 1
                ? this.sheetIdx = this.defaultSheetIdx
                : this.sheetIdx;
    }

    public Integer getSheetIdx() {
        return sheetIdx;
    }

    /**
     * 操作的表格id 开放出去
     */
    public void setSheetIdx(Integer sheetIdx) {
        this.sheetIdx = sheetIdx;
    }

    /**
     * 设置表头标题栏
     */
    public void setHeader(Integer sheetIdx, List<String> headers) {
        if (sheetIdx >= this.sheetNum || sheetIdx < 0 || !CollectionUtils.isNotEmpty(headers)) {
            throw new IllegalArgumentException("sheetIdx out of bound or headers are blank");
        }
        for (int i = 0; i < headers.size(); ++i) {
            getCell(sheetIdx, 0, i).setCellValue(headers.get(i));
        }
    }

    /**
     * 设置表头标题栏
     */
    public void setHeader(List<String> headers) {
        if (!CollectionUtils.isNotEmpty(headers)) {
            throw new IllegalArgumentException("excel headers are blank");
        }
        for (int i = 0; i < headers.size(); ++i) {
            getCell(sheetIdx, 0, i).setCellValue(headers.get(i));
        }
    }

    /**
     * 设置表头标题栏
     */
    public void setHeader(TreeMap<Integer, String> headers) {
        if (headers == null || !CollectionUtils.isNotEmpty(headers.values())) {
            throw new IllegalArgumentException("excel headers are blank");
        }
        for (Integer key : headers.keySet()) {
            getCell(sheetIdx, 0, key).setCellValue(headers.get(key));
        }

    }

    public HSSFCell getCell(Integer sheetIdx, Integer rowNum, Integer colNum) {
        if (sheetIdx >= this.sheetNum || sheetIdx < 0 || rowNum < 0 || colNum < 0) {
            throw new IllegalArgumentException("sheetIdx, rowNum or colNum out of bound");
        }
        HSSFRow row = getRow(sheetIdx, rowNum);
        HSSFCell curCell = row.getCell(colNum);
        if (curCell == null) {
            return row.createCell(colNum);
        } else {
            return curCell;
        }
    }

    public HSSFCell getCell(Integer rowNum, Integer colNum) {
        HSSFRow row = getRow(sheetIdx, rowNum);
        HSSFCell curCell = row.getCell(colNum);
        if (curCell == null) {
            return row.createCell(colNum);
        } else {
            return curCell;
        }
    }

    /**
     * 设置值
     *
     * @param value
     * @param cell
     */
    public void setValue(Object value, HSSFCell cell, Boolean asString) {
        if (value == null || cell == null) {
            logger.info("参数中有Null，不处理，{}，{}", value, cell);
            return;
        }

        Class clazz = value.getClass();

        if (asString) {
            clazz = String.class;
        }

        if (value instanceof Number) {
            cell.setCellValue(Long.valueOf(String.valueOf(value)));
            setType(cell, clazz);
        } else if (value instanceof Number) {
        } else if (value instanceof Boolean) {
            cell.setCellValue(Boolean.valueOf(String.valueOf(value)));
            setType(cell, clazz);
        } else if (value instanceof Date) {
            String valueDateStr = DateUtils.simpleFormat((Date) value);
            cell.setCellValue(valueDateStr);
            setType(cell, String.class);
        } else if (value instanceof Timestamp) {
            long valueDateLong = ((Timestamp) value).getTime();
            Date valueDate = new Date(valueDateLong);
            String valueStr = DateUtils.simpleFormat(valueDate);
            cell.setCellValue(valueStr);
            setType(cell, String.class);
        } else if (value instanceof String) {
            cell.setCellValue((String) value);
            setType(cell, String.class);
        } else {
            cell.setCellValue(value.toString());
            setType(cell, String.class);
        }

    }

    /**
     * 输出流
     *
     * @param os
     * @throws IOException
     */
    public void write(OutputStream os) throws IOException {
        workbook.write(os);
    }

    /**
     * 设置类型
     *
     * @param cell
     * @param clazz
     */
    private void setType(HSSFCell cell, Class<?> clazz) {

        if (cell == null) {
            throw new IllegalArgumentException("cell is null");
        }

        /** 如果为空则默认为String 类型 */
        if (clazz == null) {
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            return;
        }

        /** 数字类型 */
        if (numClassTypes.contains(clazz)) {
            cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
            return;
        }
        /** boolean 类型 */
        else if (clazz == Boolean.class || clazz == boolean.class) {
            cell.setCellType(HSSFCell.CELL_TYPE_BOOLEAN);
            return;
        } else {
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            return;
        }

    }

    private HSSFRow getRow(Integer sheetIdx, Integer rowNum) {
        if (sheetIdx >= this.sheetNum || sheetIdx < 0 || rowNum < 0) {
            throw new IllegalArgumentException("sheetIdx or row out of bound");
        }
        HSSFSheet curSheet = getSheet(sheetIdx);
        HSSFRow row = curSheet.getRow(rowNum);
        if (row == null) {
            return curSheet.createRow(rowNum);
        } else {
            return row;
        }
    }

    private HSSFRow getRow(Integer rowNum) {
        if (this.sheetIdx >= this.sheetNum || this.sheetIdx < 0 || rowNum < 0) {
            throw new IllegalArgumentException("sheetIdx or row out of bound");
        }
        HSSFSheet curSheet = getSheet();
        HSSFRow row = curSheet.getRow(rowNum);
        if (row == null) {
            return curSheet.createRow(rowNum);
        } else {
            return row;
        }
    }

    private HSSFSheet getSheet(int sheetIdx) {
        if (sheetIdx >= this.sheetNum || sheetIdx < 0) {
            throw new IllegalArgumentException("sheetIdx out of bound");
        }
        if (this.workbook.getNumberOfSheets() < this.sheetNum) {
            return workbook.createSheet();
        } else {
            return workbook.getSheetAt(sheetIdx);
        }
    }

    private HSSFSheet getSheet() {
        if (this.sheetIdx >= this.sheetNum || this.sheetIdx < 0) {
            throw new IllegalArgumentException("sheetIdx out of bound");
        }
        if (this.workbook.getNumberOfSheets() < this.sheetNum) {
            return workbook.createSheet();
        } else {
            return workbook.getSheetAt(this.sheetIdx);
        }
    }

}
