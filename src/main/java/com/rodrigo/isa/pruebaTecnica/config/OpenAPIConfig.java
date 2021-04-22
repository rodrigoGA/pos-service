package com.rodrigo.isa.pruebaTecnica.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "POS Service",
                version = "1.0",
                description = "POS (Point Of Sale) para prueba tecnica para ISA"
        ),
        servers = @Server(url = "http://localhost:8080")
)
public class OpenAPIConfig {
}