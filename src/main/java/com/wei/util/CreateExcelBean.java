package com.wei.util;

import org.apache.poi.ss.usermodel.CellType;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName CreateExcelBean
 * 描述 : 创建多sheetExcel
 * @Author weijunjie
 * @Date 2020/2/5 14:06
 */
@SuppressWarnings("all")
public class CreateExcelBean {

    public String sheetName;

    public LinkedHashMap<String,String> sheetTitle;

    public List<Map<String,Object>> dataList;

    public Map<String,Integer> widthMap;

    public Map<String,CellType> typeMap;

    public Map<String, CellType> getTypeMap() {
        return typeMap;
    }

    public void setTypeMap(Map<String, CellType> typeMap) {
        this.typeMap = typeMap;
    }

    public Map<String, Integer> getWidthMap() {
        return widthMap;
    }

    public void setWidthMap(Map<String, Integer> widthMap) {
        this.widthMap = widthMap;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public LinkedHashMap<String, String> getSheetTitle() {
        return sheetTitle;
    }

    public void setSheetTitle(LinkedHashMap<String, String> sheetTitle) {
        this.sheetTitle = sheetTitle;
    }

    public List<Map<String, Object>> getDataList() {
        return dataList;
    }

    public void setDataList(List<Map<String, Object>> dataList) {
        this.dataList = dataList;
    }

    public CreateExcelBean(String sheetName, LinkedHashMap<String, String> sheetTitle, List<Map<String, Object>> dataList) {
        this.sheetName = sheetName;
        this.sheetTitle = sheetTitle;
        this.dataList = dataList;
    }

    public CreateExcelBean(String sheetName, LinkedHashMap<String, String> sheetTitle, List<Map<String, Object>> dataList, Map<String, CellType> typeMap) {
        this.sheetName = sheetName;
        this.sheetTitle = sheetTitle;
        this.dataList = dataList;
        this.typeMap = typeMap;
    }

    public CreateExcelBean() {
    }
}
