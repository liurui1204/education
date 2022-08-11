package com.mohe.nanjinghaiguaneducation.modules.trainingClass.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class EduCommentUploadDto {
    private MultipartFile file;

    private String classId;

    private String courseId;
}
