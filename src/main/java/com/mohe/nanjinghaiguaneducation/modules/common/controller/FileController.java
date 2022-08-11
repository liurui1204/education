package com.mohe.nanjinghaiguaneducation.modules.common.controller;


import com.mohe.nanjinghaiguaneducation.common.result.ResultData;
import com.mohe.nanjinghaiguaneducation.common.utils.UploadUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RequestMapping("file")
@RestController
public class FileController {
    @ApiOperation("上传文件")
    @PostMapping("/upload")
    public ResultData upload(MultipartFile file){
        ResultData<Map<String, String>> upload = UploadUtils.upload(file, file.getName());
        upload.getData().put("fileUrl",upload.getData().get("url"));

        return upload;
    }

    @ApiOperation("下载文件")
    @GetMapping("/download")
    public void download(String fileUrl, HttpServletResponse response, String fileName){
        UploadUtils.download(fileUrl,response,fileName);
    }
}
