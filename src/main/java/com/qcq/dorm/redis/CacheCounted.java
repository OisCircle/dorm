package com.qcq.dorm.redis;

/**
 * <p>
 * 记录缓存统计
 * </p>
 *
 * @author O
 * @version 1.0
 * @since 2018/11/27
 */
public interface CacheCounted {
	/**
	 * 命中率
	 *
	 * @return 百分比字符串
	 */
	String hitRate();

	/**
	 * 未命中率
	 *
	 * @return 百分比字符串
	 */
	String missedRate();

	/**
	 * 命中次数
	 *
	 * @return long
	 */
	Long hitCount();

	/**
	 * 未命中次数
	 *
	 * @return long
	 */
	Long missedCount();
}
