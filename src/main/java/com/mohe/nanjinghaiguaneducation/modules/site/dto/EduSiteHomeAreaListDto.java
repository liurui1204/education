package com.mohe.nanjinghaiguaneducation.modules.site.dto;

import com.mohe.nanjinghaiguaneducation.modules.common.entity.EduSystemNoticeEntity;
import com.mohe.nanjinghaiguaneducation.modules.resource.entity.EduResourceEntity;
import com.mohe.nanjinghaiguaneducation.modules.site.entity.EduTrainingElegantEntity;
import com.mohe.nanjinghaiguaneducation.modules.site.entity.EduTrainingNewsEntity;
import com.mohe.nanjinghaiguaneducation.modules.site.entity.EduTrainingResourceEntity;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.entity.EduTrainingClassCourseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class EduSiteHomeAreaListDto {
    @ApiModelProperty("教培动态")
    private List<EduTrainingNewsEntity> eduTrainingNewsEntities;
    @ApiModelProperty("教培风采")
    private List<EduTrainingElegantEntity> trainingElegantEntities;
    @ApiModelProperty("最新通知")
    private List<EduSystemNoticeEntity> eduSystemNoticeEntities;
    @ApiModelProperty("最新资源")
    private List<EduResourceEntity> eduResourceEntities;
    @ApiModelProperty("精品课程")
    private List<EduTrainingClassCourseEntity> eduTrainingClassCourseEntities;
    @ApiModelProperty("教培动态大图列表")
    private List<Map<String, Object>> eduTrainingNewsImages;
    @ApiModelProperty("成果展示")
    private List<EduTrainingResourceEntity> eduTrainingResourceEntities;
}
