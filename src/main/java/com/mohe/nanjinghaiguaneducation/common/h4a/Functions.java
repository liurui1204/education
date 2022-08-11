package com.mohe.nanjinghaiguaneducation.common.h4a;

import cn.gov.customs.casp.sdk.h4a.BeanReaderHelper;
import cn.gov.customs.casp.sdk.h4a.accredit.ws.IAccreditReaderGetFunctionsInApplicationCupaaFaultArgsFaultFaultMessage;
import cn.gov.customs.casp.sdk.h4a.accredit.ws.IAccreditReaderGetFunctionsInRolesCupaaFaultArgsFaultFaultMessage;
import cn.gov.customs.casp.sdk.h4a.entity.FunctionsInApplication;
import cn.gov.customs.casp.sdk.h4a.entity.FunctionsInRoles;
import cn.gov.customs.casp.sdk.h4a.enumdefines.AccreditCategory;
import cn.gov.customs.casp.sdk.h4a.enumdefines.FunctionCategories;
import cn.gov.customs.casp.sdk.h4a.passport.IAccreditBeanReader;

public class Functions {
    public static void main(String[] args) {
        try{
            Functions function = new Functions();
//            String groupCode = "";// 选择权限组来获取
//            FunctionsInApplication[] beanFunctionsInApplication = function.getBeanFunctionsInApplication(groupCode);
//            for(FunctionsInApplication xx : beanFunctionsInApplication){
//                System.out.println(xx.getFunc_name() +" =>"+ xx.getCode_name());
//            }
//            System.out.println(beanFunctionsInApplication);

            String roleValue = "JGCSLD";
            FunctionsInRoles[] beanFunctionsInRoles = function.getBeanFunctionsInRoles(roleValue);
            System.out.println(beanFunctionsInRoles);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    /**
     * 获取功能——根据功能标识、应用标识、功能类型获取满足条件的功能信息。
     * @param  groupCode 如果不传就是获取所有的权限列表  如果传了就是获取当前Code的权限详细信息，没啥卵用
     * @return
     * @throws IAccreditReaderGetFunctionsInApplicationCupaaFaultArgsFaultFaultMessage
     */
    public FunctionsInApplication[] getBeanFunctionsInApplication(String groupCode) throws IAccreditReaderGetFunctionsInApplicationCupaaFaultArgsFaultFaultMessage {
        IAccreditBeanReader iAccreditBeanReader = BeanReaderHelper.getIAccreditBeanReader();
        return iAccreditBeanReader.getBeanFunctionsInApplication(groupCode, AccreditCategory.Code, config.APP_VALUE,
                AccreditCategory.Code, FunctionCategories.Exterior, "");
    }

    /**
     * 根据角色获取 角色相关的权限列表
     * @param roleValue 角色的英文标识
     * @return
     * @throws IAccreditReaderGetFunctionsInRolesCupaaFaultArgsFaultFaultMessage
     */
    public FunctionsInRoles[] getBeanFunctionsInRoles(String roleValue) throws IAccreditReaderGetFunctionsInRolesCupaaFaultArgsFaultFaultMessage {
        IAccreditBeanReader iAccreditBeanReader = BeanReaderHelper.getIAccreditBeanReader();
        return iAccreditBeanReader.getBeanFunctionsInRoles(config.APP_VALUE, AccreditCategory.Code, config.VIEW_VALUE_BANGONG,
                AccreditCategory.Code, roleValue, AccreditCategory.Code, "");
    }
}
