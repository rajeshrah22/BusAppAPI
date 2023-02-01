package playground;

import java.io.IOException;
import org.ehcache.*;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;

import java.util.Arrays;
public class cacheTest {
	public static void main(String[] args) {
		CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder() 
			    .withCache("preConfigured",
			        CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, String.class, ResourcePoolsBuilder.heap(10))) 
			    .build(); 
			cacheManager.init(); 

			Cache<Long, String> preConfigured =
			    cacheManager.getCache("preConfigured", Long.class, String.class); 

			Cache<Long, String> myCache = cacheManager.createCache("myCache", 
			    CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, String.class, ResourcePoolsBuilder.heap(10)));

			myCache.put(1L, "da one!"); 
			String value = myCache.get(1L);
			
			System.out.println("1L: " + value);

			value = myCache.get(1L);
			//cacheManager.removeCache("preConfigured"); 
			cacheManager.removeCache("myCache");
			cacheManager.close(); 
			
			
			
			
	}
}
