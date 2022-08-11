package com.mohe.nanjinghaiguaneducation.common.h4a;

import cn.gov.customs.casp.sdk.h4a.BeanReaderHelper;
import cn.gov.customs.casp.sdk.h4a.OguXmlReaderHelper;
import cn.gov.customs.casp.sdk.h4a.accredit.ws.*;
import cn.gov.customs.casp.sdk.h4a.entity.ChildrenInRoles;
import cn.gov.customs.casp.sdk.h4a.entity.Roles;
import cn.gov.customs.casp.sdk.h4a.entity.UsersByFunctions;
import cn.gov.customs.casp.sdk.h4a.entity.UsersInRoles;
import cn.gov.customs.casp.sdk.h4a.enumdefines.AccreditCategory;
import cn.gov.customs.casp.sdk.h4a.enumdefines.DelegationCategories;
import cn.gov.customs.casp.sdk.h4a.enumdefines.RoleCategories;
import cn.gov.customs.casp.sdk.h4a.enumdefines.ViewCategory;
import cn.gov.customs.casp.sdk.h4a.ogu.ws.ObjectCategory;
//import cn.gov.customs.casp.sdk.h4a.ogu.ws.OrganizationCategory;
import cn.gov.customs.casp.sdk.h4a.passport.IAccreditBeanReader;
import cn.gov.customs.casp.sdk.h4a.util.H4aDefaultConstants;
import org.w3c.dom.Element;

public class Users {
    public static void main(String[] args) {

        //OrganizationCategory.NONE
        try {
//            Element xx= OguXmlReaderHelper.getXmlObjectsDetail(H4aDefaultConstants.DEFAULT_BASE_VIEW,
//                    ViewCategory.ViewCode, "nj2369&forms",
//                    ObjectCategory.USER_IDENTITY, "", cn.gov.customs.casp.sdk.h4a.ogu.ws.OrganizationCategory.NONE, "");
            Users users = new Users();
//            Roles[] beanRoles = users.getBeanRoles();
            UsersInRoles[] xies = users.getUsersInRoles("JYCZHK");
//            UsersByFunctions[] jyczhks = users.getBeanUsersByFunctions("FUNCTION_MAINTAIN");
            System.out.println(xies);
//            ChildrenInRoles[] glies = users.getChildrenInRoles("GLY");
//            System.out.println(glies);
//            System.out.println(beanRoles);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }

    //获取当前应用中所有的角色列表
    public Roles[] getBeanRoles() throws IAccreditReaderGetRolesCupaaFaultArgsFaultFaultMessage {
        try{
            IAccreditBeanReader iAccreditBeanReader = BeanReaderHelper.getIAccreditBeanReader();
            Roles[] beanRoles = iAccreditBeanReader.getBeanRoles(config.ROLE_LIST_EMPTY, AccreditCategory.None, config.APP_VALUE, AccreditCategory.Code,
                    config.VIEW_VALUE_BANGONG, AccreditCategory.Code, RoleCategories.All, "");
            return beanRoles;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    //获取当前角色中所有的用户
    public UsersInRoles[] getUsersInRoles(String roleValue) throws IAccreditReaderGetUsersInRolesCupaaFaultArgsFaultFaultMessage {
        IAccreditBeanReader iAccreditBeanReader = BeanReaderHelper.getIAccreditBeanReader();
        //附加字段取不出来，需要的话要单独调用接口
        return iAccreditBeanReader.getBeanUsersInRoles("", OrganizationCategory.NONE, config.APP_VALUE, AccreditCategory.Code,
                config.VIEW_VALUE_BANGONG, AccreditCategory.Code, roleValue, AccreditCategory.Code, DelegationCategories.All,
                config.USERS_EXT_PARAMS);//PERSON_ID,SIDELINE
    }

    public ChildrenInRoles[] getChildrenInRoles(String roleCode) throws IAccreditReaderGetChildrenInRolesCupaaFaultArgsFaultFaultMessage {
        IAccreditBeanReader iAccreditBeanReader = BeanReaderHelper.getIAccreditBeanReader();
        return iAccreditBeanReader.getBeanChildrenInRoles("", OrganizationCategory.NONE, config.APP_VALUE, AccreditCategory.Code,
                config.VIEW_VALUE_BANGONG, AccreditCategory.Code, roleCode, AccreditCategory.Code, DelegationCategories.All,
                config.USERS_EXT_PARAMS);
    }

    //获取单个用户的信息 bak
    public UsersByFunctions[] getBeanUsersByFunctions(String roleValue) throws IAccreditReaderGetUsersByFunctionsCupaaFaultArgsFaultFaultMessage {
//        IOguBeanReader oguBeanReaderHelper = BeanReaderHelper.getIOguBeanReader();
        IAccreditBeanReader iAccreditBeanReader = BeanReaderHelper.getIAccreditBeanReader();
        //附加字段取不出来，需要的话要单独调用接口
        UsersByFunctions[] beanUsersByFunctions = iAccreditBeanReader.getBeanUsersByFunctions("", OrganizationCategory.NONE, config.APP_VALUE, AccreditCategory.Code,
                config.VIEW_VALUE_BANGONG, AccreditCategory.Code, roleValue, AccreditCategory.Code, DelegationCategories.All,
                "");
        return beanUsersByFunctions;
    }



}
