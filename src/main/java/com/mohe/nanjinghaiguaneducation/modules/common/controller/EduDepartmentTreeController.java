package com.mohe.nanjinghaiguaneducation.modules.common.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mohe.nanjinghaiguaneducation.common.constant.SysConstant;
import com.mohe.nanjinghaiguaneducation.common.result.ResultData;
import com.mohe.nanjinghaiguaneducation.common.utils.ConvertUtils;
import com.mohe.nanjinghaiguaneducation.modules.common.dto.EduTreeDto;
import com.mohe.nanjinghaiguaneducation.modules.common.dto.EduTreeEmployeeDto;
import com.mohe.nanjinghaiguaneducation.modules.common.entity.EduDepartmentEmployeeEntity;
import com.mohe.nanjinghaiguaneducation.modules.common.entity.EduDepartmentEntity;
import com.mohe.nanjinghaiguaneducation.modules.common.entity.EduDepartmentTreeEntity;
import com.mohe.nanjinghaiguaneducation.modules.common.service.EduDepartmentEmployeeService;
import com.mohe.nanjinghaiguaneducation.modules.common.service.EduDepartmentService;
import com.mohe.nanjinghaiguaneducation.modules.employee.entity.EduEmployeeEntity;
import com.mohe.nanjinghaiguaneducation.modules.employee.service.EduEmployeeService;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.entity.EduTrainingClassEntity;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.service.EduTrainingClassService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.collections.IteratorUtils;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.function.Supplier;

@RestController
@Api(tags = "部门参数")
@RequestMapping("common/department")
public class EduDepartmentTreeController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private EduEmployeeService eduEmployeeService;
    @Autowired
    private EduDepartmentService eduDepartmentService;
    @Autowired
    private EduDepartmentEmployeeService eduDepartmentEmployeeService;
    @Autowired
    private EduTrainingClassService eduTrainingClassService;

    @ApiOperation("查看部门树")
    @PostMapping("/tree")
    public ResultData<EduDepartmentTreeEntity> tree(@RequestBody EduTreeDto eduTreeDto) {
        String name = eduTreeDto.getName();
        ResultData<EduDepartmentTreeEntity> resultData = new ResultData<>();
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        resultData.setSuccess(true);
        EntityWrapper<EduDepartmentEntity> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("isEnable", 1).isNull("parentId").eq("type", 1);
        //查询顶级目录
        EduDepartmentEntity entity = eduDepartmentService.selectOne(entityWrapper);
        EduDepartmentTreeEntity eduDepartmentTreeEntity = ConvertUtils.convert(entity, EduDepartmentTreeEntity::new);
        //获取父级code
        String parentId = eduDepartmentTreeEntity.getDepartmentCode();
        EntityWrapper<EduDepartmentEntity> entityEntityWrapper = new EntityWrapper<>();
        entityEntityWrapper.eq("isEnable", 1);
        entityEntityWrapper.orderBy("`order`", true);
        //查询所有的部门
        List<EduDepartmentEntity> entityList = eduDepartmentService.selectList(entityEntityWrapper);
        List<EduDepartmentTreeEntity> eduDepartmentTreeEntities = ConvertUtils.convertList(entityList, EduDepartmentTreeEntity::new);
        //递归遍历
        List<EduDepartmentTreeEntity> tree = getTree(parentId, eduDepartmentTreeEntities);
        eduDepartmentTreeEntity.setChildren(tree);
        if (name != null && name != "") {
            List<EduDepartmentTreeEntity> entities = treeMatch(eduDepartmentTreeEntity.getChildren(), name);
            eduDepartmentTreeEntity.setChildren(entities);
        }
        resultData.setData(eduDepartmentTreeEntity);
        return resultData;
    }

    private List<EduDepartmentTreeEntity> getTree(String parentId, List<EduDepartmentTreeEntity> eduDepartmentTreeEntities) {
        List<EduDepartmentTreeEntity> eduDepartmentTreeEntityList = new ArrayList<>();
        for (EduDepartmentTreeEntity eduDepartment : eduDepartmentTreeEntities) {
            String code = eduDepartment.getDepartmentCode();
            String departmentCode = eduDepartment.getParentId();
            if (parentId.equals(departmentCode)) {
                List<EduDepartmentTreeEntity> tree = getTree(code, eduDepartmentTreeEntities);
                if (tree != null && tree.size() > 0) {
                    eduDepartment.setChildren(tree);
                }
                eduDepartmentTreeEntityList.add(eduDepartment);
            }
        }

        return eduDepartmentTreeEntityList;
    }

    @ApiOperation("根据部门Code获取人员 new")
    @PostMapping("/getEmployeeList")
    public ResultData<List<EduEmployeeEntity>> getEmployeeLIst(@ApiParam("选择完部门树后将code值以集合方式传") @RequestBody EduTreeEmployeeDto eduTreeEmployeeDto) {
        ResultData<List<EduEmployeeEntity>> resultData = new ResultData<>();
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        resultData.setSuccess(true);
        resultData.setData(eduDepartmentService.getEmployeeListByDepartmentCodes(eduTreeEmployeeDto.getDepartmentCodes()));
        return resultData;
    }

    @ApiOperation("根据部门Code获取人员 搭配部门树使用")
    @PostMapping("/getEmployee")
    public ResultData<List<EduEmployeeEntity>> getEmployee(@ApiParam("选择完部门树后将code值以集合方式传") @RequestBody EduTreeEmployeeDto eduTreeEmployeeDto) {
        ResultData<List<EduEmployeeEntity>> resultData = new ResultData<>();
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        resultData.setSuccess(true);
        //查询所有人员
        List<EduEmployeeEntity> eduEmployeeEntities = eduEmployeeService.selectList(new EntityWrapper<>());
        List<EduEmployeeEntity> entities = new ArrayList<>();
        List<String> departmentCodes = eduTreeEmployeeDto.getDepartmentCodes();

        for (String departmentCode : departmentCodes) {
            EntityWrapper<EduDepartmentEntity> entityEntityWrapper = new EntityWrapper<>();
            entityEntityWrapper.eq("departmentCode", departmentCode);
            //查询部门
            EduDepartmentEntity eduDepartmentEntitie = eduDepartmentService.selectOne(entityEntityWrapper);
            if (ObjectUtils.isEmpty(eduDepartmentEntitie)){
                continue;
            }
            //查询部门下的人员
            List<EduDepartmentEmployeeEntity> eduDepartmentEmployeeEntity = eduDepartmentEmployeeService.selectList(new EntityWrapper<EduDepartmentEmployeeEntity>()
                    .eq("departmentCode", eduDepartmentEntitie.getDepartmentCode()));
            if (eduDepartmentEmployeeEntity.isEmpty() || ObjectUtils.isEmpty(eduDepartmentEmployeeEntity)){
                continue;
            }
            for (EduEmployeeEntity eduEmployeeEntity : eduEmployeeEntities) {
                for (EduDepartmentEmployeeEntity departmentEmployeeEntity : eduDepartmentEmployeeEntity) {
                    System.out.println(departmentEmployeeEntity.getEmployee_id());
                    if (eduEmployeeEntity.getId().equals(departmentEmployeeEntity.getEmployee_id()) && !ObjectUtils.isEmpty(eduEmployeeEntity.getEmployeeCode())) {
                        entities.add(eduEmployeeEntity);
                        break;
                    }
                }

            }
        }
        resultData.setData(entities);
        return resultData;
    }

    //List 迭代器处理数据
    public List<EduDepartmentTreeEntity> treeMatch(List<EduDepartmentTreeEntity> entities, String name) {
        Iterator<EduDepartmentTreeEntity> oneIter = entities.iterator();
        while (oneIter.hasNext()) {
            EduDepartmentTreeEntity entity = oneIter.next();
            List<EduDepartmentTreeEntity> twoCategoryList = entity.getChildren();
            if (ObjectUtils.isEmpty(twoCategoryList)) {
                if (!entity.getDepartmentName().contains(name)){
                    oneIter.remove();
                }
                continue;
            }
            Iterator<EduDepartmentTreeEntity> twoIter = twoCategoryList.iterator();
            while (twoIter.hasNext()) {
                EduDepartmentTreeEntity twoCategory = twoIter.next();
                List<EduDepartmentTreeEntity> threeCategoryList = twoCategory.getChildren();
                if (ObjectUtils.isEmpty(threeCategoryList)) {
                    if (!twoCategory.getDepartmentName().contains(name)){
                        twoIter.remove();
                    }
                    continue;
                }
                Iterator<EduDepartmentTreeEntity> threeIter = threeCategoryList.iterator();
                while (threeIter.hasNext()) {
                    EduDepartmentTreeEntity threeCategory = threeIter.next();
                    List<EduDepartmentTreeEntity> FourCategoryList = threeCategory.getChildren();
                    if (ObjectUtils.isEmpty(FourCategoryList)) {
                        if (!threeCategory.getDepartmentName().contains(name)){
                            threeIter.remove();
                        }
                        continue;
                    }
                    Iterator<EduDepartmentTreeEntity> fourIter = FourCategoryList.iterator();
                    while (fourIter.hasNext()) {
                        EduDepartmentTreeEntity fourCate = fourIter.next();
                        if (!fourCate.getDepartmentName().contains(name)) {
                            fourIter.remove();
                        }
                    }
                    if (!threeCategory.getDepartmentName().contains(name)){
                        if (CollectionUtils.isEmpty(threeCategory.getChildren())){
                            threeIter.remove();
                        }
                    }
                }
                if (!twoCategory.getDepartmentName().contains(name)){
                    if(CollectionUtils.isEmpty(twoCategory.getChildren()) || twoCategory.getChildren().size()<1){
                        twoIter.remove();
                    }
                }
            }
            if (!entity.getDepartmentName().contains(name)){
                if(CollectionUtils.isEmpty(entity.getChildren())){
                    oneIter.remove();
                }
            }

        }
        return entities;
    }

    @ApiOperation("查看部门树")
    @PostMapping("/lazyTree")
    public ResultData<List<EduDepartmentTreeEntity>> lazyTree(HttpServletRequest request, @RequestBody EduTreeDto eduTreeDto) {
        ResultData<List<EduDepartmentTreeEntity>> resultData = new ResultData<>();
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        resultData.setSuccess(true);
        String classId = eduTreeDto.getClassId();
        EntityWrapper<EduDepartmentEntity> entityWrapper = new EntityWrapper<>();
        //不管什么业务，返回的department部门树，都给排序
        entityWrapper.orderBy("`order`", true);
        EduTrainingClassEntity eduTrainingClassEntity = eduTrainingClassService.selectById(classId);
        //判断如果培训班创建人为登录人
        if(eduTrainingClassEntity.getCreateBy().equals(request.getAttribute("userId").toString())) {
            if (ObjectUtils.isEmpty(eduTreeDto.getDepartmentCode())){
                entityWrapper.eq("isEnable", 1).isNull("parentId").eq("type", 1);
            }
            if (!ObjectUtils.isEmpty(eduTreeDto.getDepartmentCode())){
                entityWrapper.eq("parentId",eduTreeDto.getDepartmentCode());
            }
            if (!ObjectUtils.isEmpty(eduTreeDto.getName())){
                entityWrapper.like("departmentName",eduTreeDto.getName());
            }
            List<EduDepartmentEntity> eduDepartmentEntities = eduDepartmentService.selectList(entityWrapper);
            if (ObjectUtils.isEmpty(eduDepartmentEntities)){
                return resultData;
            }
            List<EduDepartmentTreeEntity>entities = ConvertUtils.convertList(eduDepartmentEntities,EduDepartmentTreeEntity::new);
            resultData.setData(entities);
        }else {
//            if (ObjectUtils.isEmpty(eduTreeDto.getDepartmentCode())){  // 第一级南京海关\\
//                entityWrapper.eq("isEnable", 1).isNull("parentId").eq("type", 1);
//            }
            // 根据code获取当前登录人的部门
            String userId = request.getAttribute("userId").toString();
            //获取当前登录人的信息
            EduEmployeeEntity eduEmployeeEntity = eduEmployeeService.selectById(userId);
            String h4aAllPathName = eduEmployeeEntity.getH4aAllPathName();
            String[] split = h4aAllPathName.split("\\\\");
            //获取部门名称
            String deptName = split[0]+"\\"+split[1]+"\\"+split[2];
            EduDepartmentEntity departmentEntity = eduDepartmentService.selectOne(new EntityWrapper<EduDepartmentEntity>().eq("departmentAllPath", deptName));
            entityWrapper.eq("departmentAllPath",departmentEntity.getDepartmentAllPath());
            if (!ObjectUtils.isEmpty(eduTreeDto.getDepartmentCode())){
                entityWrapper = new EntityWrapper<>();
                entityWrapper.eq("parentId",eduTreeDto.getDepartmentCode());
            }
            if (!ObjectUtils.isEmpty(eduTreeDto.getName())){
                entityWrapper.like("departmentName",eduTreeDto.getName());
            }
            List<EduDepartmentEntity> eduDepartmentEntities = eduDepartmentService.selectList(entityWrapper);
            if (ObjectUtils.isEmpty(eduDepartmentEntities)){
                return resultData;
            }
            List<EduDepartmentTreeEntity>entities = ConvertUtils.convertList(eduDepartmentEntities,EduDepartmentTreeEntity::new);
            resultData.setData(entities);
        }

        return resultData;
    }

}
