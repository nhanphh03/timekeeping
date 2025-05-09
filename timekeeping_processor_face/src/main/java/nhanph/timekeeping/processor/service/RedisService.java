package nhanph.timekeeping.processor.service;

public interface RedisService {

    void save(String key, Object value);

    Object get(String key) ;
}
