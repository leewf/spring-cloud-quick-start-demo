package cloud.wf.module;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


@EnableEurekaClient
@SpringBootApplication
public class ModuleBApplication {

    public static void main(String[] args) {
        SpringApplication.run(ModuleBApplication.class, args);
    }

}
