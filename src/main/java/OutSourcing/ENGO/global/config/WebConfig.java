package OutSourcing.ENGO.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(
//                        "http://localhost:63342",
//                        "http://localhost:63343",
//                        "https://korea-environmental-association.s3.ap-northeast-2.amazonaws.com",
//                        "http://korea-environmental-association.s3.ap-northeast-2.amazonaws.com",
//                        "http://outsourcing-engo-front-final.s3.ap-northeast-2.amazonaws.com"
                        "*"
                )
                .allowedMethods("*")
                .allowedHeaders("*");
//                .allowCredentials(true);
    }
}