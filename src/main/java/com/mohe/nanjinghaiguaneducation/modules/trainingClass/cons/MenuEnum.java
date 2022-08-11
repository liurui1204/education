package com.mohe.nanjinghaiguaneducation.modules.trainingClass.cons;

import com.mohe.nanjinghaiguaneducation.common.exception.RRException;

public enum MenuEnum {
    //"界面_后台维护_关区培训管理（线上)_评估"
    XSPG("JM_HTWH_GQPXGLXS_PG"),
    //"界面_后台维护_关区培训管理（线下_评估"
    XXPG("JM_HTWH_GQPXGLXX_PG"),
    //"界面_后台维护_关区培训管理（线上)_决算审批"
    XSJSSP("JM_HTWH_GQPXGLXS_JSSP"),
    //,"界面_后台维护_关区培训管理（线下)_决算审批"
    XXJSSP("JM_HTWH_GQPXGLXX_JSSP"),
    //,"界面_后台维护_关区培训管理（线上)_决算申请"
    XSJSSQ("JM_HTWH_GQPXGLXS_JSSQ"),
    //,"界面_后台维护_关区培训管理（线下)_决算申请"
    XXJSSQ("JM_HTWH_GQPXGLXX_JSSQ"),
    //,"界面_后台维护_关区培训管理（线上)_报名及请假"
    XSBMJQJ("JM_HTWH_GQPXGLXS_BMJQJ"),
    //,"界面_后台维护_关区培训管理（线下)_报名及请假"
    XXBMJQJ("JM_HTWH_GQPXGLXX_BMJQJ"),
    //,"界面_后台维护_关区培训管理（线上)_培训班审批"
    XSPXBSP("JM_HTWH_GQPXGLXS_PXBSP"),
    //,"界面_后台维护_关区培训管理（线下)_培训班审批"
    XXPXBSP("JM_HTWH_GQPXGLXX_PXBSP"),
    //,"界面_后台维护_关区培训管理（线上)_培训班查询"
    XSPXBCX("JM_HTWH_GQPXGLXS_PXBCX"),
    //,"界面_后台维护_关区培训管理（线下)_培训班查询"
    XXPXBCX("JM_HTWH_GQPXGLXX_PXBCX"),
    //,"界面_后台维护_关区培训管理（线上)_培训班申请"
    XSPXBSQ("JM_HTWH_GQPXGLXS_PXBSQ"),
    //,"界面_后台维护_关区培训管理（线下)_培训班申请"
    XXPXBSQ("JM_HTWH_GQPXGLXX_PXBSQ"),
    //界面_后台维护_关区培训管理（线上)_评估设置
    XSPGSZ("JM_HTWH_GQPXGLXS_PGSZ"),
    //界面_后台维护_关区培训管理（线下)_评估设置
    XXPGSZ("JM_HTWH_GQPXGLXX_PGSZ"),
    //,"教育处综合科"
    JYCZHK("JYCZHK"),
    //,"机关处室联络员"
    JGCSLLY("JGCSLLY"),
    //,"教育处教育监控科"
    JYCJYJKK("JYCJYJKK"),
    //,"教育处领导"
    JYCLD("JYCLD"),
    //,"学员"
    XY("XY"),
    //机关处室领导
    JGCSLD("JGCSLD"),
    LSGLLY("LSGLLY"),
    LSGLD("LSGLD")
    ;
    private String code;
    MenuEnum(String code){
        this.code=code;
    }
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static MenuEnum getByValue(String code) {
        for (MenuEnum value : values()) {
            if (value.getCode().equals(code)){
                return value;
            }
        }
        return null;
    }
}
