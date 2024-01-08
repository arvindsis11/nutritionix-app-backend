package com.nutritionix.whishlist.config;

import java.time.Duration;
import java.util.List;

import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.spi.CachingProvider;

import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheEventListenerConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.event.EventType;
import org.ehcache.jsr107.Eh107Configuration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class EHConfiguration {

	@Bean
	public CacheManager getCacheManager() {
		CachingProvider provider = Caching.getCachingProvider();
		CacheManager cacheManager = provider.getCacheManager();
		System.err.println("-------------------------");
		CacheEventListenerConfigurationBuilder asynchronousListener = CacheEventListenerConfigurationBuilder
				.newEventListenerConfiguration(new CustomCacheEventLogger(), EventType.CREATED, EventType.EXPIRED)
				.unordered().asynchronous();
		CacheConfiguration<String, List> configuration = CacheConfigurationBuilder
				.newCacheConfigurationBuilder(String.class, List.class,
						ResourcePoolsBuilder.heap(100).offheap(10, MemoryUnit.MB))
				.withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(50))).add(asynchronousListener)
				.build();

		javax.cache.configuration.Configuration<String, List> cacheConfiguration = Eh107Configuration
				.fromEhcacheCacheConfiguration(configuration);

		cacheManager.createCache("whishlistCache", cacheConfiguration);
		return cacheManager;
	}
}