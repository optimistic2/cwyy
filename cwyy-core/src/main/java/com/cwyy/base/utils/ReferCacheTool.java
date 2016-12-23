package com.cwyy.base.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;

import com.alibaba.fastjson.JSONObject;
import com.cwyy.base.annotation.Refer;
import com.cwyy.base.constant.ReferConstant;
import com.cwyy.base.entity.BaseEntity;

/**
 * 
 * @author hupeng 2016年10月14日
 *
 */
public class ReferCacheTool {
	static RedisTemplate<String, Map<String, JSONObject>> redisTemplate = ContextUtils.getBean(RedisTemplate.class);
	static RedisTemplate<String, JSONObject> redisTemplateVO = ContextUtils.getBean(RedisTemplate.class);

	public static void putReferCache(Object t) {
		if (t instanceof BaseEntity) {
			Refer refer = t.getClass().getAnnotation(Refer.class);
			if (refer != null) {
				Object referId = ((BaseEntity) t).getAttributeValue(refer.id());
				Object referCode = ((BaseEntity) t).getAttributeValue(refer.code());
				Object referName = ((BaseEntity) t).getAttributeValue(refer.name());
				Object referParentId = ((BaseEntity) t).getAttributeValue(refer.parentId());
				String redisId = refer.referCode() + ReferConstant.REFER_UNDERLINE + referId;
				JSONObject json = new JSONObject();
				json.put(ReferConstant.REFER_ID, referId);
				json.put(ReferConstant.REFER_CODE, referCode);
				json.put(ReferConstant.REFER_NAME, referName);
				json.put(ReferConstant.REFER_PARENTID, referParentId);
				redisTemplateVO.opsForValue().set(redisId, json);
				System.out.println("参照缓存新增保存");
			}
		}
	}

	public static void deleteReferCache(Object entity) {
		if (entity instanceof BaseEntity) {
			Refer refer = entity.getClass().getAnnotation(Refer.class);
			if (refer != null) {
				Object referId = ((BaseEntity) entity).getAttributeValue(refer.id());
				String redisId = refer.referCode() + ReferConstant.REFER_UNDERLINE + referId;
				redisTemplateVO.delete(redisId);
				System.out.println("参照缓存删除" + redisId);
			}
		}
	}

	/**
	 * 批量添加参照索引
	 * 
	 * @param lst
	 * @param clazz
	 */
	public static void putBatchReferCache(List<? extends BaseEntity> lst, Class<?> clazz) {

		if (lst != null && !lst.isEmpty()) {
			Long add = 0L;
			Map<String, Map<String, JSONObject>> map = new HashMap<>();
			Refer refer = clazz.getAnnotation(Refer.class);
			long start = System.currentTimeMillis();
			if (refer != null) {
				List<JSONObject> redisVOs = new ArrayList<>();
				for (Object t : lst) {
					add++; 
					if (t instanceof BaseEntity) {
						Object referId = ((BaseEntity) t).getAttributeValue(refer.id());
						Object referCode = ((BaseEntity) t).getAttributeValue(refer.code());
						Object referName = ((BaseEntity) t).getAttributeValue(refer.name());
						Object referParentId = ((BaseEntity) t).getAttributeValue(refer.parentId());
						String key = refer.referCode() + ReferConstant.REFER_UNDERLINE + referId;
						JSONObject json = new JSONObject();
						json.put(ReferConstant.REFER_ID, referId);
						json.put(ReferConstant.REFER_CODE, referCode);
						json.put(ReferConstant.REFER_NAME, referName);
						json.put(ReferConstant.REFER_PARENTID, referParentId);
						redisTemplateVO.opsForValue().set(key, json);
						if (null != referParentId) {
							if (((referParentId instanceof String) && StringUtils.isNotBlank((String) referParentId))
									|| !(referParentId instanceof String)) {
								Map<String, JSONObject> mapTempVO = new HashMap<>();
								String sonKey = refer.referCode() + ReferConstant.REFER_UNDERLINE + referId;
								mapTempVO.put(sonKey, json);
								String parentKey = ReferConstant.REFER_PARENT + ReferConstant.REFER_UNDERLINE
										+ refer.referCode() + ReferConstant.REFER_UNDERLINE + referParentId;
								if (null != map.get(parentKey)) {
									mapTempVO.putAll(map.get(parentKey));
								}
								map.put(parentKey, mapTempVO);
							}
						}
						redisVOs.add(json);
					}
				}
				for (String key : map.keySet()) {
					add++;
					redisTemplate.opsForValue().set(key, map.get(key));
				}
				long end = System.currentTimeMillis();
				System.out.println("hmset with pipeline used [" + (end - start) / 1000 + "] seconds ..");
				System.out.println("总数量：：：：：" + add);
			}
		}
	}

}
