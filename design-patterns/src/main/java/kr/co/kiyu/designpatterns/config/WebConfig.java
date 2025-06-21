package kr.co.kiyu.designpatterns.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 웹 설정 - CORS 허용
 * 
 * 브라우저에서 API 호출 시 CORS 에러를 방지하기 위한 설정
 * 로컬 HTML 파일에서 localhost:8078 API 호출을 허용한다
 * 
 * @author KIYU-IT
 * @date 2025. 6. 20.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
} 