package com.mohe.nanjinghaiguaneducation.modules.performance.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.jwt.JWTUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mohe.nanjinghaiguaneducation.common.constant.SysConstant;
import com.mohe.nanjinghaiguaneducation.common.result.ResultData;
import com.mohe.nanjinghaiguaneducation.common.utils.*;
import com.mohe.nanjinghaiguaneducation.modules.employee.service.EduEmployeeService;
import com.mohe.nanjinghaiguaneducation.modules.performance.dto.EduOnlinePerformanceDetailDto;
import com.mohe.nanjinghaiguaneducation.modules.performance.dto.EduOnlinePerformanceDetailEnterReferenceDto;
import com.mohe.nanjinghaiguaneducation.modules.performance.dto.EduStudyPerformanceDetailDto;
import com.mohe.nanjinghaiguaneducation.modules.performance.dto.UploadDto;
import com.mohe.nanjinghaiguaneducation.modules.performance.entity.*;
import com.mohe.nanjinghaiguaneducation.modules.performance.service.EduOnlinePerformanceDetailService;
import com.mohe.nanjinghaiguaneducation.modules.performance.service.EduOnlinePerformanceImportDetailService;
import com.mohe.nanjinghaiguaneducation.modules.performance.service.EduOnlinePerformanceImportService;
import com.mohe.nanjinghaiguaneducation.modules.performance.service.EduOnlinePerformanceService;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.entity.EduStudyPerformanceDetailListEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * author: CC
 * date:   2022/4/11
 * description:
 **/
@RestController
@RequestMapping("/front/performance/online")
@Api(tags = "网络培训班（用户端）")
public class EduOnlinePerformanceController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private EduOnlinePerformanceService eduOnlinePerformanceService;
    @Autowired
    private EduOnlinePerformanceDetailService eduOnlinePerformanceDetailService;
    @Autowired
    private EduOnlinePerformanceImportService eduOnlinePerformanceImportService;
    @Autowired
    private EduOnlinePerformanceImportDetailService eduOnlinePerformanceImportDetailService;
    @Autowired
    private EduEmployeeService eduEmployeeService;

//    @RequestMapping ("/upload")
//    @ApiOperation("网络培训班导入")
//    @Transactional
//    public ResultData importData(EduCommentUploadDto uploadDto, HttpServletRequest request)  {
//        ResultData resultData = new ResultData();
//        try {
//            //加载拦截器 作用于 获取表头
//            DynamicHeaderListener noModelDataListener = new DynamicHeaderListener();
//
//            //解析数据
//            List<Map<Integer,Object>> list = EasyExcel.read(uploadDto.getFile().getInputStream(),noModelDataListener).sheet(0).doReadSync();
//            //获取拦截器拦截到的 表头的Map集合
//            Map<Integer, String> key = noModelDataListener.getKey();
//            //处理 excel中的数据 将其 取出  最后统一操作！
//
//            String name = DateUtil.format(new Date(),"yyyyMMddHHmmss")+"网络培训班数据导入";
//            //文件上传到服务器
//            ResultData<Map<String, String>> data = UploadUtils.upload(uploadDto.getFile(), name);
//            String userId = request.getAttribute("userId").toString();
//            uploadDto.setFileUri(data.getData().get("fileUrl"));
//            uploadDto.setFileName(uploadDto.getFile().getName());
//            //因为excel中 只取了一级表头 二级表头也存在这个数据里了 所以要去掉一行的数据才对
//            uploadDto.setTotalNUmber(list.size()-1);
//            uploadDto.setUserId(userId);
//            Map upload = eduOnlinePerformanceImportDetailService.upload(key, list,uploadDto);
//            List<EduOnlinePerformanceImportDetailEntity> entities =(List<EduOnlinePerformanceImportDetailEntity>) upload.get("entities");
//
//            //excel  导出的所有数据统一新增进数据库
//            eduOnlinePerformanceImportDetailService.insertBatch(entities);
//            resultData.setCode(SysConstant.SUCCESS);
//            resultData.setSuccess(true);
//            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
//        } catch (Exception e){
//            resultData.setCode(SysConstant.FAIL);
//            resultData.setSuccess(false);
//            resultData.setMessage(e.getMessage());
//            return resultData;
//        }
//        return resultData;
//    }


    /**
     * 获取网络培训班列表  前台
     */
    @PostMapping("/list")
    @ApiOperation("获取网络培训班列表")
    public ResultData<PageUtils> pageListEduOnlinePerformance(@RequestBody Map<String, Object> params) {
        logger.info("查询网络培训班列表  参数为： {}", JSON.toJSONString(params));
        ResultData<PageUtils> resultData = new ResultData<>();
        try {
            Integer page = (Integer) params.get("page");
            Integer limit = (Integer) params.get("limit");
            if (page == null || limit == null
                    || page < 1 || limit < 1) {
                resultData.setSuccess(false);
                resultData.setCode(SysConstant.PARAMETER_ERROR);
                resultData.setMessage(SysConstant.getMessage(SysConstant.PARAMETER_ERROR));
                return resultData;
            }
            PageUtils pageUtils = eduOnlinePerformanceService.queryOnlinePerformanceList(params);
            resultData.setData(pageUtils);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setCode(SysConstant.SUCCESS);
            resultData.setSuccess(true);
            return resultData;
        } catch (Exception e) {
            logger.error("查询网络培训班列表异常： {}", e.getMessage());
            resultData.setSuccess(false);
            resultData.setCode(SysConstant.PARAMETER_ERROR);
            resultData.setMessage(SysConstant.getMessage(SysConstant.PARAMETER_ERROR));
            return resultData;
        }
    }


    @PostMapping("/rateTreeInfo")
    @ApiOperation("获取各部门达标情况")
    public ResultData<EduOnlinePerformanceDetailDto> findDepartmentSituation(@RequestBody EduOnlinePerformanceDetailEnterReferenceDto eduOnlinePerformanceDetailEnterReferenceDto) {
        logger.info(" 部门达标情况 参数网络培训班ID为： {}", JSON.toJSONString(eduOnlinePerformanceDetailEnterReferenceDto));
        ResultData<EduOnlinePerformanceDetailDto> resultData = new ResultData<>();
        try {
            EduOnlinePerformanceDetailDto eduOnlinePerformanceDetailDto = new EduOnlinePerformanceDetailDto();
            if (eduOnlinePerformanceDetailEnterReferenceDto.getOnlineClassId() == null) {
                resultData.setSuccess(false);
                resultData.setCode(SysConstant.FAIL);
                resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
                resultData.setData(eduOnlinePerformanceDetailDto);
                return resultData;
            }
            String id = eduOnlinePerformanceDetailEnterReferenceDto.getOnlineClassId();
            EduOnlinePerformanceDetailDto detailDto = eduOnlinePerformanceService.selectDetail(id);
            resultData.setData(detailDto);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setCode(SysConstant.SUCCESS);
            resultData.setSuccess(true);
            return resultData;
        } catch (Exception e) {
            logger.error("查询网络培训班列表异常： {}", e.getMessage());
            resultData.setSuccess(false);
            resultData.setCode(SysConstant.PARAMETER_ERROR);
            resultData.setMessage(SysConstant.getMessage(SysConstant.PARAMETER_ERROR));
            return resultData;


        }


    }

    @PostMapping("/rateTreeDetail")
    @ApiOperation("获取部门的各课程达标情况")
    public ResultData<List<EduStudyTree>>  rateDetail(@RequestBody Map<String,Object> params){
        ResultData<List<EduStudyTree>> resultData=new ResultData<>();
        resultData.setSuccess(true);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        resultData.setCode(SysConstant.SUCCESS);
        String treeCode = params.get("treeCode").toString();
        EduOnlinePerformanceEntity eduOnlinePerformanceEntity = eduOnlinePerformanceService.selectOne(new EntityWrapper<EduOnlinePerformanceEntity>()
                .eq("year",DateUtil.year(new Date())));
        String departmentCustomsRate = eduOnlinePerformanceEntity.getDepartmentCustomsRate();
        EduStudyTree eduStudyTree = JSON.parseObject(departmentCustomsRate, EduStudyTree.class);
        if (eduStudyTree.getTreeCode().equals(treeCode)){
            resultData.setData(eduStudyTree.getChildren());
        }else {
            for (EduStudyTree eduStudyTreeChild : eduStudyTree.getChildren()) {
                Boolean isResult = false;
                if (eduStudyTreeChild.getTreeCode().equals(treeCode)){
                    resultData.setData(eduStudyTreeChild.getChildren());
                    break;
                }else {
                    for (EduStudyTree child : eduStudyTreeChild.getChildren()) {
                        if (child.getTreeCode().equals(treeCode)){
                            resultData.setData(child.getChildren());
                            isResult=true;
                            break;
                        }
                    }
                    if (isResult){
                        break;
                    }
                }
            }
        }

        return resultData;

    }
}
