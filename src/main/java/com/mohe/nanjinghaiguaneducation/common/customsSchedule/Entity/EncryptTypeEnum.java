package com.mohe.nanjinghaiguaneducation.common.customsSchedule.Entity;

public enum EncryptTypeEnum implements BaseConstEnum {
    None(0),
    MD5(1),
    ASE(2);

    private int value;

    private EncryptTypeEnum(int value) {
        this.value = value;
    }

    public int getConstValue() {
        return this.value;
    }

    public boolean compareValue(int compare) {
        return this.getConstValue() == compare;
    }
}
