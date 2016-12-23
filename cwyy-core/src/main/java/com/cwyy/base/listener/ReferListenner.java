package com.cwyy.base.listener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.cwyy.base.annotation.Refer;
import com.cwyy.base.entity.BaseEntity;
import com.cwyy.base.exception.BusinessException;
import com.cwyy.base.repository.EntityNativeQuery;
import com.cwyy.base.utils.ContextUtils;
import com.cwyy.base.utils.ReferCacheTool;

@Component
public class ReferListenner implements ApplicationListener<ApplicationContextEvent> {
	@Autowired
	private EntityNativeQuery<? extends BaseEntity> query;
	/**
	 * Y的时候初始化
	 */
	@Value("${refer.init:N}")
	private String referInit;

	@Override
	public void onApplicationEvent(ApplicationContextEvent event) {
//		if (event.getApplicationContext().getParent() != null && referInit.equals("Y")) {
			Map<String, Object> beansWithAnnotationMap = ContextUtils.getApplicationContext()
					.getBeansWithAnnotation(Refer.class);

			for (String key : beansWithAnnotationMap.keySet()) {
				System.out.println("beanName= " + key);
				Object bean = beansWithAnnotationMap.get(key);
				Refer refer = bean.getClass().getAnnotation(Refer.class);
				if (refer != null) {
					List<String> filednames = new ArrayList<>();
					filednames.add(refer.id());
					filednames.add(refer.code());
					filednames.add(refer.name());
					if(!StringUtils.isEmpty(refer.parentId())){
						filednames.add(refer.parentId());
					}
					List<BaseEntity> lst = null;
					try {
						lst = (List<BaseEntity>) query.query((Class<? extends BaseEntity>) bean.getClass(),
								filednames, null,null);
					} catch (BusinessException e) {
						throw new RuntimeException(e);
					}
					ReferCacheTool.putBatchReferCache(lst, bean.getClass());
				}
			}
//		}

	}

}
