package org.grow.mall;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.oas.annotations.EnableOpenApi;


@EnableOpenApi
@SpringBootApplication
public class MarketbApplication {

    public static void main(String[] args) {
        SpringApplication.run(MarketbApplication.class, args);
    }
}
