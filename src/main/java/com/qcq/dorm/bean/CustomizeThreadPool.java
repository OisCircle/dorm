package com.qcq.dorm.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import java.util.concurrent.*;

/**
 * <p>
 * 自定义线程池
 * </p>
 *
 * @author O
 * @version 1.0
 * @since 2018/11/20
 */
@Configuration
public class CustomizeThreadPool {
	@Bean
	public ExecutorService executorService() {
		ThreadFactory threadFactory = new CustomizableThreadFactory();
		((CustomizableThreadFactory) threadFactory).setThreadGroupName("projectMainPool");

		return new ThreadPoolExecutor(5,
				100,
				180,
				TimeUnit.SECONDS,
				new LinkedBlockingDeque<>(),
				threadFactory
		);
	}
}