package com.mohe.nanjinghaiguaneducation.filter;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mohe.nanjinghaiguaneducation.common.constant.CodeConstant;
import com.mohe.nanjinghaiguaneducation.common.constant.RedisKey;
import com.mohe.nanjinghaiguaneducation.common.exception.RRException;
import com.mohe.nanjinghaiguaneducation.common.result.ResultData;
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
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class LoginFilter implements Filter, Ordered {

    //private static final Logger logger = LoggerFactory.getLogger(AuthorizationInterceptor.class);

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
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        try{
            //??????????????????
            String uri = httpRequest.getRequestURI();
            //???????????????????????????????????? ???TODO ????????????????????????????????????
            if (uri.contains("/authLogin") || uri.contains("/login") ||uri.contains("/download") || uri.contains("swagger-ui")
                    || uri.contains("csrf") || uri.equals("/nanjingHaiguanEducation/") || uri.equals("/nanjingHaiguanEducation/error")
                    || uri.contains("/swagger-resources") || uri.contains("/swagger-resources/configuration/ui")
                    || uri.contains("v2/api-docs") || uri.contains("/swagger-resources/configuration/security") || uri.contains("/front")
                    || uri.contains("site/qualityCourses/findByName")) {
                chain.doFilter(httpRequest, httpResponse);
            }else{
                String token = httpRequest.getHeader(jwtUtils.getHeader());
                String userInfo = redisTemplate.opsForValue().get(RedisKey.REDIS_TOKEN + token);
                EduEmployeeEntity eduEmployeeEntity1 = JSON.parseObject(userInfo, EduEmployeeEntity.class);
                if(StringUtils.isBlank(token)){
                    token = httpRequest.getParameter(jwtUtils.getHeader());
                }

                //????????????
                if(StringUtils.isBlank(token)){
                    throw new RRException(CodeConstant.getMessage(CodeConstant.TOKEN_NULL));
                }

                //????????????
                Claims claims = jwtUtils.getClaimByToken(token);
                if(claims == null || jwtUtils.isTokenExpired(claims.getExpiration())){
                    throw new RRException(CodeConstant.getMessage(CodeConstant.TOKEN_INVALID));
                }else {
                    //???????????????????????????????????????
                    EduEmployeeEntity eduEmployeeEntity = eduEmployeeService.selectById(claims.getSubject());
                    if (null == eduEmployeeEntity) {
                        throw new RRException(CodeConstant.getMessage(CodeConstant.LOGIN_ERR));
                    }
                    if(eduEmployeeEntity.getIsEnable() != 1) {
                        throw new RRException(CodeConstant.getMessage(CodeConstant.ACCOUNT_FREEZE));
                    }
                }
                assert eduEmployeeEntity1 != null;
                String h4aUserGuid = eduEmployeeEntity1.getH4aUserGuid();  // ?????????guid
                EduDepartmentEmployeeEntity departmentEmployeeEntity = eduDepartmentEmployeeService.selectOne(new EntityWrapper<EduDepartmentEmployeeEntity>().eq("h4aGuid", h4aUserGuid));
                //??????userId???request??????????????????userId?????????????????????
                EduDepartmentEntity departmentEntity = eduDepartmentService.selectOne(new EntityWrapper<EduDepartmentEntity>().eq("departmentCode", departmentEmployeeEntity.getDepartmentCode()));
                System.out.println("================");
                System.out.println(JSON.toJSONString(departmentEntity));
                httpRequest.setAttribute("departmentEntity",departmentEntity);
                httpRequest.setAttribute("userInfo", eduEmployeeEntity1);
                httpRequest.setAttribute("userId",claims.getSubject());
                chain.doFilter(httpRequest, httpResponse);
            }
        }catch (RRException e){
            ResultData<String> resultData = new ResultData<>();
            resultData.setCode(String.valueOf(e.getCode()));
            resultData.setData(e.getMsg());
            resultData.setSuccess(false);
            resultData.setMessage(e.getMsg());
            byte[] content = JSON.toJSONBytes(resultData);
            String str = new String(content, StandardCharsets.UTF_8);
            //??????????????????????????????
            ServletOutputStream out = response.getOutputStream();
            byte[] re = str.getBytes(StandardCharsets.UTF_8);
            response.setContentLength(re.length);//????????????????????????
            out.write(re);
        }



    }

    private static class MyResponseWrapper extends HttpServletResponseWrapper {
        private final ByteArrayOutputStream buffer;

        private final ServletOutputStream out;

        public MyResponseWrapper(HttpServletResponse httpServletResponse) {
            super(httpServletResponse);
            buffer = new ByteArrayOutputStream();
            out = new WrapperOutputStream(buffer);
        }

        @Override
        public ServletOutputStream getOutputStream()
                throws IOException {
            return out;
        }

        @Override
        public void flushBuffer()
                throws IOException {
            if (out != null) {
                out.flush();
            }
        }

        public byte[] getContent()
                throws IOException {
            flushBuffer();
            return buffer.toByteArray();
        }

        static class WrapperOutputStream extends ServletOutputStream {
            private final ByteArrayOutputStream bos;

            public WrapperOutputStream(ByteArrayOutputStream bos) {
                this.bos = bos;
            }

            @Override
            public void write(int b)
                    throws IOException {
                bos.write(b);
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setWriteListener(WriteListener arg0) {
            }
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
