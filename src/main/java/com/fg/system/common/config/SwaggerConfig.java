/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.fg.system.common.config;

import com.fasterxml.classmate.TypeResolver;
import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.plugin.core.PluginRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.schema.TypeNameExtractor;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.DescriptionResolver;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@Configuration
@EnableOpenApi//Swagger3
//@EnableSwagger2
@EnableKnife4j //UI
public class SwaggerConfig implements WebMvcConfigurer {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.OAS_30)
            .useDefaultResponseMessages(false) //去掉默认的状态响应码
            .apiInfo(apiInfo())
             //分组名称
            .groupName("后台管理接口")
            .select()
            //加了ApiOperation注解的类，才生成接口文档
            .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
            //包下的类，才生成接口文档
            //.apis(RequestHandlerSelectors.basePackage("com.fg.system.modules"))
            .paths(PathSelectors.any())
            .build()
            .securitySchemes(security());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("基础权限系统")
            .description("通用的springboot脚手架")
                .contact(new Contact("javafg","https://blog.csdn.net/qq_40136782","xxxx@qq.com"))

                .termsOfServiceUrl("https://blog.csdn.net/qq_40136782")
            .version("1.0.0")
            .build();
    }

    private List<SecurityScheme> security() {
        return newArrayList(
            new ApiKey("token", "token", "header")
        );
    }


   /* private ApiInfo apiInfo() {
        String serviceUrl = "http://localhost:" + serverProperties.getPort() +
                serverProperties.getServlet().getContextPath();
        return new ApiInfoBuilder()
                .title("xxxx服务后台接口文档")
                .description("xxxx服务")
                .termsOfServiceUrl(serviceUrl)
                .version(sysInfoProperties.getVersion())
                .contact(new Contact(sysInfoProperties.getPic(), null, "wq6e54@123.com"))
                .build();
    }

    @Bean(value = "defaultApi2")
    public Docket defaultApi2() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                //分组名称
                .groupName("1.X版本接口")
                .select()
                //这里指定Controller扫描包路径
                .apis(RequestHandlerSelectors.basePackage("com.xxxxx.contentstatistics.web"))
                .paths(PathSelectors.any())
                .build();
        return docket;
    }*/


}