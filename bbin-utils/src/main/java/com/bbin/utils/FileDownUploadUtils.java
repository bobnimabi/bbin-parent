package com.bbin.utils;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mrt on 2019/4/29 0029 下午 7:24
 */
public class FileDownUploadUtils {
    static Logger log = LoggerFactory.getLogger(FileDownUploadUtils.class);

    //上传文件
    public static String fileUploadOne(MultipartFile file, String upfilePath) {
        // 获得原始文件名+格式
        String fileName = file.getOriginalFilename();
        //截取文件名
        String fname = fileName.substring(0, fileName.lastIndexOf("."));
        //截取文件格式
        String format = fileName.substring(fileName.lastIndexOf(".") + 1);
        //获取当前时间(精确到毫秒)
        long MS = System.currentTimeMillis();
        String timeMS = String.valueOf(MS);
        //原文件名+当前时间戳作为新文件名
        String videoName = fname + "_" + timeMS + "." + format;
        String filelocalPath = "";
        char pathChar = upfilePath.charAt(upfilePath.length() - 1);
        Date date = new Date();
        String dateOne = new SimpleDateFormat("yyyy-MM-dd/").format(date);
        if (pathChar == '/') {
            filelocalPath = upfilePath + dateOne;
        } else {
            filelocalPath = upfilePath + "/" + dateOne;
        }
        File f = new File(filelocalPath);
        if (!f.exists())
            f.mkdirs();
        if (!file.isEmpty()) {
            try {
                //全路径：/${upfilePath}/yyyy-MM-dd/文件名_时间戳.后缀
                FileOutputStream fos = new FileOutputStream(filelocalPath + videoName);
                InputStream in = file.getInputStream();
                //InputStream in = request.getInputStream();
                byte[] bytes = new byte[1024];
                int len = 0;
                while ((len = in.read(bytes)) != -1) {
                    fos.write(bytes, 0, len);
                }
                fos.close();
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //返回：/yyyy-MM-dd/文件名_时间戳.后缀
        return "/"+dateOne+videoName;
    }

    //拼接完整路径
    public static String completePath(String relativePath,String upfilePath){
        char pathChar = upfilePath.charAt(upfilePath.length() - 1);
        if (pathChar == '/') {
            return upfilePath.substring(0,upfilePath.length() - 1) + relativePath;
        } else {
            return upfilePath + relativePath;
        }
    }

    //文件写入字符串
    public static void writeStr(String path , String content, boolean isAppend) throws Exception{
        FileWriter fileWriter = new FileWriter(path, isAppend);
        try {
            IOUtils.write(content, fileWriter);
        } catch (Exception e) {
            log.info("文件写入字符串异常",e);
        }finally {
            try {
                fileWriter.close();
            } catch (Exception e) {
                log.info("文件流关闭失败",e);
            }
        }
    }

    //输入流下载
    public static void downStream(HttpServletResponse response,InputStream inStream,String fileName ) throws Exception{
        ServletOutputStream outputStream = response.getOutputStream();
        response.reset();
//        response.setContentType("bin");
        response.addHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        byte[] b = new byte[100];
        int len;
        try {
            while ((len = inStream.read(b)) > 0) {
                outputStream.write(b, 0, len);
            }
        } catch (IOException e) {
            log.info("下载文件stream异常：",e);
        } finally {
            try {
                inStream.close();
            } catch (Exception e) {
                log.info("关闭输入流失败", e);
            }finally {
                try {
                    outputStream.close();
                } catch (Exception e) {
                    log.info("关闭输出流失败", e);
                }
            }
        }
    }

    //文件下载:下载后文件会删除
    public static void downFile(HttpServletResponse response,String filePath,String fileName) throws Exception{
        File file = new File(filePath + fileName);
        InputStream inStream = new FileInputStream(file);
        ServletOutputStream outputStream = response.getOutputStream();
        response.reset();
        response.addHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        byte[] b = new byte[100];
        int len;
        try {
            while ((len = inStream.read(b)) > 0) {
                outputStream.write(b, 0, len);
            }
        } catch (IOException e) {
            log.error("下载文件异常：",e);
        } finally {
            try {
                inStream.close();
            } catch (Exception e) {
                log.error("文件流inStream关闭异常：",e);
            }finally {
                try {
                    outputStream.close();
                    file.delete();
                } catch (Exception e) {
                    log.error("文件流outputStream关闭异常：",e);
                }finally {
                    try {
                        file.delete();
                    } catch (Exception e) {
                        log.error("文件删除异常：",e);
                    }
                }
            }
        }
    }
}
