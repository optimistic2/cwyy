package com.cwyy.base.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cwyy.base.entity.BaseEntity;
import com.cwyy.base.exception.BusinessException;

@Component
public class EntityNativeQuery<T extends BaseEntity> extends AbstractEntityOperator<T> {

	@Autowired
	public EntityNativeQuery(EntityManager entityManager) {
		super(entityManager);
	}

	@SuppressWarnings("unchecked")
	public List<T> query(Class<? extends BaseEntity> clazz, List<String> filedNames,String conditionSql,  String order)
			throws BusinessException {
		getDbFiledNames().clear();
		initOperator(clazz);
		setFieldName(clazz);
		List<String> dbfiledNames = new ArrayList<>();
		for (String filedname : filedNames) {
			dbfiledNames.add(getDbFiledname(filedname));
		}

		StringBuffer sql = constructSQL(dbfiledNames, conditionSql, order);
		Query rquery = entityManager.createNativeQuery(sql.toString());
		List<Object> objs = rquery.getResultList();
		try {
			return convertResult(objs,filedNames);
		} catch (Exception e) {
			throw new BusinessException(e);
		}
	}

	protected List<T> convertResult(List<Object> objs, List<String> fieldNames)
			throws InstantiationException, IllegalAccessException {
		List<T> result = new ArrayList<T>();
		for (Object obj : objs) {
			Object[] item = (Object[]) obj;
			@SuppressWarnings("unchecked")
			T entity = (T) getClazz().newInstance();
			for (int i = 0; i < item.length; i++) {
				entity.setAttributeValue(fieldNames.get(i), item[i]);
			}
			result.add(entity);
		}
		return result;
	}

}
