package com.tigar.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author tigar
 * @date 2019/1/25.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.tigar"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        //TODO: 修改下面描叙
        return new ApiInfoBuilder()
                .title("系统")
                .description("系统")
                .termsOfServiceUrl("http://www.test.cn/")
                .contact(new Contact("test", "", "erp@test.com"))
                .version("1.0.0")
                .build();
    }
}
