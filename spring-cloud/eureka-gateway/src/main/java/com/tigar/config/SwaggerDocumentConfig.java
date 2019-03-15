//package com.tigar.config;
//
//import org.springframework.context.annotation.Primary;
//import org.springframework.stereotype.Component;
//import springfox.documentation.swagger.web.SwaggerResource;
//import springfox.documentation.swagger.web.SwaggerResourcesProvider;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * zuul整合swagger：https://blog.csdn.net/qq_31489805/article/details/80444284
// * @author tigar
// * @date 2019/3/12.
// */
//@Component
//@Primary
//public class SwaggerDocumentConfig implements SwaggerResourcesProvider {
//
//    @Override
//    public List<SwaggerResource> get() {
//        List resources = new ArrayList<>();
//        resources.add(swaggerResource("学校服务接口", "/school/v2/api-docs", "1.0"));
//        resources.add(swaggerResource("用户微服务接口", "/user/v2/api-docs", "1.0"));
//        return resources;
//    }
//
//    private SwaggerResource swaggerResource(String name, String location, String version) {
//        SwaggerResource swaggerResource = new SwaggerResource();
//        swaggerResource.setName(name);
//        swaggerResource.setLocation(location);
//        swaggerResource.setSwaggerVersion(version);
//        return swaggerResource;
//    }
//
//}