package com.springboot.cs.common.utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;

import java.util.List;

@Component
public class JedisUtil {

	@Autowired
	private JedisPool jedisPool;

	public synchronized Jedis getJedis(){
		try{
			if(jedisPool != null){
				Jedis jedis = jedisPool.getResource();
				return jedis;
			}else{
				return null;
			}
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 设置redis,带过期时间
	 * @param key
	 * @param value
	 * @param cacheSeconds
	 * @return
	 */
	public String set(String key,String value,Integer cacheSeconds) {
		Jedis jedis = getJedis();
		String result = "";
		try {
			result = jedis.set(key, value);
			jedis.expire(key,cacheSeconds);
		} finally {
			jedis.close();
		}
		return result;



	}

	/**
	 * 设置redis，不带过期时间
	 * @param key
	 * @param value
	 * @return
	 */
	public String set(String key, String value) {
		Jedis jedis = getJedis();
		String result = "";
		try {
			result = jedis.set(key, value);
		} finally {
			jedis.close();
		}
		return result;

	}


	/**
	 * 获取
	 * @param key
	 * @return
	 */
	public String get(String key) {

		Jedis jedis = getJedis();
		String result = "";
		try {
			result = jedis.get(key);
		} finally {
			jedis.close();
		}
		return result;
	}

	/**
	 * 判断是否存在
	 * @param key
	 * @return
	 */
	public Boolean exists(String key) {

		Jedis jedis = getJedis();
		Boolean result;
		try {
			result = jedis.exists(key);
			return result;
		} finally {
			jedis.close();
		}
	}

	/**
	 * 设置过期时间
	 * @param key
	 * @param seconds
	 * @return
	 */
	public Long expire(String key, int seconds) {

		Jedis jedis = getJedis();
		Long result;
		try {
			result = jedis.expire(key, seconds);
			return result;
		} finally {
			jedis.close();
		}

	}

	public Long ttl(String key) {
		Jedis jedis = getJedis();
		Long result;
		try {
			result = jedis.ttl(key);
			return result;
		} finally {
			jedis.close();
		}

	}

	public Long incr(String key) {

		Jedis jedis = getJedis();
		Long result;
		try {
			result = jedis.incr(key);
			return result;
		} finally {
			jedis.close();
		}

	}

	public Long hset(String key, String field, String value) {

		Jedis jedis = getJedis();
		Long result;
		try {
			result = jedis.hset(key, field, value);
			return result;
		} finally {
			jedis.close();
		}

	}

	public String hget(String key, String field) {

		Jedis jedis = getJedis();
		String result;
		try {
			result = jedis.hget(key, field);
			return result;
		} finally {
			jedis.close();
		}
	}

	public Long hdel(String key, String... field) {

		Jedis jedis = getJedis();
		Long result;
		try {
			result = jedis.hdel(key, field);
			return result;
		} finally {
			jedis.close();
		}

	}

	/**
	 * 删除指定的key
	 * @param key
	 */
	public void del(String key){

		Jedis jedis = getJedis();
		Long result;
		try {
			jedis.del(key);
		} finally {
			jedis.close();
		}

	}

	/**
	 * 删除指定的key
	 * @param list
	 */
	public void del(List<String> list){

		Jedis jedis = getJedis();
		try {

			String[] arr = new String[list.size()];
			for(int i = 0; i <list.size(); i ++) {
				arr[i] = list.get(i);
			}
			jedis.del(arr);
		} finally {
			jedis.close();
		}

	}

	/**
	 * setnx
	 * @param key,value,time
	 */
	public Long setnx(String key,String value,Integer time){

		Jedis jedis = getJedis();
		try {
			Long setnx = jedis.setnx(key, value);
			jedis.expire(key,time);
			return setnx;
		} finally {
			jedis.close();
		}

	}

	/**
	 * mset
	 * @param keysvalues
	 */
	public void mset(String... keysvalues){

		Jedis jedis = getJedis();
		try {
			if(keysvalues == null || keysvalues.length < 1) {
				return;
			}
			jedis.mset(keysvalues);
		} finally {
			jedis.close();
		}

	}

	public List<String> mget(String... keysvalues){

		Jedis jedis = getJedis();
		try {
			List<String> mget = jedis.mget(keysvalues);
			return mget;
		} finally {
			jedis.close();
		}

	}

}