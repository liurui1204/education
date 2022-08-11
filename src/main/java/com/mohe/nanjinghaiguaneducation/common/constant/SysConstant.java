package com.mohe.nanjinghaiguaneducation.common.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 系统常量配置
 */
public class SysConstant {
    /**
     * 密码盐值长度
     */
    public static final int SALT_LENGTH = 10;
    /**
     * 用户编号长度
     */
    public static final int USER_CODE_LENGTH = 6;
    /**
     * token过期天数
     */
    public static final int TOKEN_EXPIRE_DAYS = 7;

    /**
     * 操作成功
     */
    public static final String SUCCESS = "200";
    /**
     * 参数错误
     */
    public static final String PARAMETER_ERROR = "500";
    /**
     * token已过期
     */
    public static final String TOKEN_EXPIRED = "501";
    /**
     * 用户资源权限不足
     */
    public static final String INSUFFICIENT_PERMISSIONS = "502";
    /**
     * 用户名或密码错误
     */
    public static final String USERNAME_PASSWORD_ERROR = "503";
    /**
     * 账号已被锁定,请联系管理员
     */
    public static final String ACCOUNT_LOCKED = "504";
    /**
     * 用户已登出
     */
    public static final String TOKEN_STATUS_EXCEPTION = "505";
    /**
     * 用户未登录
     */
    public static final String NO_LOGIN = "506";
    /**
     * 操作失败
     */
    public static final String FAIL = "507";
    /**
     * 文件上传失败
     */
    public static final String UPLOAD_FAIL = "508";
    /**
     * 用户账号或手机号已存在
     */
    public static final String ACCOUNT_PHONE_ERROR = "509";
    /**
     * 请求体为空
     */
    public static final String REQUEST_BODY_NULL = "401";
    /**
     * 请求方式错误
     */
    public static final String REQUEST_WAY_ERROR = "402";
    /**
     * 服务限流或降级
     */
    public static final String SERVICE_LIMIT = "403";
    /**
     * 服务异常
     */
    public static final String SERVICE_EXCEPTION = "404";
    /**
     * gateway网关异常
     */
    public static final String GATEWAY_EXCEPTION = "405";

    /**
     * 存放code及message
     */
    static Map<String, String> codeMap = new HashMap<String, String>();

    static {
        codeMap.put("200", "操作成功");
        codeMap.put("500", "参数错误");
        codeMap.put("501", "token已过期");
        codeMap.put("502", "用户资源权限不足");
        codeMap.put("503", "用户账号或密码错误");
        codeMap.put("504", "账号已被锁定,请联系管理员");
        codeMap.put("505", "用户已登出或token被禁用");
        codeMap.put("506", "用户未登录");
        codeMap.put("507", "操作失败");
        codeMap.put("508", "文件上传失败");
        codeMap.put("509", "用户账号或手机号已存在");
        codeMap.put("401", "请求体为空");
        codeMap.put("402", "请求方式错误");
        codeMap.put("403", "服务限流或降级");
        codeMap.put("404", "服务异常");
        codeMap.put("405", "gateway网关异常");
    }

    /**
     * 根据返回码获得Message
     *
     * @param code 返回码
     * @return
     */
    public static String getMessage(String code) {
        return codeMap.get(code);
    }
}
