package nhanph.timekeeping.processor.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class RedisCacheCleaner {
    private final RedisTemplate<String, Object> redisTemplate;

    @Scheduled(cron = "0 59 23 * * *")
    public void clearCache() {
        log.info("Clearing Redis cache at end of day...");
        var keysFirst = redisTemplate.keys("first_*");

        if (!keysFirst.isEmpty()) {
            redisTemplate.delete(keysFirst);
            log.info("Deleted {} keys", keysFirst.size());
        } else {
            log.info("No matching keys to delete");
        }
    }
}
