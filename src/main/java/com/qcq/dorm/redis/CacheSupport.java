package com.qcq.dorm.redis;

import org.aspectj.lang.ProceedingJoinPoint;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *
 * </p>
 *
 * @author O
 * @version 1.0
 * @since 2018/11/27
 */
public interface CacheSupport<T> {
	/**
	 * 从redis获取数据，如果存在则直接返回，如果不存在则从mysql获取数据，返回数据并添加缓存
	 *
	 * @param pjp 切面
	 * @param key key
	 * @return T
	 * @throws Throwable t
	 */
	T cacheable(ProceedingJoinPoint pjp, String key) throws Throwable;

	/**
	 * 从redis获取数据，如果存在则直接返回，如果不存在则从mysql获取数据，返回数据并添加缓存
	 *
	 * @param pjp      切面
	 * @param key      key
	 * @param timeout  time
	 * @param timeUnit unit
	 * @return T
	 * @throws Throwable t
	 */
	T cacheable(ProceedingJoinPoint pjp, String key, Long timeout, TimeUnit timeUnit) throws Throwable;

	/**
	 * 添加
	 *
	 * @param value v
	 * @param key   k
	 */
	void set(String key, T value);

	/**
	 * 添加
	 *
	 * @param value   v
	 * @param unit    u
	 * @param timeout t
	 * @param key     k
	 */
	void set(String key, T value, Long timeout, TimeUnit unit);

	/**
	 * delete
	 *
	 * @param keys keys
	 * @return count deleted
	 */
	Long delete(Set<String> keys);

	/**
	 * delete
	 *
	 * @param key key
	 * @return boolean
	 */
	Boolean delete(String key);

	/**
	 * get
	 *
	 * @param key key
	 * @return T
	 */
	T get(String key);
}
