package nhanph.timekeeping.processor.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig {
    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);        // số luồng core
        executor.setMaxPoolSize(8);         // tối đa
        executor.setQueueCapacity(100);     // hàng đợi
        executor.setThreadNamePrefix("AsyncUploader-");
        executor.initialize();
        return executor;
    }
}