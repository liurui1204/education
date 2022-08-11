package com.mohe.nanjinghaiguaneducation.h4a;

import cn.gov.customs.casp.config.ConfigReader;
import cn.gov.customs.casp.sdk.h4a.BeanReaderHelper;
import cn.gov.customs.casp.sdk.h4a.accredit.ws.IAccreditReaderGetFunctionsOfUserCupaaFaultArgsFaultFaultMessage;
import cn.gov.customs.casp.sdk.h4a.accredit.ws.UserCategory;
import cn.gov.customs.casp.sdk.h4a.entity.FunctionsOfUser;
import cn.gov.customs.casp.sdk.h4a.entity.ObjectParentOrganizations;
import cn.gov.customs.casp.sdk.h4a.entity.ObjectsDetail;
import cn.gov.customs.casp.sdk.h4a.enumdefines.AccreditCategory;
import cn.gov.customs.casp.sdk.h4a.enumdefines.DelegationCategories;
import cn.gov.customs.casp.sdk.h4a.enumdefines.FunctionCategories;
import cn.gov.customs.casp.sdk.h4a.enumdefines.ViewCategory;
import cn.gov.customs.casp.sdk.h4a.ogu.ws.IOguReaderGetObjectParentOrganizationsCupaaFaultArgsFaultFaultMessage;
import cn.gov.customs.casp.sdk.h4a.ogu.ws.IOguReaderGetObjectsDetailCupaaFaultArgsFaultFaultMessage;
import cn.gov.customs.casp.sdk.h4a.ogu.ws.ObjectCategory;
import cn.gov.customs.casp.sdk.h4a.ogu.ws.OrganizationCategory;
import cn.gov.customs.casp.sdk.h4a.passport.IAccreditBeanReader;
import cn.gov.customs.casp.sdk.h4a.passport.IOguBeanReader;
import cn.gov.customs.casp.sdk.h4a.sso.passport.Ticket;
import cn.gov.customs.casp.sdk.h4a.util.H4aDefaultConstants;
import org.springframework.boot.test.context.SpringBootTest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
public class H4aSSOTest {

    public static void main(String[] args) {
        String objectDetailsLastParam = ConfigReader.getConfigValue("h4a-config.xml", "object_param", "parameter_obj", "getBeanObjectsDetail_lastParam");
        System.out.println(objectDetailsLastParam);
        String organizationLastParam = ConfigReader.getConfigValue("h4a-config.xml", "object_param", "parameter_obj", "organizationCategory_lastParam");
        System.out.println(organizationLastParam);
    }
    public void LoginTest(Ticket ticket, HttpServletRequest request, HttpServletResponse response, String time){
        HttpSession session  = request.getSession(true);
        StringBuffer userId = new StringBuffer();

        userId.append(ticket.getLn());//用户账号
        userId.append("&"); //特殊的格式要求？
        userId.append(ticket.getAbm());//用户账号对应的基本认证方式

        //获取配置信息
        String objectDetailsLastParam = ConfigReader.getConfigValue("h4a-config.xml", "object_param", "parameter_obj", "getBeanObjectsDetail_lastParam");
        String organizationLastParam = ConfigReader.getConfigValue("h4a-config.xml", "object_param", "parameter_obj", "organizationCategory_lastParam");
        if(null == objectDetailsLastParam){
            objectDetailsLastParam = "";
        }
        if(null == organizationLastParam){
            organizationLastParam = "";
        }


        //TODO
        IOguBeanReader oguBeanReaderHelper = BeanReaderHelper.getIOguBeanReader();
        try {
            ObjectsDetail[] objectDetails = oguBeanReaderHelper.
                    getBeanObjectsDetail(
                            H4aDefaultConstants.DEFAULT_BASE_VIEW,
                            ViewCategory.ViewCode, userId.toString(),
                            ObjectCategory.USER_IDENTITY, "",
                            OrganizationCategory.NONE, objectDetailsLastParam);
            if(null != objectDetails && objectDetails.length>0){
                ObjectsDetail objectDeatil = objectDetails[0];//这里为了编写示例默认取第一个人员，默认第一个是主职，其他都是兼职，
                //如果业务系统需要使用到兼职用户需要自行编写业务逻辑实现兼职用户的情况
                session.setAttribute("user", objectDeatil);
                session.setAttribute("userIP", request.getRemoteAddr());
                ObjectParentOrganizations[] objectOrganizations = oguBeanReaderHelper.
                        getBeanObjectParentOrganizations(
                                H4aDefaultConstants.DEFAULT_BASE_VIEW,
                                ViewCategory.ViewCode, objectDeatil.getAll_path_name(),
                                ObjectCategory.USER_ALL_PATH_NAME, "",
                                OrganizationCategory.NONE, false, false,
                                "", organizationLastParam);
                if(null != objectOrganizations && objectOrganizations.length>0){
                    session.setAttribute("organization", objectOrganizations);
                }

                IAccreditBeanReader acdit = BeanReaderHelper.getIAccreditBeanReader();
                FunctionsOfUser[] functionOfUsers = acdit.
                        getBeanFunctionsOfUser(
                                objectDeatil.getAll_path_name(),
                                UserCategory.USER_ALL_PATH_NAME, "",
                                cn.gov.customs.casp.sdk.h4a.accredit.ws.OrganizationCategory.NONE,
                                H4aDefaultConstants.DEFAULT_APPID,
                                AccreditCategory.Code, H4aDefaultConstants.DEFAULT_BASE_VIEW,
                                AccreditCategory.Code, FunctionCategories.All, DelegationCategories.All, "");

                session.setAttribute("functionOfUser", functionOfUsers);

            }
        } catch (IOguReaderGetObjectsDetailCupaaFaultArgsFaultFaultMessage | IOguReaderGetObjectParentOrganizationsCupaaFaultArgsFaultFaultMessage | IAccreditReaderGetFunctionsOfUserCupaaFaultArgsFaultFaultMessage e) {
            e.printStackTrace();
        }
    }

}

