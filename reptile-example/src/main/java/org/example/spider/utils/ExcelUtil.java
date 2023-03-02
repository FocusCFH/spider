package org.example.spider.utils;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.spider.common.result.template.ContentResultForm;
import org.example.spider.utils.model.SheetRowModel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: chenfuhao
 * @Date: 2021/05/26/15:17
 * @Description:
 */
@Slf4j
public class ExcelUtil {


//    // 创建工作簿
//    public static XSSFWorkbook createExcel(FileInputStream fis){
//        try {
//            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(fis);
//            return xssfWorkbook;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

    /**
     *
     * 创建工作簿
     *
     * @param fis
     * @param fileType
     * @return
     *
     */
    public static Workbook createExcel(InputStream fis, String fileType){
        try {
            Workbook workbook = null;
            if ("xlsx".equals(fileType)){
                workbook = new XSSFWorkbook(fis);
            }
            else if("xls".equals(fileType)){
                workbook = new HSSFWorkbook(fis);
            }
            return workbook;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *
     * 获取sheet表格名称列表
     *
     * @param workBook
     * @return
     *
     */
    public static List<SheetTable> getSheet(Workbook workBook){
        try {
            List<SheetTable> list = new ArrayList<>();
            int num = workBook.getNumberOfSheets();
            for (int i = 0; i < num; i++) {
                SheetTable sheetTable = new SheetTable(i, workBook.getSheetName(i));
                list.add(sheetTable);
            }
            return list;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 根据名称获取sheet
     * @param workBook
     * @param sheetName
     * @return
     */
    public static Sheet getSheetByName(Workbook workBook, String sheetName){
        return workBook.getSheet(sheetName);
    }

    /**
     *
     * 根据sheet表格名称读取表格数据，返回每一行的表格数据列表
     *
     * @param workBook
     * @param sheetName
     * @return
     *
     */
    public static Map<String, String> getSheetCellValues(Workbook workBook, String sheetName){
        // key ：行下表-列下表，例如 1-2     value：为单元格的值String类型
        Map<String, String> map = new HashMap<>();
        Sheet sheet = workBook.getSheet(sheetName);
        // 获取总行数
        int rows = getRowsNum(sheet);

        // 获取总列数
        int column = getColumnNum(sheet);
        System.out.println("------行数：" + rows + " ， 列数：" + column);
        // 遍历每一行每一列
        for (int i = 0; i < rows; i ++){
            for (int j = 0 ; j < column; j++){
                String v = "";
                // 获取表格指定行、列的值
                if (isMergedRegion(sheet,i,j)){
                    v = getMergedRegionValue(sheet,i,j);
                }else {
                    v = getCellValueInSheet(sheet,i,j);
                }
                String k = new String(i + "-" + j);
                map.put(k,v);
            }
        }
        return map;
    }

    /**
     *
     * 获取表格指定行、列的值
     *
     * @param sheet
     * @param row
     * @param column
     * @return
     *
     */
    private static String getCellValueInSheet(Sheet sheet, int row, int column) {
        Row row1 = sheet.getRow(row);
        Cell cell = row1.getCell(column);
        return getCellValue(cell);
    }

    /**
     *
     * 获取表格列数
     *
     * @param sheet
     * @return
     *
     */
    public static Integer getColumnNum(Sheet sheet){
        int num = sheet.getRow(0).getPhysicalNumberOfCells();
        return num;
    }

    /**
     *
     * 获取表格行数
     *
     * @param sheet
     * @return
     *
     */
    public static Integer getRowsNum(Sheet sheet){
        int num = sheet.getPhysicalNumberOfRows();//获得总行数
        return num;
    }

    /**
     *
     * 获取表格内容
     *
     * @param cellValues
     * @param row
     * @param column
     * @return
     *
     */
    public static String getCellValueInSheetByRowColumn(Map<String, String> cellValues , int row , int column){
        return cellValues.get(row + "-" + column);
    }

    /**
     *
     * 获取单元格的值
     *
     * @param cell
     * @return
     *
     */
    private static String getCellValue(Cell cell){
        if(cell == null) return "";
        if(cell.getCellTypeEnum() == CellType.STRING){
            return cell.getStringCellValue();
        }else if(cell.getCellTypeEnum() == CellType.BOOLEAN){
            return String.valueOf(cell.getBooleanCellValue());
        }else if(cell.getCellTypeEnum() == CellType.FORMULA){
            return cell.getCellFormula() ;
        }else if(cell.getCellTypeEnum() == CellType.NUMERIC){
            return String.valueOf(cell.getNumericCellValue());
        }
        return "";
    }

    /**
     *
     * 获取合并单元格的值
     *
     * @param sheet
     * @param row
     * @param column
     * @return
     *
     */
    private static String getMergedRegionValue(Sheet sheet , int row , int column){
        int sheetMergeCount = sheet.getNumMergedRegions();

        for(int i = 0 ; i < sheetMergeCount ; i++){
            CellRangeAddress ca = sheet.getMergedRegion(i);
            int firstColumn = ca.getFirstColumn();
            int lastColumn = ca.getLastColumn();
            int firstRow = ca.getFirstRow();
            int lastRow = ca.getLastRow();

            if(row >= firstRow && row <= lastRow){
                if(column >= firstColumn && column <= lastColumn){
                    Row fRow = sheet.getRow(firstRow);
                    Cell fCell = fRow.getCell(firstColumn);
                    return getCellValue(fCell) ;
                }
            }
        }

        return null ;
    }

    /**
     *
     * 判断指定的单元格是否是合并单元格
     *
     * @param sheet
     * @param row 行下标
     * @param column 列下标
     * @return
     *
     */
    private static boolean isMergedRegion(Sheet sheet, int row , int column) {
        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress range = sheet.getMergedRegion(i);
            int firstColumn = range.getFirstColumn();
            int lastColumn = range.getLastColumn();
            int firstRow = range.getFirstRow();
            int lastRow = range.getLastRow();
            if(row >= firstRow && row <= lastRow){
                if(column >= firstColumn && column <= lastColumn){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     *
     * sheet表格下表及名称实体
     *
     */
    public static class  SheetTable {
        private Integer index;

        private String name;

        public SheetTable(Integer index, String name) {
            this.index = index;
            this.name = name;
        }

        public Integer getIndex() {
            return index;
        }

        public void setIndex(Integer index) {
            this.index = index;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    /**
     * 过滤特定需要分析的表格名称
     * @param excelSheetNames
     * @param targetSheetNames
     * @return
     */
    private static List<String> filterShouldAnalysisTableNames(List<String>  excelSheetNames,List<String> targetSheetNames){
        // 目标表格列表为空，excel表格部位空，返回excel表格
        if(CollectionUtils.isEmpty(targetSheetNames) && CollectionUtils.isNotEmpty(excelSheetNames)) return excelSheetNames;
        // 两个集合都为空，返回空集合
        if(CollectionUtils.isEmpty(targetSheetNames) && CollectionUtils.isEmpty(excelSheetNames)
                || CollectionUtils.isNotEmpty(targetSheetNames) && CollectionUtils.isEmpty(excelSheetNames)) return new ArrayList<>();
        return ListUtils.retainAll(excelSheetNames,targetSheetNames);
    }

    public static Map<String,List<SheetRowModel>> readExcelModel(Workbook excel,List<String> sheetNames){
        // 获取excel里所有表格名称
        List<ExcelUtil.SheetTable> sheets = ExcelUtil.getSheet(excel);
        if (CollectionUtils.isEmpty(sheets)) {
            log.info("空白的excel文档！");
            return null;
        }
        // excel表格名称
        List<String> excelSheetNames = sheets.stream().map(ExcelUtil.SheetTable::getName).collect(Collectors.toList());
        // 过滤应该要分析的表格
        List<String> shouldAnalysisTableNames = ExcelUtil.filterShouldAnalysisTableNames(excelSheetNames,sheetNames);
        // 表格数据Map， key： 表格名称；value：每一行数据
        Map<String,List<SheetRowModel>> sheetModelsMap = new HashMap<>();
        // 遍历所有表格
        for (String sheetName : shouldAnalysisTableNames){
            Sheet sheet = ExcelUtil.getSheetByName(excel, sheetName);
            Map<String, String> sheetCellValues = ExcelUtil.getSheetCellValues(excel, sheetName);
            Integer rowsNum = ExcelUtil.getRowsNum(sheet);
            Integer columnNum = ExcelUtil.getColumnNum(sheet);
            // 获取表头中每一列的名称与其列标
            Integer tableTittleRowNum = 0; // 表头所在的行
            Map<Integer,String> colIndexMap = new HashMap<>();
            for (int col =0; col < columnNum; col ++) { // 列
                String colName = sheetCellValues.get(tableTittleRowNum + "-" + col);
                colIndexMap.put(col,colName);
            }
            // 获取数据
            Set<Integer> colKeys = colIndexMap.keySet();
            List<SheetRowModel> rowModels = new ArrayList<>();
            // 第三行开始取数
            Integer sort = 1;
            for (int row = 2; row < rowsNum; row ++ ) {

                SheetRowModel sheetRowModel = new SheetRowModel();
                boolean isValidRow = true;
                // 遍历每一列
                for (Integer key : colKeys){
                    String colValue = sheetCellValues.get(row + "-" + key);
                    // 第二列开始取数
                    if (key == 0) {
                       continue;
                    }
                    else if (key == 1){
                        // 如果能源种类为空，则忽略这行数据
                        if (StringUtils.isBlank(colValue)) {
                            isValidRow = false;
                            break;
                        }
                        sheetRowModel.setEnergyType(colValue);
                    }
                    else if (key == 2){
                        sheetRowModel.setEmissionFactorValue(colValue);
                    }
                    else if (key == 3){
                        sheetRowModel.setUnit(colValue);
                    }
                    else if (key == 4){
                        sheetRowModel.setMaterialUsage(colValue);
                    }
                    else if (key == 5){
                        sheetRowModel.setRemark(colValue);
                    }
                }
                // 如果是有效行
                if (isValidRow) {
                    sheetRowModel.setSort(sort);
                    rowModels.add(sheetRowModel);
                    sort = sort + 1;
                }
            }
            sheetModelsMap.put(sheetName,rowModels);
        }
        return sheetModelsMap;
    }

    public  static void main(String[] args) throws Exception {
        List<String> buildingStageTableName = Arrays.asList(new String[]{"混凝土加工消耗能源统计"
                ,"装配式建筑预制构件生产加工消耗能源统计"
                ,"建筑建造阶段施工人员工作、生活消耗能源汇总","施工设备消耗能源统计"});
//        FileInputStream fis = new FileInputStream("C:\\Users\\陈富豪\\Desktop\\第二阶段审查数据(参考).xlsx");
//        Workbook excel = ExcelUtil.createExcel(fis,".xlsx");
//        List<SheetTable> sheet = getSheet(excel);
//        System.out.println("表格下表及表格名称：" + JSON.toJSONString(sheet));
//        Map<String, String> cellValues = getSheetCellValues(excel, "建筑单体模型数据");
//        System.out.println("所有单元格的值：" + JSON.toJSONString(cellValues));

        String filePath = "C:\\Users\\陈富豪\\Desktop\\碳排放汇总资料合集\\建筑建造阶段能源消耗汇总表.xlsx";
        try {
            InputStream workbookIn = new FileInputStream(filePath);
            Workbook excel = ExcelUtil.createExcel(workbookIn,"xlsx");
//            Map<String, String> cellValues = getSheetCellValues(excel, "建筑拆除阶段消耗能源汇总");
            Map<String, List<SheetRowModel>> analysisExcelModels = readExcelModel(excel, buildingStageTableName);
            System.out.println("所有单元格的值：" + JSON.toJSONString(analysisExcelModels));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

}
