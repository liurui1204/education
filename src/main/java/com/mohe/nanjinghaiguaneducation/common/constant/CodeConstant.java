package com.mohe.nanjinghaiguaneducation.common.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回码常量类
 * 
 * @author jay
 * @date
 *
 */

public class CodeConstant {

	/** 存放code及message */
	static Map<String, String> codeMap = new HashMap<String, String>();
	static {
		codeMap.put("T1", "token失效");
		codeMap.put("T2", "token为空");
		codeMap.put("C0", "操作成功");
		codeMap.put("C1", "系统异常，请联系管理员");
		codeMap.put("C2", "操作失败");
		codeMap.put("C3", "非法参数");
		codeMap.put("C4", "路径不存在");
		codeMap.put("C5", "登录超时/登录异常/非法用户");
		codeMap.put("C6", "您的账号已被锁定，请联系客服");
	}

	/** token失效 **/
	public static final String TOKEN_INVALID = "T1";

	/** token为空 **/
	public static final String TOKEN_NULL = "T2";

	/** 操作成功* */
	public static final String SUCCESS = "C0";

	/** 系统异常，请联系管理员 **/
	public static final String SYS_EXCEPTION = "C1";

	/** 操作失败 **/
	public static final String FAIL = "C2";

	/** 参数有误 **/
	public static final String PARAME_ERROR = "C3";

	/** 路径不存在 **/
	public static final String NO_PATH = "C4";

	/** 登录超时/登录异常/非法用户 **/
	public static final String LOGIN_ERR = "C5";

	/** 您的账号已被锁定，请联系客服 **/
	public static final String ACCOUNT_FREEZE = "C6";

	/**
	 * 
	 * 根据返回码获得Message
	 * 
	 * @param code 返回码
	 * @return
	 */
	public static String getMessage(String code) {
		return codeMap.get(code);
	}
}
