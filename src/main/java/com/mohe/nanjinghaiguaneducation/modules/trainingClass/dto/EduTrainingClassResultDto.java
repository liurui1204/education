package com.mohe.nanjinghaiguaneducation.modules.trainingClass.dto;

import com.mohe.nanjinghaiguaneducation.modules.trainingClass.entity.EduTrainingClassEntity;
import lombok.Data;

@Data
public class EduTrainingClassResultDto extends EduTrainingClassEntity {
    private boolean showConfirmBtn ;
}
