package com.mohe.nanjinghaiguaneducation.common.utils;

import cn.hutool.core.date.DateUtil;
import com.mohe.nanjinghaiguaneducation.common.constant.SysConstant;
import com.mohe.nanjinghaiguaneducation.common.result.ResultData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

@ConfigurationProperties(prefix = "mohe.file")
@Component
public class UploadUtils {

    private static String filePath;

    /**
     * 单文件上传
     * @param file
     * @return
     */
    public static ResultData<Map<String,String>> upload(MultipartFile file, String name){

        ResultData<Map<String,String>> resultData = new ResultData<>();
        String dateTime = DateUtil.format(new Date(), "yyyyMMddHHmmss");
        String suffixName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String fileName =name+dateTime+UUID.randomUUID();
        String md5Str = DigestUtils.md5DigestAsHex(fileName.getBytes());

        String fileUrl = filePath+"/"+md5Str.substring(0,2)+"/"+md5Str.substring(2,4)+"/"+md5Str.substring(4,6)+"/"+md5Str+suffixName;
        File dest = new File(fileUrl);
        if(!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            //保存文件
            BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(fileUrl));
            outputStream.write(file.getBytes());
            outputStream.flush();
            outputStream.close();
            //返回 磁盘地址和映射url地址
//            map.put("path",path);
            Map<String,String> map = new HashMap();
            map.put("fileUrl",fileUrl);
            map.put("filePath",md5Str.substring(0,2)+"/"+md5Str.substring(2,4)+"/"+md5Str.substring(4,6));
            map.put("fileName",name+suffixName);

            map.put("url",filePath+"/"+md5Str.substring(0,2)+"/"+md5Str.substring(2,4)+"/"+md5Str.substring(4,6)+"/"+md5Str+suffixName);
            map.put("imageUrl","/"+md5Str.substring(0,2)+"/"+md5Str.substring(2,4)+"/"+md5Str.substring(4,6)+"/"+md5Str+suffixName);
            resultData.setSuccess(true);
            resultData.setData(map);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setCode(SysConstant.SUCCESS);
            return resultData;
        } catch (Exception e) {
            e.printStackTrace();
            resultData.setSuccess(false);
            resultData.setMessage(e.getMessage());
            resultData.setCode(SysConstant.FAIL);
            return resultData;

        }
    }

    /**
     * 多文件上传
     * @param file
     * @return
     */
    public static ResultData<List<Map<String,String>>> upload(MultipartFile[] file, String[] names){

        ResultData<List<Map<String,String>>> resultData = new ResultData<>();
        List<Map<String,String>>list = new ArrayList<>();
        if (file.length>0){
            for (int i=0;i<file.length;i++) {
                try {
                    String dateTime = DateUtil.format(new Date(), "yyyyMMddHHmmss");
                    String suffixName = file[i].getOriginalFilename().substring(file[i].getOriginalFilename().lastIndexOf("."));
                    String fileName =names[i]+dateTime+UUID.randomUUID();
                    String md5Str = DigestUtils.md5DigestAsHex(fileName.getBytes());

                    String fileUrl = filePath+"/"+md5Str.substring(0,2)+"/"+md5Str.substring(2,4)+"/"+md5Str.substring(4,6)+"/"+md5Str+suffixName;
                    File dest = new File(fileUrl);
                    if(!dest.getParentFile().exists()) {
                        dest.getParentFile().mkdirs();
                    }
                    Map<String,String> map = new HashMap();
                    map.put("fileUrl",fileUrl);
                    map.put("filePath",md5Str.substring(0,2)+"/"+md5Str.substring(2,4)+"/"+md5Str.substring(4,6));
                    map.put("fileName",md5Str);
                    map.put("name",names[i]);
                    list.add(map);
                    BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(fileUrl));
                    outputStream.write(file[i].getBytes());
                    outputStream.flush();
                    outputStream.close();

                }catch (Exception e){
                    resultData.setSuccess(false);
                    resultData.setMessage(e.getMessage());
                    resultData.setCode(SysConstant.FAIL);
                    return resultData;
                }

            }
            resultData.setSuccess(true);
            resultData.setData(list);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setCode(SysConstant.SUCCESS);
            return resultData;
        }
        resultData.setSuccess(false);
        resultData.setMessage("上传文件数量为0");
        resultData.setCode(SysConstant.FAIL);
        return resultData;
    }

    /**
     * 下载文件
     * @param path
     * @param res
     */
    public static void download(String path, HttpServletResponse res,String fileName){
        InputStream fis=null;
        OutputStream toClient=null;
        try {
            //获取下载的路径
            File file=new File(path);
            //获取文件名
            String filename=file.getName();
            //取得文件的后缀名
            String ext=filename.substring(filename.lastIndexOf(".")+1).toUpperCase();
            //以流的形式下载文件
            fis=new BufferedInputStream(new FileInputStream(file));
            System.out.println("文件大小："+fis.available());
            //创建一个和文件一样大小的缓存区
            byte[] buffer=new byte[fis.available()];
            //读取流
            fis.read(buffer);
            //清空首部空白行
            res.reset();
            //设置文件下载后的指定文件名
            res.addHeader("Content-Disposition", "attachment;filename=" +new String(fileName.getBytes("UTF-8"),"ISO8859-1"));
            res.addHeader("Content-Length", "" + file.length());
            //response.getOutputStream() 获得字节流，通过该字节流的write(byte[] bytes)可以向response缓冲区中写入字节，再由Tomcat服务器将字节内容组成Http响应返回给浏览器。
            toClient = new BufferedOutputStream(res.getOutputStream());
            res.setContentType("application/x-msdownload;");
            //将buffer 个字节从指定的 byte 数组写入此输出流。
            toClient.write(buffer);
            //刷新此缓冲的输出流。这迫使所有缓冲的输出字节被写出到底层输出流中。 把缓存区的数据全部写出
            toClient.flush();
        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            try {
                //关闭流
                fis.close();
                //关闭缓冲输出流
                toClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private static void setDownloadContent(String fileName, HttpServletRequest request, HttpServletResponse response) {
        String agent = request.getHeader("User-Agent");
        try {
            if (null != agent && agent.toUpperCase().indexOf("MSIE") > 0) {
                fileName = URLEncoder.encode(fileName, "UTF-8");
            } else {
                fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
            }
        } catch (UnsupportedEncodingException e1) {
        }
        response.setContentType("application/x-msdownload;");
//        response.setContentType("text/html;");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
    }


    public static String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        UploadUtils.filePath = filePath;
    }
}
