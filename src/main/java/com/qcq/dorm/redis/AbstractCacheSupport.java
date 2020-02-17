package com.qcq.dorm.redis;

import org.aspectj.lang.ProceedingJoinPoint;

import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 缓存抽象
 * </p>
 *
 * @author O
 * @version 1.0
 * @since 2018/11/26
 */
public abstract class AbstractCacheSupport<T> implements CacheSupport<T> {
	/**
	 * 抽象出来的公共行为
	 *
	 * @param pjp      pjp
	 * @param key      key
	 * @param timeout  time
	 * @param timeUnit unit
	 * @return T
	 * @throws Throwable t
	 */
	protected abstract T cacheable0(ProceedingJoinPoint pjp, String key, Long timeout, TimeUnit timeUnit) throws Throwable;

	@Override
	public T cacheable(ProceedingJoinPoint pjp, String key) throws Throwable {
		return cacheable0(pjp, key, null, null);
	}

	@Override
	public T cacheable(ProceedingJoinPoint pjp, String key, Long timeout, TimeUnit timeUnit) throws Throwable {
		return cacheable0(pjp, key, timeout, timeUnit);
	}
}
