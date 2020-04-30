package com.qcq.dorm.schedule;

import com.qcq.dorm.redis.CacheCounted;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


/**
 * <p>
 * 统计日志
 * </p>
 *
 * @author O
 * @version 1.0
 * @since 2018/11/27
 */
@Slf4j
@Component
public class LogSchedule {
    @Resource
    private CacheCounted cacheCounted;

    /**
     * 日志定时输出缓存信息
     */
    @Scheduled(fixedDelay = 1000 * 60 * 30)
    public void printCacheReport() {
        log.info("********** cache counted **********");
        log.info("current cache hit    count: {}", cacheCounted.hitCount());
        log.info("current cache missed count: {}", cacheCounted.missedCount());
        log.info("current cache hit rate: {}", cacheCounted.hitRate());
        log.info("current cache miss rate: {}", cacheCounted.missedRate());
        log.info("***********************************");
    }
}
