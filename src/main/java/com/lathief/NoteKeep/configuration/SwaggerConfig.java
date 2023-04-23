package com.lathief.NoteKeep.configuration;

import java.util.ArrayList;
import java.util.List;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Value("${server.port}")
    private int serverPort;

    @Bean
    public OpenAPI NoteApi(@Value("Swagger UI for NoteKeep App") String appDescription,
                                  @Value("v1.0.0") String appVersion
    ) {
        Server server = new Server();
        server.setUrl("http://localhost:8080/");
        List<Server> listOfServer = new ArrayList<>();
        listOfServer.add(server);

        return new OpenAPI()
                .info(new Info()
                        .title("NoteKeep App")
                        .version(appVersion)
                        .description(appDescription)
                        .termsOfService("https://github.com/lathief/notes-app")
                ).servers(listOfServer);
    }
}
