package com.mohe.nanjinghaiguaneducation.common.customsSchedule.Helper;


import com.mohe.nanjinghaiguaneducation.common.customsSchedule.Decoder.BASE64Decoder;
import com.mohe.nanjinghaiguaneducation.common.customsSchedule.Decoder.BASE64Encoder;

public class NJEncryptHelper {
    public NJEncryptHelper() {
    }

    public static String EncryptToBase64(String strSource) {
        return strSource == null ? null : (new BASE64Encoder()).encode(strSource.getBytes());
    }

    public static String DeEncryptFromBase64(String strSource) {
        if (strSource == null) {
            return null;
        } else {
            BASE64Decoder decoder = new BASE64Decoder();

            try {
                byte[] b = decoder.decodeBuffer(strSource);
                return new String(b);
            } catch (Exception var3) {
                return null;
            }
        }
    }
}