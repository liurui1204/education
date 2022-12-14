package com.mohe.nanjinghaiguaneducation.modules.site.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mohe.nanjinghaiguaneducation.common.constant.SysConstant;
import com.mohe.nanjinghaiguaneducation.common.constant.SystemBusConst;
import com.mohe.nanjinghaiguaneducation.common.result.ResultData;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.modules.common.entity.EduSystemNoticeEntity;
import com.mohe.nanjinghaiguaneducation.modules.common.entity.EduSystemSettingsEntity;
import com.mohe.nanjinghaiguaneducation.modules.common.service.EduSystemNoticeService;
import com.mohe.nanjinghaiguaneducation.modules.common.service.EduSystemSettingsService;
import com.mohe.nanjinghaiguaneducation.modules.redBase.entity.EduRedBaseEntity;
import com.mohe.nanjinghaiguaneducation.modules.redBase.service.EduRedBaseService;
import com.mohe.nanjinghaiguaneducation.modules.resource.entity.EduResourceEntity;
import com.mohe.nanjinghaiguaneducation.modules.resource.service.EduResourceService;
import com.mohe.nanjinghaiguaneducation.modules.site.dto.EduSiteHomeAreaListDto;
import com.mohe.nanjinghaiguaneducation.modules.site.entity.EduTrainingElegantEntity;
import com.mohe.nanjinghaiguaneducation.modules.site.entity.EduTrainingNewsEntity;
import com.mohe.nanjinghaiguaneducation.modules.site.entity.EduTrainingResourceEntity;
import com.mohe.nanjinghaiguaneducation.modules.site.service.*;
import com.mohe.nanjinghaiguaneducation.modules.trainingBase.entity.EduTrainingBaseEntity;
import com.mohe.nanjinghaiguaneducation.modules.trainingBase.service.EduTrainingBaseService;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.entity.EduTrainingClassCourseEntity;
import com.mohe.nanjinghaiguaneducation.modules.trainingClass.service.EduTrainingClassCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequestMapping("/front/home")
@Api(tags = "???????????????-??????")
@RestController
public class EduSiteHomeController {

    @Autowired
    private EduSiteSwiperService eduSiteSwiperService;

    @Autowired
    private EduSiteLinksService eduSiteLinksService;

    @Autowired
    private EduTrainingNewsService eduTrainingNewsService;
    @Autowired
    private EduTrainingElegantService eduTrainingElegantService;
    @Autowired
    private EduSystemNoticeService eduSystemNoticeService;
    @Autowired
    private EduResourceService eduResourceService;
    @Autowired
    private EduTrainingClassCourseService eduTrainingClassCourseService;

    @Autowired
    private EduTrainingBaseService eduTrainingBaseService;
    @Autowired
    private EduRedBaseService eduRedBaseService;

    @Autowired
    private EduSystemSettingsService eduSystemSettingsService;

    @Autowired
    private EduTrainingResourceService eduTrainingResourceService;

    @PostMapping("/scroll")
    @ApiOperation("???????????????")
    public ResultData<PageUtils> scroll(){
        ResultData<PageUtils> result = new ResultData<>();
        try {
            HashMap<String, Object> params = new HashMap<>();
            PageUtils pageUtils = eduSiteSwiperService.queryPage(params);
            result.setData(pageUtils);
            result.setSuccess(true);
            result.setCode(SysConstant.SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            result.setSuccess(false);
            result.setCode(SysConstant.FAIL);
            result.setMessage(SysConstant.getMessage(SysConstant.FAIL));
        }
        return result;
    }

    @PostMapping("/links")
    @ApiOperation("????????????")
    public ResultData<PageUtils> links(){
        ResultData<PageUtils> result = new ResultData<>();
        try {
            HashMap<String, Object> params = new HashMap<>();
            PageUtils pageUtils = eduSiteLinksService.queryPage(params);
            result.setData(pageUtils);
            result.setSuccess(true);
            result.setCode(SysConstant.SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            result.setSuccess(false);
            result.setCode(SysConstant.FAIL);
            result.setMessage(SysConstant.getMessage(SysConstant.FAIL));
        }
        return result;
    }

    @PostMapping("/areasList")
    @ApiOperation("????????????/????????????/????????????/????????????/???????????? ??????")
    public ResultData<EduSiteHomeAreaListDto> getAreaList(){
        ResultData<EduSiteHomeAreaListDto> result = new ResultData<>();
        EduSiteHomeAreaListDto eduSiteHomeAreaListDto = new EduSiteHomeAreaListDto();
        try {
            HashMap<String, Object> params = new HashMap<>();
            PageUtils pageUtils = eduSiteLinksService.queryPage(params);
            //??????????????????
            //????????????
            eduSiteHomeAreaListDto.setEduTrainingNewsEntities(eduTrainingNewsService.selectList(
                    new EntityWrapper<EduTrainingNewsEntity>().eq("isEnable",1)
                            .eq("displayInHome", 1).orderBy("createTime", false)));
            //????????????
            eduSiteHomeAreaListDto.setTrainingElegantEntities(eduTrainingElegantService.selectList(
                    new EntityWrapper<EduTrainingElegantEntity>().eq("isEnable",1).eq("confirmStatus", 3)
                            .eq("displayInHome", 1).orderBy("createTime", false)));
            //????????????
            eduSiteHomeAreaListDto.setEduSystemNoticeEntities(eduSystemNoticeService.selectList(
                    new EntityWrapper<EduSystemNoticeEntity>().eq("isEnable",1)
                            .orderBy("`order`,`createTime`", false)));
            //????????????
            eduSiteHomeAreaListDto.setEduResourceEntities(eduResourceService.selectList(
                    new EntityWrapper<EduResourceEntity>().eq("isEnable",1).orderBy("createTime", false)));
            //????????????
            eduSiteHomeAreaListDto.setEduTrainingClassCourseEntities(eduTrainingClassCourseService.selectList(
                    new EntityWrapper<EduTrainingClassCourseEntity>().eq("isEnable",1)
                            .eq("isQuality", 1).orderBy("createTime", false)));
            //????????????
            eduSiteHomeAreaListDto.setEduTrainingResourceEntities(eduTrainingResourceService.selectList(
                    new EntityWrapper<EduTrainingResourceEntity>().eq("isEnable",1)
                    .orderBy("createTime",false)
            ));
            //??????????????????
            /**EduSystemSettingsEntity imageValue = eduSystemSettingsService.selectOne(new EntityWrapper<EduSystemSettingsEntity>()
                    .eq("`key`", SystemBusConst.HOME_PAGE_TRAINING_NEWS_IMAGE_KEY));
            if("".equals(imageValue.getValue())){
                eduSiteHomeAreaListDto.setEduTrainingNewsImage("??????????????????????????????????????????");
            }else{
                eduSiteHomeAreaListDto.setEduTrainingNewsImage(imageValue.getValue());
            }*/
            ArrayList<Map<String, Object>> imageList = new ArrayList<>();
            for(EduTrainingNewsEntity news : eduSiteHomeAreaListDto.getEduTrainingNewsEntities()){
                String imageUrlFromString = this.getImageUrlFromString(news.getContent());
                if(!"".equals(imageUrlFromString)){
                    HashMap<String, Object> tmpMap = new HashMap<>();
                    tmpMap.put("id", news.getId());
                    tmpMap.put("title",news.getTitle());
                    tmpMap.put("imageUrl", imageUrlFromString);
                    imageList.add(tmpMap);
                    if(imageList.size()>=5){//????????????5??????????????????
                        break;
                    }
                }
            }
            eduSiteHomeAreaListDto.setEduTrainingNewsImages(imageList);
            System.out.println(eduSiteHomeAreaListDto);
            result.setData(eduSiteHomeAreaListDto);
            result.setSuccess(true);
            result.setCode(SysConstant.SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            result.setSuccess(false);
            result.setCode(SysConstant.FAIL);
            result.setMessage(SysConstant.getMessage(SysConstant.FAIL));
        }
        return result;
    }
    @ApiOperation("????????????")
    @PostMapping("/courseList")
    public ResultData courseList(@RequestBody Map<String,Object> params){

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
            PageUtils pageUtils = eduTrainingClassCourseService.queryPageList(params);

            resultData.setData(pageUtils);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setCode(SysConstant.SUCCESS);
            resultData.setSuccess(true);
            return resultData;
        }catch (Exception e) {
            e.printStackTrace();
            resultData.setSuccess(false);
            resultData.setCode(SysConstant.PARAMETER_ERROR);
            resultData.setMessage(SysConstant.getMessage(SysConstant.PARAMETER_ERROR));
            return resultData;
        }
    }

    @ApiOperation("???????????????????????????????????????")
    @PostMapping("/resourceList")
    public ResultData resourceList(@RequestBody Map<String,Object> params){
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
            PageUtils pageUtils = eduResourceService.queryPageList(params);
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

    @PostMapping("/noticeList")
    @ApiOperation("????????????")
    public ResultData noticeList(@RequestBody Map<String,Object> params){
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
            PageUtils pageUtils = eduSystemNoticeService.queryPage(params);

            resultData.setData(pageUtils);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setCode(SysConstant.SUCCESS);
            resultData.setSuccess(true);
            return resultData;
        }catch (Exception e) {
            e.printStackTrace();
            resultData.setSuccess(false);
            resultData.setCode(SysConstant.PARAMETER_ERROR);
            resultData.setMessage(SysConstant.getMessage(SysConstant.PARAMETER_ERROR));
            return resultData;
        }
    }

    @PostMapping("/elegantList")
    @ApiOperation("??????????????????")
    public ResultData elegantList(@RequestBody Map<String, Object> params, HttpServletRequest request) {
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
            PageUtils pageUtils = eduTrainingElegantService.queryPageList(params);

            resultData.setData(pageUtils);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setCode(SysConstant.SUCCESS);
            resultData.setSuccess(true);
            return resultData;
        } catch (Exception e) {
            e.printStackTrace();
            resultData.setSuccess(false);
            resultData.setCode(SysConstant.PARAMETER_ERROR);
            resultData.setMessage(SysConstant.getMessage(SysConstant.PARAMETER_ERROR));
            return resultData;
        }
    }

    @PostMapping("/newsList")
    @ApiOperation("??????????????????")
    public ResultData newsList(@RequestBody Map<String,Object> params){
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
            PageUtils pageUtils = eduTrainingNewsService.queryPageList(params);

            resultData.setData(pageUtils);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setCode(SysConstant.SUCCESS);
            resultData.setSuccess(true);
            return resultData;
        }catch (Exception e) {
            e.printStackTrace();
            resultData.setSuccess(false);
            resultData.setCode(SysConstant.PARAMETER_ERROR);
            resultData.setMessage(SysConstant.getMessage(SysConstant.PARAMETER_ERROR));
            return resultData;
        }
    }

    @PostMapping("/trainingSource")
    @ApiOperation("??????????????????")
    public ResultData trainingSource(@RequestBody Map<String,Object> params){
        ResultData resultData = new ResultData();
        try{
            Integer page = (Integer) params.get("page");
            Integer limit = (Integer) params.get("limit");
            if (page == null || limit == null
                    || page < 1 || limit < 1) {
                resultData.setSuccess(false);
                resultData.setCode(SysConstant.PARAMETER_ERROR);
                resultData.setMessage(SysConstant.getMessage(SysConstant.PARAMETER_ERROR));
                return resultData;
            }
            PageUtils pageUtils = eduTrainingResourceService.queryPage(params);
            resultData.setData(pageUtils);
            resultData.setMessage(SysConstant.getMessage(SysConstant.SUCCESS));
            resultData.setCode(SysConstant.SUCCESS);
            resultData.setSuccess(true);
            return resultData;
        }catch (Exception e){
            e.printStackTrace();
            resultData.setSuccess(false);
            resultData.setCode(SysConstant.PARAMETER_ERROR);
            resultData.setMessage(SysConstant.getMessage(SysConstant.PARAMETER_ERROR));
            return resultData;
        }
    }


    //??????????????????????????????
    private String getImageUrlFromString(String content){
        Pattern p_img = Pattern.compile("<(img|IMG)(.*?)(/>|></img>|>)");
        Matcher m_img = p_img.matcher(content);
        boolean result_img = m_img.find();
        if (result_img) {
            //??????????????????<img />??????????????????
            String str_img = m_img.group(2);
            //????????????<img />????????????src
            Pattern p_src = Pattern.compile("(src|SRC)=(\"|\')(.*?)(\"|\')");
            Matcher m_src = p_src.matcher(str_img);
            if (m_src.find()) {
                return m_src.group(3);
            }
        }
        return "";
    }

    @PostMapping("/content/tree")
    @ApiOperation("?????????????????? - ????????????")
    public ResultData getContentTree(){
        //TODO ?????????
        return null;
    }

    @PostMapping("/trainingBasicList")
    @ApiOperation("??????????????????")
    public ResultData<PageUtils> getTrainingBasicList(){
        ResultData<PageUtils> result = new ResultData<>();
        try {
            HashMap<String, Object> params = new HashMap<>();
            PageUtils pageUtils = eduTrainingBaseService.queryPage(params);
            result.setData(pageUtils);
            result.setSuccess(true);
            result.setCode(SysConstant.SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            result.setSuccess(false);
            result.setCode(SysConstant.FAIL);
            result.setMessage(SysConstant.getMessage(SysConstant.FAIL));
        }
        return result;
    }

    @PostMapping("/redBaseList")
    @ApiOperation("??????????????????")
    public ResultData<PageUtils> getRedBaseList(){
        ResultData<PageUtils> result = new ResultData<>();
        try {
            HashMap<String, Object> params = new HashMap<>();
//            PageUtils pageUtils = eduTrainingBaseService.queryPage(params);
            params.put("isEnable",1);
            PageUtils pageUtils = eduRedBaseService.queryPage(params);
            result.setData(pageUtils);
            result.setSuccess(true);
            result.setCode(SysConstant.SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            result.setSuccess(false);
            result.setCode(SysConstant.FAIL);
            result.setMessage(SysConstant.getMessage(SysConstant.FAIL));
        }
        return result;
    }

    @PostMapping("/count")
    public ResultData count(){
        Map map = new HashMap();
        map.put("baseCount",eduTrainingBaseService.selectCount(new EntityWrapper<EduTrainingBaseEntity>()
                .eq("isEnable",1)));
        map.put("redBaseCount",eduRedBaseService.selectCount(new EntityWrapper<EduRedBaseEntity>()
                .eq("isEnable",1)));
        ResultData resultData = new ResultData();
        resultData.setData(map);
        resultData.setSuccess(true);
        resultData.setCode(SysConstant.SUCCESS);
        return resultData;
    }
}
