package com.bbin.utils;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.bbin.common.exception.ExceptionCast;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 *
 * @Author yuanhaoyue swithaoy@gmail.com
 * @Description 工具类
 * @Date 2018-06-06
 * @Time 14:07
 */
public class ExcelUtil {
    static Logger  log = LoggerFactory.getLogger(ExcelUtil.class);
    /**
     * 读取 Excel(多个 sheet)
     * @param excel    文件
     * @param rowModel 实体类映射，继承 BaseRowModel 类
     * @return Excel 数据 list
     */
    public static List<Object> readExcel(MultipartFile excel, BaseRowModel rowModel) {
        ExcelListener excelListener = new ExcelListener();
        ExcelReader reader = getReader(excel, excelListener);
        if (reader == null) {
            return null;
        }
        for (Sheet sheet : reader.getSheets()) {
            if (rowModel != null) {
                sheet.setClazz(rowModel.getClass());
            }
            reader.read(sheet);
        }
        return excelListener.getDatas();
    }

    /**
     * 读取某个 sheet 的 Excel
     * @param excel    文件
     * @param rowModel 实体类映射，继承 BaseRowModel 类
     * @param sheetNo  sheet 的序号 从1开始
     * @return Excel 数据 list
     */
    public static List<Object> readExcel(MultipartFile excel, BaseRowModel rowModel, int sheetNo) {
        return readExcel(excel, rowModel, sheetNo, 1);
    }

    /**
     * 读取某个 sheet 的 Excel
     * @param excel       文件
     * @param rowModel    实体类映射，继承 BaseRowModel 类
     * @param sheetNo     sheet 的序号 从1开始
     * @param headLineNum 表头行数，默认为1
     * @return Excel 数据 list
     */
    public static List<Object> readExcel(MultipartFile excel, BaseRowModel rowModel, int sheetNo,
                                         int headLineNum) {
        ExcelListener excelListener = new ExcelListener();
        ExcelReader reader = getReader(excel, excelListener);
        if (reader == null) {
            return null;
        }
        reader.read(new Sheet(sheetNo, headLineNum, rowModel.getClass()));
        return excelListener.getDatas();
    }



    /**
     * 导出 Excel ：一个 sheet，带表头
     * @param response  HttpServletResponse
     * @param list      数据 list，每个元素为一个 BaseRowModel
     * @param fileName  导出的文件名
     * @param sheetName 导入文件的 sheet 名
     * @param object    映射实体类，Excel 模型
     */
    public static void writeExcel(HttpServletResponse response, List<? extends BaseRowModel> list,
                                  String fileName, String sheetName, BaseRowModel object) {
        ExcelWriter writer = new ExcelWriter(getOutputStream(fileName, response), ExcelTypeEnum.XLSX);
        Sheet sheet = new Sheet(1, 0, object.getClass());
        sheet.setSheetName(sheetName);
        writer.write(list, sheet);
        writer.finish();
    }

    /**
     * 导出 Excel ：多个 sheet，带表头
     * @param response  HttpServletResponse
     * @param list      数据 list，每个元素为一个 BaseRowModel
     * @param fileName  导出的文件名
     * @param sheetName 导入文件的 sheet 名
     * @param object    映射实体类，Excel 模型
     */
    public static ExcelWriterFactroy writeExcelWithSheets(HttpServletResponse response, List<? extends BaseRowModel> list,
                                                          String fileName, String sheetName, BaseRowModel object) {
        ExcelWriterFactroy writer = new ExcelWriterFactroy(getOutputStream(fileName, response), ExcelTypeEnum.XLSX);
        Sheet sheet = new Sheet(1, 0, object.getClass());
        sheet.setSheetName(sheetName);
        writer.write(list, sheet);
        return writer;
    }

    /**
     * 导出文件时为Writer生成OutputStream
     */
    private static OutputStream getOutputStream(String fileName, HttpServletResponse response) {
        try {
            fileName = new String((fileName + ".xlsx").getBytes(), "ISO-8859-1");
            response.addHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
            return response.getOutputStream();
        } catch (IOException e) {
            ExceptionCast.castFailMes("创建Excel文件失败");
        }
        return null;
    }

    /**
     * 返回 ExcelReader
     * @param excel         需要解析的 Excel 文件
     * @param excelListener new ExcelListener()
     */
    private static ExcelReader getReader(MultipartFile excel,
                                         ExcelListener excelListener) {
        String filename = excel.getOriginalFilename();
        if (filename == null || (!filename.toLowerCase().endsWith(".xls") && !filename.toLowerCase().endsWith(".xlsx"))) {
            ExceptionCast.castFailMes("Excel文件格式错误");
        }
        InputStream inputStream;
        try {
            inputStream = new BufferedInputStream(excel.getInputStream());
            return new ExcelReader(inputStream, null, excelListener, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /****************************************多批次导出Excel*****************************************/
    /**
     * 导出Excel：一个sheet，带表头，大数据多批次写入Excel文件
     */
    public static void writeExcelBatch( List<? extends BaseRowModel> list,
                                        String sheetName, Class<? extends BaseRowModel> baseClass,
                                        String filePath,String fileName,boolean hasNext) throws Exception{

        if (CollectionUtils.isEmpty(list)) ExceptionCast.castFailMes("多批次导出Excel：list为空");
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(filePath + fileName);
            ExcelWriter writer = container.getExcelWriter();
            if (null == writer) {
                writer = new ExcelWriter(outputStream, ExcelTypeEnum.XLSX);
                container.setExcelWriter(writer);
            }
            Sheet sheet = new Sheet(1, 0, baseClass);
            sheet.setSheetName(sheetName);
            writer.write(list, sheet);
            if (!hasNext) writer.finish();
        } catch (Exception e) {
            log.info("多批次导出Excel异常",e);
        }
    }

    /**********************************多批次生成Excel需要唯一的ExcelWriter**************************************/
    private static class container {
        private static ThreadLocal<ExcelWriter> threadLocal = new ThreadLocal<>();
        public static ExcelWriter getExcelWriter() throws Exception{
            return threadLocal.get();
        }

        public static void setExcelWriter(ExcelWriter excelWriter) throws Exception{
            threadLocal.set(excelWriter);
        }

        public static void removeExcelWriter() throws Exception{
            threadLocal.remove();
        }
    }


    /*********************************************以下是静态内部类*************************************/
    public static class ExcelWriterFactroy extends ExcelWriter {
        private OutputStream outputStream;
        private int sheetNo = 1;

        public ExcelWriterFactroy(OutputStream outputStream, ExcelTypeEnum typeEnum) {
            super(outputStream, typeEnum);
            this.outputStream = outputStream;
        }

        public ExcelWriterFactroy write(List<? extends BaseRowModel> list, String sheetName,
                                        BaseRowModel object) {
            this.sheetNo++;
            try {
                Sheet sheet = new Sheet(sheetNo, 0, object.getClass());
                sheet.setSheetName(sheetName);
                this.write(list, sheet);
            } catch (Exception ex) {
                ex.printStackTrace();
                try {
                    outputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return this;
        }

        @Override
        public void finish() {
            super.finish();
            try {
                outputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static class ExcelListener extends AnalysisEventListener {

        //自定义用于暂时存储data。
        //可以通过实例获取该值
        private List<Object> datas = new ArrayList<>();

        /**
         * 通过 AnalysisContext 对象还可以获取当前 sheet，当前行等数据
         * 每解析一行都会调用invoke一次，可以在这里进数据进行处理
         */
        @Override
        public void invoke(Object object, AnalysisContext context) {
            //数据存储到list，供批量处理，或后续自己业务逻辑处理。
            datas.add(object);
            /*
            如数据过大，可以进行定量分批处理
            if(datas.size()<=1000){
                datas.add(object);
            }else {
                doSomething();
                datas = new ArrayList<Object>();
            }
             */

        }
        @Override
        public void doAfterAllAnalysed(AnalysisContext context) {
            datas.clear();
        }

        public List<Object> getDatas() {
            return datas;
        }

        public void setDatas(List<Object> datas) {
            this.datas = datas;
        }
    }


}
