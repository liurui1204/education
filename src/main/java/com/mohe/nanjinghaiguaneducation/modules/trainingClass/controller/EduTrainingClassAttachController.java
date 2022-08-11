package com.mohe.nanjinghaiguaneducation.modules.trainingClass.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mohe.nanjinghaiguaneducation.common.constant.SysConstant;
import com.mohe.nanjinghaiguaneducation.common.result.ResultData;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.common.utils.UploadUtils;
import com.mohe.nanjinghaiguaneducation.modules.employee.entity.EduEmployeeEntity;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.dto.EduTrainingClassAttachDto;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.dto.EduTrainingClassAttachListDto;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.entity.EduTrainingClassAttachEntity;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.entity.EduTrainingClassCourseEntity;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.service.EduTrainingClassAttachService;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.service.EduTrainingClassCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 培训班附件
 *
 * @author mohe
 * @email mohe@163.com
 * @date 2022-03-03 17:38:16
 */
@Api(tags = "培训班附件管理")
@RestController
@RequestMapping("/trainingClass/attach")
public class EduTrainingClassAttachController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private EduTrainingClassAttachService eduTrainingClassAttachService;
    @Autowired
    private EduTrainingClassCourseService eduTrainingClassCourseService;

    @RequestMapping("/courseware/upload")
    @ApiOperation("上传课件")
    public ResultData coursewareUpload(EduTrainingClassAttachDto eduTrainingClassAttachDto, HttpServletRequest request){
        ResultData resultData= new ResultData();
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        resultData.setSuccess(true);

        EduEmployeeEntity eduEmployeeEntity = (EduEmployeeEntity) request.getAttribute("userInfo");

        //循环上传文件要一一对应
        ResultData<Map<String, String>> upload = UploadUtils.upload(eduTrainingClassAttachDto.getFiles(), eduTrainingClassAttachDto.getNames());
        Map<String, String> map = upload.getData();
        String courseId = eduTrainingClassAttachDto.getCourseId();
        //查询课程信息
        EduTrainingClassCourseEntity eduTrainingClassCourseEntity = eduTrainingClassCourseService.selectById(courseId);
        //将课件的 名称--地址 绑定给课程信息
        eduTrainingClassCourseEntity.setCourseWare(eduTrainingClassAttachDto.getNames());
        eduTrainingClassCourseEntity.setCourseWareAddr(map.get("fileUrl"));
        eduTrainingClassCourseService.updateById(eduTrainingClassCourseEntity);
        //处理自身数据
        EduTrainingClassAttachEntity eduTrainingClassAttach = new EduTrainingClassAttachEntity();
        eduTrainingClassAttach.setCourseId(courseId);
        eduTrainingClassAttach.setTrainingClassId(eduTrainingClassAttachDto.getTrainingClassId());
        eduTrainingClassAttach.setAttachUri(map.get("fileUrl"));
        eduTrainingClassAttach.setAttachType(1);
        eduTrainingClassAttach.setAttachTitle(eduTrainingClassAttachDto.getNames());
        eduTrainingClassAttach.setAttachFileName(eduTrainingClassAttachDto.getFiles().getOriginalFilename());
        eduTrainingClassAttach.setAttachPath(map.get("filePath"));
        eduTrainingClassAttach.setCreateTime(new Date());
        eduTrainingClassAttach.setId(IdUtil.simpleUUID());
        eduTrainingClassAttach.setCreateBy(eduEmployeeEntity.getEmployeeCode());
        eduTrainingClassAttach.setIsEnable(1);
        eduTrainingClassAttachService.insert(eduTrainingClassAttach);
        return resultData;
    }

    @RequestMapping("/notify/upload")
    @ApiOperation("上传通知")
    public ResultData notifyUpload(EduTrainingClassAttachDto eduTrainingClassAttachDto, HttpServletRequest request){
        ResultData resultData= new ResultData();
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        resultData.setSuccess(true);
        EduEmployeeEntity eduEmployeeEntity = (EduEmployeeEntity) request.getAttribute("userInfo");

        ResultData<Map<String, String>> upload = UploadUtils.upload(eduTrainingClassAttachDto.getFiles(), eduTrainingClassAttachDto.getNames());
        Map<String, String> map = upload.getData();
        EduTrainingClassAttachEntity eduTrainingClassAttach = new EduTrainingClassAttachEntity();
        eduTrainingClassAttach.setTrainingClassId(eduTrainingClassAttachDto.getTrainingClassId());
        eduTrainingClassAttach.setAttachUri(map.get("fileUrl"));
        eduTrainingClassAttach.setAttachType(2);
        eduTrainingClassAttach.setAttachTitle(eduTrainingClassAttachDto.getNames());
        eduTrainingClassAttach.setAttachFileName(eduTrainingClassAttachDto.getFiles().getOriginalFilename());
        eduTrainingClassAttach.setAttachPath(map.get("filePath"));
        eduTrainingClassAttach.setCreateTime(new Date());
        eduTrainingClassAttach.setId(IdUtil.simpleUUID());
        eduTrainingClassAttach.setCreateBy(eduEmployeeEntity.getEmployeeCode());
        eduTrainingClassAttach.setIsEnable(1);
        eduTrainingClassAttach.setIsShowIndex(eduTrainingClassAttachDto.getIsShowIndex());
        eduTrainingClassAttachService.insert(eduTrainingClassAttach);
        return resultData;
    }

    @RequestMapping("/notify/update")
    @ApiOperation("修改是否在首页展示")
    public ResultData notifyUpdate(@RequestBody Map<String,Object>params){
        ResultData resultData= new ResultData();
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        resultData.setSuccess(true);
        String id = params.get("id").toString();
        Integer isShowIndex = (Integer) params.get("isShowIndex");
        EduTrainingClassAttachEntity eduTrainingClassAttach = eduTrainingClassAttachService.selectById(id);
        eduTrainingClassAttach.setIsShowIndex(isShowIndex);
        eduTrainingClassAttachService.updateById(eduTrainingClassAttach);
        return resultData;
    }
    @RequestMapping("/file/upload")
    @ApiOperation("上传附件")
    public ResultData fileUpload(EduTrainingClassAttachDto eduTrainingClassAttachDto, HttpServletRequest request){
        ResultData resultData= new ResultData();
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        resultData.setSuccess(true);
        EduEmployeeEntity eduEmployeeEntity = (EduEmployeeEntity) request.getAttribute("userInfo");

        ResultData<Map<String, String>> upload = UploadUtils.upload(eduTrainingClassAttachDto.getFiles(), eduTrainingClassAttachDto.getNames());
        Map<String, String> map = upload.getData();
        EduTrainingClassAttachEntity eduTrainingClassAttach = new EduTrainingClassAttachEntity();
        eduTrainingClassAttach.setTrainingClassId(eduTrainingClassAttachDto.getTrainingClassId());
        eduTrainingClassAttach.setAttachUri(map.get("fileUrl"));
        eduTrainingClassAttach.setAttachType(3);
        eduTrainingClassAttach.setAttachTitle(eduTrainingClassAttachDto.getNames());
        eduTrainingClassAttach.setAttachFileName(eduTrainingClassAttachDto.getFiles().getOriginalFilename());
        eduTrainingClassAttach.setAttachPath(map.get("filePath"));
        eduTrainingClassAttach.setCreateTime(new Date());
        eduTrainingClassAttach.setId(IdUtil.simpleUUID());
        eduTrainingClassAttach.setCreateBy(eduEmployeeEntity.getEmployeeCode());
        eduTrainingClassAttach.setIsEnable(1);
        eduTrainingClassAttach.setIsShowIndex(eduTrainingClassAttachDto.getIsShowIndex());
        eduTrainingClassAttachService.insert(eduTrainingClassAttach);
        return resultData;
    }

    @PostMapping("/delete")
    public ResultData delete(@RequestBody Map<String,Object> params){
        ResultData resultData= new ResultData();
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        resultData.setSuccess(true);
        String id = params.get("id").toString();
        eduTrainingClassAttachService.deleteById(id);
        return resultData;
    }
    @GetMapping("/courseware/download")
    @ApiOperation("下载课件")
    public ResultData oursewareDownload(@ApiParam("附件") String id, HttpServletResponse response){
        ResultData resultData= new ResultData();
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        resultData.setData("下载成功");
        resultData.setSuccess(true);
        EduTrainingClassAttachEntity eduTrainingClassAttach = eduTrainingClassAttachService.selectById(id);
        if (ObjectUtils.isEmpty(eduTrainingClassAttach)){
            resultData.setCode(SysConstant.FAIL);
            resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
            resultData.setSuccess(false);
            return resultData;
        }
        String fileUrl = eduTrainingClassAttach.getAttachUri();
        String fileName = eduTrainingClassAttach.getAttachTitle();
        try {
            UploadUtils.download(fileUrl,response,fileName);
        }catch (Exception e){
            resultData.setCode(SysConstant.FAIL);
            resultData.setMessage(e.getMessage());
            resultData.setData("下载失败");
            resultData.setSuccess(false);
            return resultData;
        }
        return resultData;
    }

    @GetMapping("/notify/download")
    @ApiOperation("下载通知")
    public ResultData notifyDownload(@ApiParam("附件id") String id, HttpServletResponse response){
        ResultData resultData= new ResultData();
        resultData.setCode(SysConstant.SUCCESS);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        resultData.setData("下载成功");
        resultData.setSuccess(true);
        EduTrainingClassAttachEntity eduTrainingClassAttach = eduTrainingClassAttachService.selectById(id);
        if (ObjectUtils.isEmpty(eduTrainingClassAttach)){
            resultData.setCode(SysConstant.FAIL);
            resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
            resultData.setSuccess(false);
            return resultData;
        }
        String fileUrl = eduTrainingClassAttach.getAttachUri();
        String fileName = eduTrainingClassAttach.getAttachTitle();
        try {
            UploadUtils.download(fileUrl,response,fileName);
        }catch (Exception e){
            resultData.setCode(SysConstant.FAIL);
            resultData.setMessage(e.getMessage());
            resultData.setData("下载失败");
            resultData.setSuccess(false);
            return resultData;
        }
        return resultData;
    }

    @GetMapping("/file/download")
    @ApiOperation("下载附件")
    public ResultData fileDownload(@ApiParam("附件id") String id, HttpServletResponse response){
        ResultData resultData= new ResultData();

        resultData.setCode(SysConstant.SUCCESS);
        resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
        resultData.setData("下载成功");
        resultData.setSuccess(true);
        EduTrainingClassAttachEntity eduTrainingClassAttach = eduTrainingClassAttachService.selectById(id);
        if (ObjectUtils.isEmpty(eduTrainingClassAttach)){
            resultData.setCode(SysConstant.FAIL);
            resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
            resultData.setSuccess(false);
            return resultData;
        }
        String fileUrl = eduTrainingClassAttach.getAttachUri();
        String fileName = eduTrainingClassAttach.getAttachTitle();
        try {
            UploadUtils.download(fileUrl,response,fileName);
        }catch (Exception e){
            resultData.setCode(SysConstant.FAIL);
            resultData.setMessage(e.getMessage());
            resultData.setData("下载失败");
            resultData.setSuccess(false);
            return resultData;
        }
        return resultData;
    }

    @PostMapping("/list")
    public ResultData<PageUtils> list(@RequestBody Map<String, Object> params){
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
            PageUtils pageUtils = eduTrainingClassAttachService.queryPage(params);
            resultData.setData(pageUtils);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setCode(SysConstant.SUCCESS);
            resultData.setSuccess(true);
            return resultData;
        }catch (Exception e) {
            resultData.setSuccess(false);
            resultData.setCode(SysConstant.PARAMETER_ERROR);
            resultData.setMessage(SysConstant.getMessage(SysConstant.PARAMETER_ERROR));
            return resultData;
        }
    }


    /**
     * 上传列表
     */
    @PostMapping("/upLoadList")
    @ApiOperation("上传列表")
    public ResultData<List<EduTrainingClassAttachEntity>> upLoadList(@RequestBody EduTrainingClassAttachListDto eduTrainingClassAttachListDto) {
        logger.info("查询上传列表：{}", JSON.toJSONString(eduTrainingClassAttachListDto));
        ResultData<List<EduTrainingClassAttachEntity>> resultData = new ResultData<>();
        try {
            resultData.setCode(SysConstant.SUCCESS);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setSuccess(true);
            if(BeanUtil.isNotEmpty(eduTrainingClassAttachListDto)){
                String classOrPlanId = eduTrainingClassAttachListDto.getClassOrPlanId();
                String attachType = eduTrainingClassAttachListDto.getAttachType();
                EntityWrapper<EduTrainingClassAttachEntity>  wrapper = new EntityWrapper();
                wrapper.eq("trainingClassId",classOrPlanId)
                        .eq("attachType",attachType);
                List<EduTrainingClassAttachEntity> eduTrainingClassAttachEntities = eduTrainingClassAttachService.selectList(wrapper);

                resultData.setData(eduTrainingClassAttachEntities);
            }
            return resultData;
        }catch (Exception e){
            logger.error("查询上传列表 异常: {}",e.getMessage());
            resultData.setMessage(SysConstant.getMessage(SysConstant.FAIL));
            resultData.setSuccess(false);
            resultData.setCode(SysConstant.FAIL);
            return resultData;
        }
    }
}
