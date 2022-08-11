package com.mohe.nanjinghaiguaneducation.common.interceptor;


import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mohe.nanjinghaiguaneducation.common.constant.CodeConstant;
import com.mohe.nanjinghaiguaneducation.common.constant.RedisKey;
import com.mohe.nanjinghaiguaneducation.common.exception.RRException;
import com.mohe.nanjinghaiguaneducation.common.utils.JwtUtils;
import com.mohe.nanjinghaiguaneducation.modules.common.entity.EduDepartmentEmployeeEntity;
import com.mohe.nanjinghaiguaneducation.modules.common.entity.EduDepartmentEntity;
import com.mohe.nanjinghaiguaneducation.modules.common.service.EduDepartmentEmployeeService;
import com.mohe.nanjinghaiguaneducation.modules.common.service.EduDepartmentService;
import com.mohe.nanjinghaiguaneducation.modules.employee.entity.EduEmployeeEntity;
import com.mohe.nanjinghaiguaneducation.modules.employee.service.EduEmployeeService;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 权限(Token)验证
 * @author jay
 * @email 755822107@qq.com
 * @date 2022-02-16 15:38
 */
@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

	private static final Logger logger = LoggerFactory.getLogger(AuthorizationInterceptor.class);

    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private EduEmployeeService eduEmployeeService;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private EduDepartmentEmployeeService eduDepartmentEmployeeService;
    @Autowired
    private EduDepartmentService eduDepartmentService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        获取用户凭证
        String uri = request.getRequestURI();
        //登录，下载的时候跳过验证 （TODO 下载要跳过验证？待确认）
        if (uri.contains("/authLogin") || uri.contains("/login") ||uri.contains("/download")) {
            return true;
        }
        //swagger的页面，跳过验证
        if(uri.contains("swagger-ui") || uri.contains("csrf") || uri.equals("/nanjingHaiguanEducation/") || uri.equals("/nanjingHaiguanEducation/error")){
            return true;
        }
        //前端页面，不用登录
        if(uri.contains("/front") ){
            return true;
        }
        if (uri.contains("site/qualityCourses/findByName")){
            return true;
        }
        String token = request.getHeader(jwtUtils.getHeader());
        String userInfo = redisTemplate.opsForValue().get(RedisKey.REDIS_TOKEN + token);
        EduEmployeeEntity eduEmployeeEntity1 = JSON.parseObject(userInfo, EduEmployeeEntity.class);
        if(StringUtils.isBlank(token)){
            token = request.getParameter(jwtUtils.getHeader());
        }

        //凭证为空
        if(StringUtils.isBlank(token)){
        	logger.info("Header参数信息警告：token为空");
            throw new RRException(CodeConstant.getMessage(CodeConstant.TOKEN_NULL));
        }

        //凭证错误
        Claims claims = jwtUtils.getClaimByToken(token);
        if(claims == null || jwtUtils.isTokenExpired(claims.getExpiration())){
        	logger.info("Header参数信息警告：token已过期["+token+"]");
            throw new RRException(CodeConstant.getMessage(CodeConstant.TOKEN_INVALID));
        }else {
        	//凭证正确，判断用户是否合法
            EduEmployeeEntity eduEmployeeEntity = eduEmployeeService.selectById(claims.getSubject());
            if (null == eduEmployeeEntity) {
				throw new RRException(CodeConstant.getMessage(CodeConstant.LOGIN_ERR));
			}
			if(eduEmployeeEntity.getIsEnable() != 1) {
				throw new RRException(CodeConstant.getMessage(CodeConstant.ACCOUNT_FREEZE));
			}
        }
        String h4aUserGuid = eduEmployeeEntity1.getH4aUserGuid();  // 用户的guid
        EduDepartmentEmployeeEntity departmentEmployeeEntity = eduDepartmentEmployeeService.selectOne(new EntityWrapper<EduDepartmentEmployeeEntity>().eq("h4aGuid", h4aUserGuid));
        //设置userId到request里，后续根据userId，获取用户信息
        EduDepartmentEntity departmentEntity = eduDepartmentService.selectOne(new EntityWrapper<EduDepartmentEntity>().eq("departmentCode", departmentEmployeeEntity.getDepartmentCode()));
        System.out.println("================");
        System.out.println(JSON.toJSONString(departmentEntity));
        request.setAttribute("departmentEntity",departmentEntity);
        request.setAttribute("userInfo", eduEmployeeEntity1);
        request.setAttribute("userId",claims.getSubject());
        return true;

    }
}
