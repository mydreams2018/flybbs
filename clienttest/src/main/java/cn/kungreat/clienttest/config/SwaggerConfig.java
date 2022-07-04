package cn.kungreat.clienttest.config;

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
 * Swagger2 配置类
 * 原生: https://host:port/context-path/swagger-ui.html
 * 美化: https://host:port/context-path/doc.html
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * Swagger 实例 Bean 是 Docket, 所以通过配置 Docket 实例来配置 Swagger
     * */
    @Bean
    public Docket docket() {

        return new Docket(DocumentationType.SWAGGER_2)
                // 展示在 Swagger 页面上的自定义工程描述信息
                .apiInfo(apiInfo())
                // 选择展示哪些接口
                .select()
                // 只有 cn.kungreat.clienttest.controller 包内的才展示
                .apis(RequestHandlerSelectors.basePackage("cn.kungreat.clienttest.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * Swagger2  的描述信息
     * Contact 注解是对 swagger-ui 界面上的一些和接口有关的联系人的信息进行描述，包括但不限于开发人员名称、地址等。
     * */
    public ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("client-test")
                .description("客户端测试功能")
                .contact(new Contact(
                        "kungreat", "https://www.kungreat.cn", "mydreams2018@outlook.com"))
                .version("1.0")
                .build();
    }
}
