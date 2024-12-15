package vn.edu.iuh.fit.nguyenductai_21024541_week05;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
@EntityScan(basePackages = "vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.models")
@EnableJpaRepositories(basePackages = "vn.edu.iuh.fit.nguyenductai_21024541_week05.backend.repositories")
public class NguyenDucTai21024541Week05Application {

    public static void main(String[] args)   {
        SpringApplication.run(NguyenDucTai21024541Week05Application.class, args);
    }

}
