package org.chun.listener;

import org.chun.cache.CacheInitializer;
import org.chun.exception.GdsCacheInitException;
import org.chun.utils.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class CacheInitializerApplicationReadyEventListener implements ApplicationListener<ApplicationReadyEvent> {

	@Autowired
	private ApplicationContext applicationContext;

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {

		for (CacheInitializer cache : applicationContext.getBeansOfType(CacheInitializer.class).values()) {
			String cacheName = cache.getClass().getSimpleName();

			try {
				cache.init();
				LogUtil.CACHE.info("{} init success.", cacheName);
			} catch (Exception e) {
				throw new GdsCacheInitException(cacheName, e);
			}
		}
	}

}
