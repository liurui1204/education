package com.mohe.nanjinghaiguaneducation.common.h4a;

import java.util.Map;

public class config {
    static String APP_VALUE = "ZHJYPT";

    //角色
    static String ROLE_VALUE_XITONGGUANLIYUAN = "SYSTEM_ADMIN";
    static String ROLE_VALUE_YINGYONGGUANLIYUAN = "APP_ADMIN";
    static String ROLE_VALUE_LIANLUOYUAN = "LLY";
    static String ROLE_VALUE_XUEYUAN = "XY";
    static String ROLE_VALUE_JIAOYUCHUZONGHEKE = "JYCZHK";
    static String ROLE_VALUE_JIAOYUCHUJIANKONGKE = "JYCJKK";

    //用户要显示的额外字段
    static String ROLE_EXTRA_ATTR_ALL = "OBJECTCLASS,PERSON,SIDELINE,ALL_PATH_NAME,GLOBAL_SORT,ORIGINAL_SORT,"
            +"DISPLAY_NAME,OBJ_NAME,PARENT_GUID,GUID,CODE_NAME,SORT_ID,NAME,CLASSIFY,ID,IsDelegated,ROLE_VALUE";

    //视角
    static String VIEW_VALUE_BANGONG = "CCIC_VIEW";
    static String VIEW_VALUE_BASIC = "BASE_VIEW";

    //角色
    static String ROLE_LIST_EMPTY = "";
//    static String ROLE_LIST_EMPTY = "JYCJYJKK";
    static String ROLE_LIST_ALL = "JYCJYJKK,LSGLD,XY,SXJDGLY,JYCLD,LSGLLY,JGCSLLY,HSZYGLY,JGCSLD,JYCZHK,GLY,JYCPXK";

    //获取用户要用到的附加字段
    static String USERS_EXT_PARAMS = "PERSON_ID,RANK_CODE,ATTRIBUTES,RANK_NAME,SIDELINE,E_MAIL"
            +",SYSCONTENT1,SYSCONTENT2,SYSCONTENT3"
//            +",SYSDISTINCT1,SYSDISTINCT2"
//            +",OUSYSCONTENT1,OUSYSCONTENT2,OUSYSCONTENT3"
//            +",OUSYSDISTINCT1,OUSYSDISTINCT2"
            +"";

}
