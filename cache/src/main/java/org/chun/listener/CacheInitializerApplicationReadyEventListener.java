package org.chun.listener;

import org.chun.cache.CacheApplicationInitializer;
import org.chun.exception.GdsApplicationReadyCacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class CacheInitializerApplicationReadyEventListener implements ApplicationListener<ApplicationReadyEvent> {

	private static final Logger log = LoggerFactory.getLogger(CacheInitializerApplicationReadyEventListener.class);

	@Autowired
	private ApplicationContext applicationContext;

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {

		for (CacheApplicationInitializer cache : applicationContext.getBeansOfType(CacheApplicationInitializer.class).values()) {
			String cacheName = cache.getClass().getSimpleName();

			try {
				cache.init();
				log.info("{} init success.", cacheName);
			} catch (Exception e) {
				throw new GdsApplicationReadyCacheException(cacheName, e);
			}
		}
	}

}
