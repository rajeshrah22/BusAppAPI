package app.service;

import java.util.ArrayList;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.json.simple.JSONObject;

//local imports
import app.exceptions.CacheInitializationException;

@SuppressWarnings("rawtypes")
public class CacheManagerService{
	private CacheManager cacheManager;
	private static int instanceCount = 0;
	
	private CacheManagerService() {
		cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
			    .withCache("agencies",
			        CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, ArrayList.class, ResourcePoolsBuilder.heap(100)))
			    .withCache("routes",
				        CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, ArrayList.class, ResourcePoolsBuilder.heap(100)))
			    .withCache("routeConfigs",
				        CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, JSONObject.class, ResourcePoolsBuilder.heap(100)))
			    .build();
		
		cacheManager.init();
	}
	
	public static CacheManagerService createCacheManagerService() throws CacheInitializationException {		
		if (instanceCount >= 1 ) {
			throw new CacheInitializationException("CacheManager instance already exists, only one is allowed");
		}
		
		return new CacheManagerService();
	}
	
	public  Cache<String, ArrayList> getAgencyCache() {
		Cache<String, ArrayList> cache = cacheManager.getCache("agencies", String.class, ArrayList.class);
				
		if (cache == null) {
			cache = cacheManager.createCache("agencies", 
	    		    CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, ArrayList.class, ResourcePoolsBuilder.heap(100)));
		}
		
		return cache;
	}
	
	public  Cache<String, JSONObject> getGeocodedListCache() {
		Cache<String, JSONObject> cache = cacheManager.getCache("geocodedList", String.class, JSONObject.class);
		
		if (cache == null) {
			cache = cacheManager.createCache("geocodedList", 
	    		    CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, JSONObject.class, ResourcePoolsBuilder.heap(100)));
		}
		
		return cache;
	}
	
	public  Cache<String, ArrayList> getRoutecache() {
		Cache<String, ArrayList> cache = cacheManager.getCache("routes", String.class, ArrayList.class);
				
		if (cache == null) {
			cache = cacheManager.createCache("routes", 
	    		    CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, ArrayList.class, ResourcePoolsBuilder.heap(100)));
		}
		
		return cache;
	}
	
	public  Cache<String, JSONObject> getRouteConfigCache() {
		Cache<String, JSONObject> cache = cacheManager.getCache("routeConfigs", String.class, JSONObject.class);
				
		if (cache == null) {
			cache = cacheManager.createCache("routeConfigs", 
	    		    CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, JSONObject.class, ResourcePoolsBuilder.heap(100)));
		}
		
		return cache;
	}
			
}