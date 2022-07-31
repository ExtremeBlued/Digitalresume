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
     *       