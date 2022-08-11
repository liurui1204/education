package com.mohe.nanjinghaiguaneducation.modules.trainingClass.cons;

public enum FeeItemEnum {
    //科级
    KEJI(200,1),
    //处级
    CHUJI(400,2),
    //厅级
    TINGJI(800,3),
    //中级
    ZHONGJI(300,4),
    //副高
    FUGAO(500,5),
    //正高
    ZHENGGAO(1000,6),
    //知名学者
    ZHIMING(1500,7)
    ;

    //费用
    private Integer fee;
    //级别
    private Integer level;

    FeeItemEnum(Integer fee,Integer level){
        this.fee=fee;
        this.level=level;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getFee() {
        return fee;
    }

    public void setFee(Integer fee) {
        this.fee = fee;
    }

    public static Integer getByValue(Integer level) {
        for (FeeItemEnum value : values()) {
            if (value.getLevel().equals(level)){
                return value.getFee();
            }
        }
        return null;
    }
}
