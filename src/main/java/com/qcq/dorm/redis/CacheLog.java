package com.qcq.dorm.redis;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * <p>
 * 记录缓存操作日志
 * </p>
 *
 * @author O
 * @version 1.0
 * @since 2018/11/26
 */
@Aspect
@Component
@Slf4j
public class CacheLog implements CacheCounted {
	private static AtomicLong hit = new AtomicLong(0L);
	private static AtomicLong missed = new AtomicLong(0L);

	@Pointcut(value = "execution(* com.qcq.dorm.redis.RedisCacheSupport.set(..))")
	public void setCache() {
	}

	@Pointcut(value = "execution(* com.qcq.dorm.redis.RedisCacheSupport.get(..))")
	public void getCache() {
	}

	@Pointcut(value = "execution(* com.qcq.dorm.redis.RedisCacheSupport.delete(..))")
	public void deleteCache() {
	}

	@After(value = "setCache()", argNames = "jp")
	public void afterSetCache(JoinPoint jp) {
		String key = (String) jp.getArgs()[0];
		int thirdParam = 2;
		if (jp.getArgs().length > thirdParam) {
			Long time = (Long) jp.getArgs()[2];
			TimeUnit unit = (TimeUnit) jp.getArgs()[3];
			log.info("put cache: {} expire in {} {}", key, time, unit.toString());
		} else {
			log.info("put cache: {}", key);
		}
	}

	@AfterReturning(pointcut = "getCache()", returning = "result")
	public void afterGetCache(JoinPoint jp, Object result) {
		String key = (String) jp.getArgs()[0];
		if (result != null) {
			hit.getAndIncrement();
			log.info("cache hit: {}", key);
		} else {
			missed.getAndIncrement();
			log.info("cache miss: {}", key);
		}
	}

	@After(value = "deleteCache()", argNames = "jp")
	public void afterDelete(JoinPoint jp) {
		Object key = jp.getArgs()[0];
		log.info("delete cache: {}", key);
	}

	@Override
	public String hitRate() {
		return String.format("%.2f", doubleHitRate() * 100) + '%';
	}

	private static long getTotal() {
		return hit.get() + missed.get();
	}

	private static double doubleHitRate() {
		if (getTotal() == 0) {
			return 0;
		}
		return (double) hit.get() / (double) getTotal();
	}

	@Override
	public String missedRate() {
		return String.format("%.2f", (1 - doubleHitRate()) * 100) + '%';
	}

	@Override
	public Long hitCount() {
		return hit.get();
	}

	@Override
	public Long missedCount() {
		return missed.get();
	}
}
