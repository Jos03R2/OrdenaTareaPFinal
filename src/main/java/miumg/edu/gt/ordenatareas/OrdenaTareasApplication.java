package miumg.edu.gt.ordenatareas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {
    "miumg.edu.gt.ordenatareas",
    "miumg.edu.gt.datostareas"
})
@EnableJpaRepositories(basePackages = "miumg.edu.gt.datostareas.repository")
@EntityScan(basePackages = "miumg.edu.gt.datostareas.entity")
public class OrdenaTareasApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrdenaTareasApplication.class, args);
    }
}
