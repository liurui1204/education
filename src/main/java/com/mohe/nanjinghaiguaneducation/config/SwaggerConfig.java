package com.mohe.nanjinghaiguaneducation.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("测试")
                .apiInfo(apiInfo())//调用的api描述方法
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.mohe.nanjinghaiguaneducation.modules"))//扫描的API包路径
                .build();

    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("测试API文档")
                .version("1.0")
                .build();
    }

}