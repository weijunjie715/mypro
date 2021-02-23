package com.wei.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName PublicCreateExcelUtils
 * 描述 : 公共创建excel方法
 * @Author weijunjie
 * @Date 2020/2/5 13:30
 */
@SuppressWarnings("all")
public class PublicCreateExcelUtils {

    /**
     * @Description excel列序固定，与标题插入顺序一致
     * @Author weijunjie
     * @Date 2020/2/5 13:59
     **/
    public static JSONObject createOrderTitle(LinkedHashMap<String,String> titleMap){
        JSONObject jsonObject = new JSONObject(true);
        for(Map.Entry<String,String> entry : titleMap.entrySet()){
            jsonObject.put(entry.getKey(),entry.getValue());
        }
        return jsonObject;
    }

    /**
     * @Description excel数据取值
     * @Author weijunjie
     * @Date 2020/2/5 13:59
     **/
    public static List<JSONObject> createDataList(List<Map<String,Object>> dataList){
        List<JSONObject> objects = new ArrayList<>();
        if(null == dataList){
            return objects;
        }
        for(Map map:dataList){
            JSONObject theMap = JSONObject.parseObject(JSONObject.toJSONString(map));
            objects.add(theMap);
        }
        return objects;
    }

    /**
     * @Description 创建workbook (map)
     * @Author weijunjie
     * @Date 2020/2/5 13:31
     **/
    public static Workbook createWorkbook(LinkedHashMap<String,String> titleMap,
                                          Map<String,Integer> widthMap,
                                          List<Map<String,Object>> dataList,
                                          String sheetName){
        JSONObject jsonObject = createOrderTitle(titleMap);
        List<JSONObject> objects = new ArrayList<>();
        for(Map map:dataList){
            JSONObject theMap = JSONObject.parseObject(JSONObject.toJSONString(map));
            objects.add(theMap);
        }
        JSONObject widthObj = JSONObject.parseObject(JSONObject.toJSONString(widthMap));
        return createWorkbook(jsonObject,widthObj,objects,sheetName);
    }

    /**
     * @Description 传入数据组装workbook
     * @Author weijunjie
     * @Date 2020/1/20 9:04
     **/
    public static Workbook createWorkbook(JSONObject titleMap, JSONObject widthMap, List<JSONObject> dataList, String sheetName){
        SXSSFWorkbook workbook = new SXSSFWorkbook();
        SXSSFSheet sheet = workbook.createSheet(sheetName);
        boolean widthFlag = true;
        if(null == widthMap){
            widthFlag = false;
        }
        //首行写入title数据，获取对应的key值
        int z = 0;
        SXSSFRow titleRow = sheet.createRow(0);
        for(String key:titleMap.keySet()){
            SXSSFCell cell = titleRow.createCell(z);
            String val = titleMap.getString(key);
            if(!widthFlag){
                sheet.setColumnWidth(z, (val.length()+1) * 2 * 256);
            }else{
                int width = widthMap.getInteger(key);
                sheet.setColumnWidth(z, width);
            }
            cell.setCellValue(val);
            z++;
        }
        for(int i = 1;i<=dataList.size();i++){
            SXSSFRow row = sheet.createRow(i);
            //按照插入顺序获取对应的所有数据信息
            JSONObject data = dataList.get(i-1);
            int j = 0;
            for(String key:titleMap.keySet()){
                SXSSFCell cell = row.createCell(j);
                String val = data.getString(key);
                cell.setCellValue(val);
                j++;
            }
        }
        return workbook;
    }

    /**---------------------------------多sheet-------------------------------**/

    /**
     * @Description 创建多sheetWorkbook
     * @Author weijunjie
     * @Date 2020/2/5 14:10
     **/
    public static Workbook createWorkbook(List<CreateExcelBean> createExcelBeans){
        SXSSFWorkbook workbook = new SXSSFWorkbook();
        int sheetIndex = 0;
        for(CreateExcelBean createExcelBean:createExcelBeans){
            JSONObject titleMap = createOrderTitle(createExcelBean.sheetTitle);
            List<JSONObject> dataList = createDataList(createExcelBean.dataList);
            String sheetName = StringUtils.isNotBlank(createExcelBean.getSheetName())
                    ?createExcelBean.getSheetName():"未命名"+sheetIndex;
            sheetIndex++;
            SXSSFSheet sheet = workbook.createSheet(sheetName);
            JSONObject widthObj = new JSONObject();
//            JSONObject typeObj = new JSONObject();
            Map<String,Integer> widthMap = createExcelBean.getWidthMap();
            Map<String,CellType> typeMap = createExcelBean.getTypeMap();
            //判断是否指定对应列的宽度数据（没有指定默认  根据列标题的长度自适应）
            boolean widthFlag = true;
            if(null == widthMap){
                widthFlag = false;
            }else{
                widthObj = JSONObject.parseObject(JSONObject.toJSONString(widthMap));
            }
            //判断是否指定对应列的宽度数据（没有指定默认  根据列标题的长度自适应）
            boolean typeFlag = true;
            if(null == typeMap){
                typeFlag = false;
            }
            //首行写入title数据，获取对应的key值
            int z = 0;
            SXSSFRow titleRow = sheet.createRow(0);
            for(String key:titleMap.keySet()){
                SXSSFCell cell = titleRow.createCell(z);
                String val = titleMap.getString(key);
                if(!widthFlag){
                    sheet.setColumnWidth(z, (val.length()+1) * 2 * 256);
                }else{
                    int width = widthObj.getInteger(key);
                    sheet.setColumnWidth(z, width);
                }
                if(!typeFlag){
                    cell.setCellType(CellType.STRING);
                }else{
                    CellType cType = typeMap.get(key);
//                    int cellType = typeObj.getInteger(key);
                    cell.setCellType(cType);
                }
                cell.setCellValue(val);
                z++;
            }
            for(int i = 1;i<=dataList.size();i++){
                SXSSFRow row = sheet.createRow(i);
                //按照插入顺序获取对应的所有数据信息
                JSONObject data = dataList.get(i-1);
                int j = 0;
                for(String key:titleMap.keySet()){
                    SXSSFCell cell = row.createCell(j);
                    String val = data.getString(key);
                    cell.setCellValue(val);
                    j++;
                }
            }
        }
        return workbook;
    }

    /**
     * @Description 获取一种颜色的单元格格式
     * @Author weijunjie
     * @Date 2020/3/17 16:20
     **/
    public static CellStyle getOnStyle(Workbook workbook,short var1){
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFillForegroundColor(var1);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderBottom(BorderStyle.THIN); //下边框
        style.setBorderLeft(BorderStyle.THIN);//左边框
        style.setBorderTop(BorderStyle.THIN);//上边框
        style.setBorderRight(BorderStyle.THIN);//右边框

        return style;
    }

}
