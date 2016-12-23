package com.cwyy.base.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cwyy.base.vo.RedisVO;

public class RedisVOTool {
	public List<RedisVO> transMapToList(Map<String, RedisVO> redisMap) {
		if(null==redisMap) {
			return null;
		}
		List<RedisVO> result = new ArrayList<>();
		for(String key:redisMap.keySet()) {
			result.add(redisMap.get(key));
		}
		return result;
	}
	
	public Map<String, RedisVO> transListToMap(List<RedisVO> redisList) {
		Map<String, RedisVO> result = new HashMap<>();
		if(null==redisList) {
			return null;
		}
		for(RedisVO vo : redisList) {
			result.put(vo.getId(), vo);
		}
		return result;
	}
}
