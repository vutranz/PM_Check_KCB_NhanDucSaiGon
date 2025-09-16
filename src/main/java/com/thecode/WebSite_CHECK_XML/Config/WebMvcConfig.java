package com.thecode.WebSite_CHECK_XML.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Bean
    public InternalResourceViewResolver jspViewResolver() {
    InternalResourceViewResolver resolver = new InternalResourceViewResolver();
    resolver.setPrefix("/WEB-INF/view/");
    resolver.setSuffix(".jsp");
    resolver.setViewClass(JstlView.class);
    return resolver;
}

    // @Override
    // public void configureViewResolvers(ViewResolverRegistry registry) {
    //     registry.viewResolver(viewResolver());
    // }

@Override
public void addResourceHandlers(ResourceHandlerRegistry registry) {
    // Cho phép truy cập toàn bộ thư mục resources
    registry.addResourceHandler("/resources/data_KCB_XML/**")
            .addResourceLocations("/resources/");

    // // Nếu muốn tách riêng
    // registry.addResourceHandler("/css/**").addResourceLocations("/resources/css/");
    // registry.addResourceHandler("/js/**").addResourceLocations("/resources/js/");
    // registry.addResourceHandler("/images/**").addResourceLocations("/resources/images/");
    // registry.addResourceHandler("/assets/**").addResourceLocations("/resources/assets/");
    // registry.addResourceHandler("/pages/**").addResourceLocations("/resources/pages/");
}



}