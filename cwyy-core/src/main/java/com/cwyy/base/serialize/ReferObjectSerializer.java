package com.cwyy.base.serialize;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.data.redis.core.RedisTemplate;

import com.alibaba.fastjson.JSONObject;
import com.cwyy.base.annotation.ReferJsonSerialize;
import com.cwyy.base.constant.ReferConstant;
import com.cwyy.base.utils.ContextUtils;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;

public class ReferObjectSerializer extends JsonSerializer<Object> implements ContextualSerializer {

	private String referCode;

	public ReferObjectSerializer(String referCode) {
		super();
		this.referCode = referCode;
	}

	// 必须要保留无参构造方法
	public ReferObjectSerializer() {
		super();
	}

	@Override
	public void serialize(Object value, JsonGenerator jgen, SerializerProvider provider)
			throws IOException, JsonProcessingException {
		RedisTemplate<String, JSONObject> redisTemplateVO = ContextUtils.getBean(RedisTemplate.class);
		RedisTemplate<String, Map<String, JSONObject>> redisTemplate = ContextUtils.getBean(RedisTemplate.class);
		String key = referCode + ReferConstant.REFER_UNDERLINE + value;
		JSONObject json = redisTemplateVO.opsForValue().get(key);
		if ("dic".equals(referCode)) {
			String parentId = json.getString(ReferConstant.REFER_PARENTID);
			String parentKey = ReferConstant.REFER_PARENT + ReferConstant.REFER_UNDERLINE + referCode
					+ ReferConstant.REFER_UNDERLINE + parentId;
			Map<String, JSONObject> map = redisTemplate.opsForValue().get(parentKey);
			List<JSONObject> list = createSelectList(map, key);
			jgen.writeObject(list);
		} else {
			jgen.writeObject(json);
		}
	}

	@Override
	public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property)
			throws JsonMappingException {
		if (property != null) { // 为空直接跳过
			if (Objects.equals(property.getType().getRawClass(), String.class)) { // 非String类直接跳过
				AnnotatedMethod method = (AnnotatedMethod) property.getMember();
				ReferJsonSerialize referSerialTransfer = method.getAnnotated()
						.getDeclaredAnnotation(ReferJsonSerialize.class);
				if (referSerialTransfer != null) { // 如果能得到注解，就将注解的value传入ReferJsonSerialize
					return new ReferObjectSerializer(referSerialTransfer.referCode());
				}
			}
			return prov.findValueSerializer(property.getType(), property);
		}
		return prov.findNullValueSerializer(property);
	}

	private List<JSONObject> createSelectList(Map<String, JSONObject> map, String selected) {
		List<JSONObject> result = new ArrayList<>();
		for (String key : map.keySet()) {
			JSONObject json = map.get(key);
			if (selected.equals(key)) {
				json.put("selected", true);
			} else {
				json.put("selected", false);
			}
			result.add(json);
		}
		return result;
	}
}
