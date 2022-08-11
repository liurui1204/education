package com.mohe.nanjinghaiguaneducation.modules.common.controller;

import com.mohe.nanjinghaiguaneducation.common.constant.SysConstant;
import com.mohe.nanjinghaiguaneducation.common.result.ResultData;
import com.mohe.nanjinghaiguaneducation.modules.common.entity.EduDepartmentTreeEntity;
import com.mohe.nanjinghaiguaneducation.modules.common.entity.EduTrainingTreeEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Api(tags = "实训点管理")
@RequestMapping("/common/training")
public class EduTrainingController {

    @ApiOperation("查询实训点树")
    @PostMapping("/tree")
    public ResultData<List<EduTrainingTreeEntity>> tree(){
        ResultData<List<EduTrainingTreeEntity>> resultData = new ResultData<>();
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        resultData.setSuccess(true);
        List<EduTrainingTreeEntity> eduTrainingTreeEntities = new ArrayList<>();
        EduTrainingTreeEntity eduTrainingTreeEntity = new EduTrainingTreeEntity();
        EduTrainingTreeEntity eduTrainingTree = new EduTrainingTreeEntity();
        eduTrainingTreeEntity.setTrainingBaseName("南京夫子庙");
        eduTrainingTreeEntity.setId("1");
        eduTrainingTreeEntities.add(eduTrainingTreeEntity);
        eduTrainingTree.setId("2");
        eduTrainingTree.setTrainingBaseName("南京海关");
        eduTrainingTreeEntities.add(eduTrainingTree);
        resultData.setData(eduTrainingTreeEntities);
        return resultData;
    }
}
