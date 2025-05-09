CREATE TABLE detection
(
    id                       BIGINT AUTO_INCREMENT PRIMARY KEY,
    image_path               VARCHAR(255),
    camera_code              VARCHAR(255),
    people_id                VARCHAR(255),
    recognition_status       VARCHAR(255),
    response_raw             TEXT,
    created_time             DATETIME,
    captured_time            VARCHAR(255),
    first_time_check_in      DATETIME,
    last_time_check_in       DATETIME,
    search_id                VARCHAR(255),
    score DOUBLE
);

-- private final RedisService redisService;
-- private String getFirstTimeCheckIn(String peopleId) {
--         String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
--         Object firstTimeCheckIn = redisService.get("first_" + peopleId);
--         if (firstTimeCheckIn == null) {
--             firstTimeCheckIn = now;
--             redisService.save("first_" + peopleId, firstTimeCheckIn);
--         }
--         return firstTimeCheckIn.toString();
--     }