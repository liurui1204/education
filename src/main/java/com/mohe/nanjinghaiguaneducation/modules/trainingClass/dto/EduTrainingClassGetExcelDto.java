package com.mohe.nanjinghaiguaneducation.modules.trainingClass.dto;

import com.mohe.nanjinghaiguaneducation.modules.trainingClass.vo.EduTrainingClassAllExcel;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.vo.EduTrainingClassFeeItemExcelVo;
import lombok.Data;

import java.util.List;

@Data
public class EduTrainingClassGetExcelDto {
    private List<EduTrainingClassAllExcel> eduTrainingClassAllExcels;

    private List<EduTrainingClassFeeItemExcelVo> eduTrainingClassFeeItemExcelVos;
}
