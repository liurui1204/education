package com.mohe.nanjinghaiguaneducation.common.customsSchedule.Entity;

public enum RarTypeEnum implements BaseConstEnum {
    None(0),
    Rar(1),
    Zip(2);

    private int value;

    private RarTypeEnum(int value) {
        this.value = value;
    }

    public int getConstValue() {
        return this.value;
    }

    public boolean compareValue(int compare) {
        return this.getConstValue() == compare;
    }
}
