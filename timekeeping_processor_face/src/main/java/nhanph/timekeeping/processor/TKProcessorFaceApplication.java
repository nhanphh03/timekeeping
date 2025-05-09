package nhanph.timekeeping.processor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class TKProcessorFaceApplication {
    public static void main(String[] args) {
        SpringApplication.run(TKProcessorFaceApplication.class, args);
    }
}
