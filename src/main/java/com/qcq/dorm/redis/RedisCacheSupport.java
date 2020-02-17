package com.qcq.dorm.redis;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.cache.support.NullValue;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 公共代码复用
 * 为什么要在这里封装多一层redis操作？原因：为了缓存和统一操作，只有spring管理的bean才能做切面逻辑
 * 直接调用内部方法无法执行切面，要注入本身
 * </p>
 *
 * @author O
 * @version 1.0
 * @since 2018/11/15
 */
@Component
@Slf4j
@SuppressWarnings("unchecked")
public class RedisCacheSupport<T> extends AbstractCacheSupport<T> {
	@Resource
	private RedisTemplate<String, T> redisTemplate;
	@Resource
	private ExecutorService executorService;
	@Resource
	private RedisCacheSupport<T> cacheSupport;


	@Override
	public T cacheable(ProceedingJoinPoint pjp, String key) throws Throwable {
		return cacheable0(pjp, key, null, null);
	}

	@Override
	public T cacheable(ProceedingJoinPoint pjp, String key, Long timeout, TimeUnit timeUnit) throws Throwable {
		return cacheable0(pjp, key, timeout, timeUnit);
	}

	@Override
	public T cacheable0(ProceedingJoinPoint pjp, String key, Long timeout, TimeUnit timeUnit) throws Throwable {
		T t = cacheSupport.get(key);
		if (NullValue.INSTANCE.equals(t)) {
			t = (T) pjp.proceed();
			if (t != null) {
				final T t2 = t;
				if (timeout == null && timeUnit == null) {
					executorService.execute(() -> cacheSupport.set(key, t2));
				} else {
					executorService.execute(() -> cacheSupport.set(key, t2, timeout, timeUnit));
				}
			}
			return t;
		}
		return t;
	}

	@Override
	public void set(String key, T value) {
		redisTemplate.opsForValue().set(key, value);
	}

	@Override
	public void set(String key, T value, Long timeout, TimeUnit unit) {
		redisTemplate.opsForValue().set(key, value, timeout, unit);
	}

	@Override
	public Long delete(Set<String> keys) {
		return redisTemplate.delete(keys);
	}

	@Override
	public Boolean delete(String key) {
		return redisTemplate.delete(key);
	}

	@Override
	public T get(String key) {
		return redisTemplate.opsForValue().get(key);
	}
}
