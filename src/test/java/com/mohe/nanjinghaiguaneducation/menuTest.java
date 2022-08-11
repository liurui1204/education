package com.mohe.nanjinghaiguaneducation;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mohe.nanjinghaiguaneducation.common.utils.SurnameUtil;
import com.mohe.nanjinghaiguaneducation.modules.common.entity.EduSystemAuthorityEntity;
import com.mohe.nanjinghaiguaneducation.modules.common.entity.EduSystemAuthorityRoleEntity;
import com.mohe.nanjinghaiguaneducation.modules.common.service.EduSystemAuthorityRoleService;
import com.mohe.nanjinghaiguaneducation.modules.common.service.EduSystemAuthorityService;
import com.mohe.nanjinghaiguaneducation.modules.teacherInner.entity.EduInnerTeacherEntity;
import com.mohe.nanjinghaiguaneducation.modules.teacherInner.service.EduInnerTeacherService;
import com.mohe.nanjinghaiguaneducation.modules.teacherOuter.entity.EduOuterTeacher;
import com.mohe.nanjinghaiguaneducation.modules.teacherOuter.service.EduOutTeacherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.annotation.Id;

import java.util.*;
import java.util.stream.Collectors;

@SpringBootTest
public class menuTest {

    @Autowired
    private EduInnerTeacherService eduInnerTeacherService;
    @Autowired
    private EduOutTeacherService eduOutTeacherService;

//    @Test
//    public void strokesNum(){
//        List<EduOuterTeacher> eduInnerTeacherEntities = eduOutTeacherService.selectList(new EntityWrapper<>());
//        for (EduOuterTeacher eduInnerTeacherEntity : eduInnerTeacherEntities) {
//            //通过工具类截取姓氏
//            List<String> list = SurnameUtil.nameSplit(eduInnerTeacherEntity.getTeacherName());
//            String surname = list.get(0);
//            //给他转换成字符的数组
//            char[] words = surname.toCharArray();
//            int count = 0;
//            //因为有复姓这个东西 所以是要遍历
//            for (int i = 0; i < words.length; i++) {
//                int returnCount=  SurnameUtil.getStrokeCount(words[i]);
//                if (returnCount > 0) {
//                    count += returnCount;
//                }
//            }
//            eduInnerTeacherEntity.setStrokesNum(count);
//            eduOutTeacherService.updateById(eduInnerTeacherEntity);
//        }
//    }

//    @Test
//    public void strokesInnerNum(){
//        List<EduInnerTeacherEntity> eduInnerTeacherEntities = eduInnerTeacherService.selectList(new EntityWrapper<>());
//        for (EduInnerTeacherEntity eduInnerTeacherEntity : eduInnerTeacherEntities) {
//            //通过工具类截取姓氏
//            List<String> list = SurnameUtil.nameSplit(eduInnerTeacherEntity.getTeacherName());
//            String surname = list.get(0);
//            //给他转换成字符的数组
//            char[] words = surname.toCharArray();
//            int count = 0;
//            //因为有复姓这个东西 所以是要遍历
//            for (int i = 0; i < words.length; i++) {
//                int returnCount=  SurnameUtil.getStrokeCount(words[i]);
//                if (returnCount > 0) {
//                    count += returnCount;
//                }
//            }
//            eduInnerTeacherEntity.setStrokesNum(count);
//            eduInnerTeacherService.updateById(eduInnerTeacherEntity);
//        }
//    }
//    @Autowired
//    private EduSystemAuthorityRoleService eduSystemAuthorityRoleService;
//
//    @Autowired
//    private EduSystemAuthorityService eduSystemAuthorityService;
//
//
//    public static String roleCode = "JGCSLD";
//
//    @Test
//    public void getAuthorityTreeTest(){
//        //获取该角色拥有的权限id list
//        List<EduSystemAuthorityRoleEntity> authorityRoleEntities = eduSystemAuthorityRoleService.selectList(new EntityWrapper<EduSystemAuthorityRoleEntity>().eq("roleCode", menuTest.roleCode));
//        List<String> collect = authorityRoleEntities.stream().map(x -> x.getAuthorityId()).collect(Collectors.toList());
//        EntityWrapper<EduSystemAuthorityEntity> filter = new EntityWrapper<EduSystemAuthorityEntity>();
//        filter.in("id",  collect);
//        List<EduSystemAuthorityEntity> eduSystemAuthorityEntities = eduSystemAuthorityService.selectList(filter);
//        ArrayList<Map<String, Object>> finalList = new ArrayList<>();
//        for(EduSystemAuthorityEntity auth : eduSystemAuthorityEntities){
//            String fullPath = auth.getName();//full path
//            String[] tmpArr = fullPath.split("_");
//            if(tmpArr.length<=3){
//                continue;
//            }
//            //meta
//            String lv1Title = tmpArr[2];
//            //如果当前的 lv1 的菜单，已经存在，直接跳过
//            boolean jump  = false;
//            for (Map<String, Object> stringObjectMap : finalList) {
//                if(stringObjectMap == null){
//                    continue;
//                }
//                Map<String, String> mata = (Map<String, String>) stringObjectMap.get("meta");
//                if(lv1Title.equals(mata.get("title"))){
//                    jump = true;
//                    break;
//                }
//            }
//            if(jump){
//                continue;
//            }
//            Map<String, Object> _map = new HashMap<>();
//            Map<String, String> metaMap = new HashMap<>();
//            metaMap.put("title", lv1Title);
//            _map.put("meta", metaMap);
//
//            Map<String, String> commonMap = new HashMap<>();
//            commonMap.put("title", lv1Title);
//
//            //child
//            List<Map<String, Object>> childList = new ArrayList<>();
//            String _searchKey = tmpArr[0]+"_"+tmpArr[1]+"_"+tmpArr[2]+"_";
//            for (EduSystemAuthorityEntity subAuth : eduSystemAuthorityEntities){
//                if(subAuth.getName().contains(_searchKey)){
//                    Map<String, Object> _xxMap = new HashMap<>();
//                    //属于当前 二级目录
//                    _xxMap.put("name", subAuth.getName().substring(_searchKey.length()));
//                    _xxMap.put("code",  subAuth.getCode());
//                    _xxMap.put("path",  subAuth.getPath().equals("")?"取数据库里加一下":subAuth.getPath());
//                    _xxMap.put("authorityType",  subAuth.getAuthorityType());
//                    _xxMap.put("meta", commonMap);
//                    childList.add(_xxMap);
//                }
//            }
//            _map.put("children", childList);
//            finalList.add(_map);
//        }
//        System.out.println(JSON.toJSONString(finalList));
//
//    }
//    @Test
//    public void add(){
//        EduSystemAuthorityRoleEntity entity = new EduSystemAuthorityRoleEntity();
//        entity.setId(IdUtil.simpleUUID());
//        entity.setAuthorityCode("JM_HTWH_GQPXGLXS_PGSZ");
//        entity.setAuthorityId("4f2c7cb2087043b4a26518c8db22e7d1");
//        entity.setRoleCode("JGCSLLY");
//        entity.setRolesId("6c1ebc08e50448aa9f45de349a276a41");
//        eduSystemAuthorityRoleService.insert(entity);
//    }

    /**
     * 新增菜单并给角色附权限
     */
//    @Autowired
//    private EduSystemAuthorityService eduSystemAuthorityService;
//    @Autowired
    private EduSystemAuthorityRoleService eduSystemAuthorityRoleService;
//    @Test
//    void addMenuAndRole(){
//        EduSystemAuthorityEntity eduSystemAuthorityEntity = new EduSystemAuthorityEntity();
//        EduSystemAuthorityRoleEntity eduSystemAuthorityRoleEntity = new EduSystemAuthorityRoleEntity();
//        eduSystemAuthorityEntity.setId(IdUtil.simpleUUID());
//        eduSystemAuthorityEntity.setName("界面_后台维护_首页展示_成果展示");
//        eduSystemAuthorityEntity.setCode("JM_HTWH_SYZS_CGZS");
//        eduSystemAuthorityEntity.setPath("/index/latestResources");
//        eduSystemAuthorityEntity.setAuthorityType(1);
//        eduSystemAuthorityEntity.setHidden(0);
//        eduSystemAuthorityEntity.setLastUpdateTime(new Date());
//        eduSystemAuthorityEntity.setType(1);
//        eduSystemAuthorityEntity.setOrder(2150);
//        boolean insert = eduSystemAuthorityService.insert(eduSystemAuthorityEntity);
//        if (insert){
//            eduSystemAuthorityRoleEntity.setId(IdUtil.simpleUUID());
//            eduSystemAuthorityRoleEntity.setAuthorityId(eduSystemAuthorityEntity.getId());
//            eduSystemAuthorityRoleEntity.setRolesId("a944009df1fc45d7ad6df9016b5e4d79");
//            eduSystemAuthorityRoleEntity.setAuthorityCode("JM_HTWH_SYZS_CGZS");
//            eduSystemAuthorityRoleEntity.setRoleCode("GLY");
//            eduSystemAuthorityRoleService.insert(eduSystemAuthorityRoleEntity);
//        }
//    }
    /**
     * 给某个角色新增菜单权限
     @Test
     //    void addRoleAuth(){
     //        EduSystemAuthorityRoleEntity eduSystemAuthorityRoleEntity = new EduSystemAuthorityRoleEntity();
     //        eduSystemAuthorityRoleEntity.setId(IdUtil.simpleUUID());
     //        System.out.println(IdUtil.simpleUUID());
     ////        eduSystemAuthorityRoleEntity.setAuthorityCode("JM_HTWH_GQPXGLXS_PGSZ");
     ////        eduSystemAuthorityRoleEntity.setRolesId("ac0bc1d79a8b4383ac6d0e8d9c0fa619");
     ////        eduSystemAuthorityRoleEntity.setRoleCode("JYCPXK");
     ////        eduSystemAuthorityRoleEntity.setAuthorityId("4f2c7cb2087043b4a26518c8db22e7d1");
     ////        eduSystemAuthorityRoleService.insert(eduSystemAuthorityRoleEntity);
     //    }     */
//

}
