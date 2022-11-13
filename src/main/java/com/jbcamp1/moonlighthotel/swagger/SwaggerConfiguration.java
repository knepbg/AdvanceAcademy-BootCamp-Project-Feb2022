package com.jbcamp1.moonlighthotel.swagger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import io.swagger.v3.core.jackson.ModelResolver;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@SecurityScheme(name = "bearerAuth", scheme = "bearer", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
public class SwaggerConfiguration {

    public static final String ROOM_TAG = "Rooms";
    public static final String TRANSFER_TAG = "Transfers";
    public static final String USER_TAG = "Users";

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Moonlight Hotel API")
                        .description("API Documentation")
                        .version("1.0.0"))
                .tags(List.of(
                        new Tag().name(ROOM_TAG).description("Actions with Rooms"),
                        new Tag().name(TRANSFER_TAG).description("Actions with Transfers"),
                        new Tag().name(USER_TAG).description("Actions with Users")));
    }

    @Bean
    public ModelResolver modelResolver(ObjectMapper objectMapper) {
        return new ModelResolver(objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE));
    }
}

