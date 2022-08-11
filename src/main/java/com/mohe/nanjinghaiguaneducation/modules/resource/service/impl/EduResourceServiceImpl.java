package com.mohe.nanjinghaiguaneducation.modules.resource.service.impl;

import cn.hutool.http.HtmlUtil;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.mohe.nanjinghaiguaneducation.modules.resource.dao.EduResourceDao;
import com.mohe.nanjinghaiguaneducation.modules.resource.entity.EduResourceEntity;
import com.mohe.nanjinghaiguaneducation.modules.resource.entity.EduResourceTypeEntity;
import com.mohe.nanjinghaiguaneducation.modules.resource.service.EduResourceService;
import com.mohe.nanjinghaiguaneducation.modules.resource.service.EduResourceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mohe.nanjinghaiguaneducation.common.utils.PageUtils;
import com.mohe.nanjinghaiguaneducation.common.utils.Query;
import org.springframework.util.ObjectUtils;

@Service("eduResourceService")
public class EduResourceServiceImpl extends ServiceImpl<EduResourceDao, EduResourceEntity> implements EduResourceService {
    @Autowired
    private EduResourceTypeService eduResourceTypeService;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Wrapper<EduResourceEntity> eduResourceEntityEntityWrapper = new EntityWrapper<EduResourceEntity>();
//        if (!ObjectUtils.isEmpty(params.get("status"))){
//            eduResourceEntityEntityWrapper.eq("status",params.get("status"));
//        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Integer typeId = (Integer)params.get("typeId");
        if (typeId !=0){
            eduResourceEntityEntityWrapper.eq("typeId", params.get("typeId"));
        }
        if (!ObjectUtils.isEmpty(params.get("title"))){
            eduResourceEntityEntityWrapper.like("title",params.get("title").toString());
        }
        if (!ObjectUtils.isEmpty(params.get("startTime"))){
            eduResourceEntityEntityWrapper .where(" DATE_FORMAT(createTime,'%Y%m%d') >= DATE_FORMAT('"+simpleDateFormat.format(params.get("startTime"))
                    +"', '%Y%m%d') ");
        }
        if (!ObjectUtils.isEmpty(params.get("endTime"))){
            eduResourceEntityEntityWrapper.where(" DATE_FORMAT(createTime,'%Y%m%d') <= DATE_FORMAT('"+simpleDateFormat.format(params.get("endTime"))
                    +"', '%Y%m%d') ");
        }
        Page<EduResourceEntity> page = this.selectPage(
                new Query<EduResourceEntity>(params).getPage(),
                eduResourceEntityEntityWrapper
        );
        for (EduResourceEntity record : page.getRecords()) {
            EduResourceTypeEntity eduResourceTypeEntity = eduResourceTypeService.selectById(record.getTypeId());
            record.setTypeName(eduResourceTypeEntity.getName());
            //获取列表的时候，文章中的 html 标签过滤掉
            String contentWithoutHtml = EduResourceServiceImpl.guolvHtml(record.getContent());
            if("".equals(contentWithoutHtml)){
                record.setContent(record.getTitle());
            }else{
                record.setContent(contentWithoutHtml);
            }
        }
        return new PageUtils(page);
    }

//    public static void main(String[] args) {
//        String str = "<p>sdfsdfsfsdfsf</p>\n" +
//                "<h4>sdfsdfsf</h4>\n" +
//                "<img src=\"xxxxx\">";
//        String s = guolvHtml(str);
//        System.out.println(s);
//    }

    public static String guolvHtml(String s){
        if(ObjectUtils.isEmpty(s) || "".equals(s) ){
            return "";
        }
        return HtmlUtil.cleanHtmlTag(s);
    }

    @Override
    public PageUtils queryPageList(Map<String, Object> params) {
        Wrapper<EduResourceEntity> eduResourceEntityEntityWrapper = new EntityWrapper<>();
//        if (!ObjectUtils.isEmpty(params.get("status"))){
//            eduResourceEntityEntityWrapper.eq("status",params.get("status"));
//
        Page<EduResourceEntity> page = this.selectPage(
                new Query<EduResourceEntity>(params).getPage(),
                eduResourceEntityEntityWrapper.eq("isEnable",1).orderBy("createTime", false)
        );
        return new PageUtils(page);
    }

}
