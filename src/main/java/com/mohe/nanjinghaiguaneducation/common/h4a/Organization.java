package com.mohe.nanjinghaiguaneducation.common.h4a;

import cn.gov.customs.casp.sdk.h4a.BeanReaderHelper;
import cn.gov.customs.casp.sdk.h4a.entity.OrganizationChildren;
import cn.gov.customs.casp.sdk.h4a.entity.UserIdentityInfos;
import cn.gov.customs.casp.sdk.h4a.entity.UsersInGroups;
import cn.gov.customs.casp.sdk.h4a.enumdefines.ListObjectCategories;
import cn.gov.customs.casp.sdk.h4a.enumdefines.ObjectStatusCategories;
import cn.gov.customs.casp.sdk.h4a.enumdefines.ViewCategory;
import cn.gov.customs.casp.sdk.h4a.ogu.ws.*;
import cn.gov.customs.casp.sdk.h4a.passport.IAccreditBeanReader;
import cn.gov.customs.casp.sdk.h4a.passport.IOguBeanReader;

public class Organization {
    public static void main(String[] args) {
//        String testValue = "中国海关\\南京海关\\靖江海1关";
//        String xxx = testValue.substring( "中国海关\\南京海关\\".length());
//        boolean res = xxx.contains("海关" );
//        System.out.println(res);
//        String testValue = "中国海关\\南京海关\\审单处\\报关单删改接受岗";
//        String testValue = "中国海关\\南京海关\\苏州海关\\太仓海关\\办公室\\太仓收发";
        //String testValue = "中国海关\\南京海关\\关税处\\减免税科";
//        String testValue = "中国海关\\南京海关";
        String testValue = "中国海关\\南京海关\\苏州海关\\监管通关处\\审单科";
        //中国海关\南京海关\总关收文系统管理员
        //中国海关\南京海关\内网办理系统业务咨询管理员
        //中国海关\南京海关\内网办理系统留言板管理员
        //中国海关\南京海关\内网办理系统关长信箱管理员
        //中国海关\南京海关\内网办理系统其他管理员
        //中国海关\南京海关\内网办理系统廉政举报管理员
        //中国海关\南京海关\内网办理系统走私违规举报管理员
        //中国海关\南京海关\内网办理系统信访管理员
        //中国海关\南京海关\内网办理系统联系我们管理员
        try {
            Organization org = new Organization();
//            OrganizationChildren[] beanOrganizationChildren = org.getBeanOrganizationChildren(testValue);
//            System.out.println(beanOrganizationChildren);
//            String ttValue = "中国海关\\南京海关\\风险管理处\\职能部门HZ2011联络员";
//            UsersInGroups[] beanUsersInGroupsByAllPath = org.getBeanUsersInGroupsByAllPath(ttValue);
//            System.out.println(beanUsersInGroupsByAllPath);
//            UserIdentityInfos[] userIdentityInfos = org.getUserIdentityInfos();
//            System.out.println(userIdentityInfos);
            OrganizationChildren[] beanOrganizationChildren = org.getBeanOrganizationChildrenByAllPath(testValue);
            int i = 1;
            for(OrganizationChildren child : beanOrganizationChildren){
                System.out.println("("+i+")objectclass:"+child.getObjectclass()+",allPathName:"+child.getAll_path_name()
                        +",displayName:"+child.getDisplay_name()+",guid:"+child.getGroup_guid()+" # "+child.getOrg_guid()+" # "+child.getUser_guid());
                i++;
            }
            System.out.println(beanOrganizationChildren);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * 查询用户在各类认证方式下的登录信息
     * @return
     * @throws IOguReaderGetUserIdentityInfosCupaaFaultArgsFaultFaultMessage
     */
    public UserIdentityInfos[] getUserIdentityInfos() throws IOguReaderGetUserIdentityInfosCupaaFaultArgsFaultFaultMessage {
        IOguBeanReader oguBeanReaderHelper = BeanReaderHelper.getIOguBeanReader();
        return oguBeanReaderHelper.getBeanUserIdentityInfos("admin&forms", UserCategory.USER_IDENTITY);
    }

    //"中国海关\\南京海关"
    public OrganizationChildren[] getBeanOrganizationChildrenByAllPath(String allPath) throws IOguReaderGetOrganizationChildrenCupaaFaultArgsFaultFaultMessage {
        IOguBeanReader oguBeanReaderHelper = BeanReaderHelper.getIOguBeanReader();
        return oguBeanReaderHelper.getBeanOrganizationChildren(config.VIEW_VALUE_BANGONG,//指定的视角标识
                ViewCategory.ViewCode,//查询视角使用的枚举
                allPath,//要求查询的部门对象(父部门标识,多个之间采用","分隔)
                OrganizationCategory.ORG_ALL_PATH_NAME, //查询机构的属性使用的枚举,见定义
                ListObjectCategories.All, //要求查询的数据对象类型,见定义
                ObjectStatusCategories.Common, //是否包含被逻辑删除的成员
                1,//要求查询的层次（最少一层）
                "", "", "", 0, config.USERS_EXT_PARAMS
        );
    }

    //"中国海关\\南京海关"
    public OrganizationChildren[] getBeanOrganizationChildren(String allPath) throws IOguReaderGetOrganizationChildrenCupaaFaultArgsFaultFaultMessage {
        IOguBeanReader oguBeanReaderHelper = BeanReaderHelper.getIOguBeanReader();
        return oguBeanReaderHelper.getBeanOrganizationChildren(config.VIEW_VALUE_BANGONG,//指定的视角标识
                ViewCategory.ViewCode,//查询视角使用的枚举
                allPath,//要求查询的部门对象(父部门标识,多个之间采用","分隔)
                OrganizationCategory.ORG_ALL_PATH_NAME, //查询机构的属性使用的枚举,见定义
                ListObjectCategories.All, //要求查询的数据对象类型,见定义
                ObjectStatusCategories.Common, //是否包含被逻辑删除的成员
                1,//要求查询的层次（最少一层）
                "", "", "", 0, config.USERS_EXT_PARAMS
        );
    }

    public UsersInGroups[] getBeanUsersInGroupsByAllPath(String groupValue) throws IOguReaderGetUsersInGroupsCupaaFaultArgsFaultFaultMessage {
        IOguBeanReader oguBeanReaderHelper = BeanReaderHelper.getIOguBeanReader();
        return oguBeanReaderHelper.getBeanUsersInGroups(config.VIEW_VALUE_BANGONG,//指定的视角标识
                ViewCategory.ViewCode,//查询视角使用的枚举
                groupValue,//要求查询的部门对象(父部门标识,多个之间采用","分隔)
                GroupCategory.GROUP_ALL_PATH_NAME, //查询机构的属性使用的枚举,见定义
                "",
                ObjectStatusCategories.Common,config.USERS_EXT_PARAMS
        );
    }

    //objectclass有三种  USERS / GROUPS / ORGANIZATIONS
    public OrganizationChildren[] getBeanAllChildrenByOrgGuid(String orgGuid) throws IOguReaderGetOrganizationChildrenCupaaFaultArgsFaultFaultMessage {
        IOguBeanReader oguBeanReaderHelper = BeanReaderHelper.getIOguBeanReader();
        return oguBeanReaderHelper.getBeanOrganizationChildren(config.VIEW_VALUE_BANGONG,//指定的视角标识
                ViewCategory.ViewCode,//查询视角使用的枚举
                orgGuid,//要求查询的部门对象(父部门标识,多个之间采用","分隔)
                OrganizationCategory.ORG_GUID, //查询机构的属性使用的枚举,见定义
                ListObjectCategories.All, //要求查询的数据对象类型,见定义
                ObjectStatusCategories.Common, //是否包含被逻辑删除的成员
                1,//要求查询的层次（最少一层）
                "", "", "", 0, "OBJ_NAME,GLOBLA_SORT,CUSTOMS_CODE"
        );
    }
}
