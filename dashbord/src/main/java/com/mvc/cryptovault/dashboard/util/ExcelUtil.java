package com.mvc.cryptovault.dashboard.util;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Number;
import jxl.write.*;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExcelUtil {

    /**
     * @param list      数据源
     * @param fieldMap  类的英文属性和Excel中的中文列名的对应关系
     *                  如果需要的是引用对象的属性，则英文属性使用类似于EL表达式的格式
     *                  如：list中存放的都是student，student中又有college属性，而我们需要学院名称，则可以这样写
     *                  fieldMap.put("college.collegeName","学院名称")
     * @param sheetName 工作表的名称
     * @param sheetSize 每个工作表中记录的最大个数
     * @param out       导出流
     * @throws ExcelException
     * @MethodName : listToExcel
     * @Description : 导出Excel（可以导出到本地文件系统，也可以导出到浏览器，可自定义工作表大小）
     */
    public static <T> void listToExcel(
            List<T> list,
            LinkedHashMap<String, String> fieldMap,
            String sheetName,
            int sheetSize,
            OutputStream out
    ) throws ExcelException {


        if (list.size() == 0 || list == null) {
            throw new ExcelException("数据源中没有任何数据");
        }

        if (sheetSize > 65535 || sheetSize < 1) {
            sheetSize = 65535;
        }

        //创建工作簿并发送到OutputStream指定的地方  
        WritableWorkbook wwb;
        try {
            wwb = Workbook.createWorkbook(out);

            //因为2003的Excel一个工作表最多可以有65536条记录，除去列头剩下65535条  
            //所以如果记录太多，需要放到多个工作表中，其实就是个分页的过程  
            //1.计算一共有多少个工作表  
            double sheetNum = Math.ceil(list.size() / new Integer(sheetSize).doubleValue());

            //2.创建相应的工作表，并向其中填充数据  
            for (int i = 0; i < sheetNum; i++) {
                //如果只有一个工作表的情况  
                if (1 == sheetNum) {
                    WritableSheet sheet = wwb.createSheet(sheetName, i);
                    fillSheet(sheet, list, fieldMap, 0, list.size() - 1);

                    //有多个工作表的情况
                } else {
                    WritableSheet sheet = wwb.createSheet(sheetName + (i + 1), i);

                    //获取开始索引和结束索引  
                    int firstIndex = i * sheetSize;
                    int lastIndex = (i + 1) * sheetSize - 1 > list.size() - 1 ? list.size() - 1 : (i + 1) * sheetSize - 1;
                    //填充工作表  
                    fillSheet(sheet, list, fieldMap, firstIndex, lastIndex);
                }
            }

            wwb.write();
            wwb.close();

        } catch (Exception e) {
            e.printStackTrace();
            //如果是ExcelException，则直接抛出  
            if (e instanceof ExcelException) {
                throw (ExcelException) e;

                //否则将其它异常包装成ExcelException再抛出
            } else {
                throw new ExcelException("导出Excel失败");
            }
        }

    }

    /**
     * @param list     数据源
     * @param fieldMap 类的英文属性和Excel中的中文列名的对应关系
     * @param out      导出流
     * @throws ExcelException
     * @MethodName : listToExcel
     * @Description : 导出Excel（可以导出到本地文件系统，也可以导出到浏览器，工作表大小为2003支持的最大值）
     */
    public static <T> void listToExcel(
            List<T> list,
            LinkedHashMap<String, String> fieldMap,
            String sheetName,
            OutputStream out
    ) throws ExcelException {

        listToExcel(list, fieldMap, sheetName, 65535, out);

    }


    /**
     * @param list      数据源
     * @param fieldMap  类的英文属性和Excel中的中文列名的对应关系
     * @param sheetSize 每个工作表中记录的最大个数
     * @param response  使用response可以导出到浏览器
     * @throws ExcelException
     * @MethodName : listToExcel
     * @Description : 导出Excel（导出到浏览器，可以自定义工作表的大小）
     */
    public static <T> void listToExcel(
            List<T> list,
            LinkedHashMap<String, String> fieldMap,
            String sheetName,
            int sheetSize,
            HttpServletResponse response
    ) throws ExcelException {

        //设置默认文件名为当前时间：年月日时分秒  
        String fileName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()).toString();

        //设置response头信息  
        response.reset();
        response.setContentType("application/vnd.ms-excel");        //改成输出excel文件  
        response.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xls");

        //创建工作簿并发送到浏览器  
        try {

            OutputStream out = response.getOutputStream();
            listToExcel(list, fieldMap, sheetName, sheetSize, out);

        } catch (Exception e) {
            e.printStackTrace();

            //如果是ExcelException，则直接抛出  
            if (e instanceof ExcelException) {
                throw (ExcelException) e;

                //否则将其它异常包装成ExcelException再抛出
            } else {
                throw new ExcelException("导出Excel失败");
            }
        }
    }


    /**
     * @param list     数据源
     * @param fieldMap 类的英文属性和Excel中的中文列名的对应关系
     * @param response 使用response可以导出到浏览器
     * @throws ExcelException
     * @MethodName : listToExcel
     * @Description : 导出Excel（导出到浏览器，工作表的大小是2003支持的最大值）
     */
    public static <T> void listToExcel(
            List<T> list,
            LinkedHashMap<String, String> fieldMap,
            String sheetName,
            HttpServletResponse response
    ) throws ExcelException {

        listToExcel(list, fieldMap, sheetName, 65535, response);
    }

    /**
     * @param in           ：承载着Excel的输入流
     * @param entityClass  ：List中对象的类型（Excel中的每一行都要转化为该类型的对象）
     * @param fieldMap     ：Excel中的中文列头和类的英文属性的对应关系Map
     * @param uniqueFields ：指定业务主键组合（即复合主键），这些列的组合不能重复
     * @return ：List
     * @throws ExcelException
     * @MethodName : excelToList
     * @Description : 将Excel转化为List
     */
    public static <T> List<T> excelToList(
            InputStream in,
            String sheetName,
            Class<T> entityClass,
            LinkedHashMap<String, String> fieldMap,
            String[] uniqueFields
    ) throws ExcelException {

        //定义要返回的list  
        List<T> resultList = new ArrayList<T>();

        try {

            //根据Excel数据源创建WorkBook  
            Workbook wb = Workbook.getWorkbook(in);
            //获取工作表  
            Sheet sheet = wb.getSheet(sheetName);

            //获取工作表的有效行数  
            int realRows = 0;
            for (int i = 0; i < sheet.getRows(); i++) {

                int nullCols = 0;
                for (int j = 0; j < sheet.getColumns(); 