package com.cwyy.base.repository;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.Query;
import javax.persistence.Table;

import org.apache.commons.lang.ArrayUtils;

import com.cwyy.base.entity.BaseEntity;
import com.cwyy.base.utils.BeanHelper;

public abstract class AbstractEntityOperator<T extends BaseEntity> {

	private Class<? extends BaseEntity> clazz;
	protected String primaryDbFiled = null;
	protected String primaryFiled = null;

	public String getPrimaryDbFiled() {
		return primaryDbFiled;
	}

	public void setPrimaryDbFiled(String primaryDbFiled) {
		this.primaryDbFiled = primaryDbFiled;
	}

	public String getPrimaryFiled() {
		return primaryFiled;
	}

	public void setPrimaryFiled(String primaryFiled) {
		this.primaryFiled = primaryFiled;
	}

	private String talbeName;

	protected Class<? extends BaseEntity> getClazz() {
		return clazz;
	}

	// DB字段
	private List<String> dbFiledNames = new ArrayList<>();

	protected List<String> getDbFiledNames() {
		return dbFiledNames;
	}

	protected void setDbFiledNames(List<String> dbFiledNames) {
		this.dbFiledNames = dbFiledNames;
	}

	protected String getTalbeName() {
		return talbeName;
	}

	protected void setTalbeName() {
		Table table = getClazz().getAnnotation(Table.class);
		this.talbeName = table.name();
	}

	private Map<String, String> dbFiledMap = new HashMap<String, String>();

	protected EntityManager entityManager;

	public AbstractEntityOperator(EntityManager entityManager) {
		super();
		this.entityManager = entityManager;
	}

	protected void initOperator(Class<? extends BaseEntity> clazz) {
		initTable(clazz);
	}

	protected void initTable(Class<? extends BaseEntity> clazz) {
		this.clazz = clazz;
		setTalbeName();
	}

	protected String getDbFiledname(String filedName) {
		return dbFiledMap.get(filedName);
	}

	protected void setFieldName(Class<?> clazz) {
		dbFiledMap.clear();
		Field[] fields = clazz.getDeclaredFields();
		Field[] superFields = clazz.getSuperclass().getDeclaredFields();
		Field[] totalFields = (Field[]) ArrayUtils.addAll(fields, superFields);
		List<String> currentFieldNames = new ArrayList<String>();
		for (Field field : fields) {
			currentFieldNames.add(field.getName());
		}
		for(Field field : superFields) {
			currentFieldNames.add(field.getName());
		}
		if (currentFieldNames.size() > 0) {
			BeanHelper beanHelper = new BeanHelper();
			Method[] currentMethods = beanHelper.getAllGetMethod(clazz, currentFieldNames.toArray(new String[0]));

			for (int i = 0; i < totalFields.length; i++) {
				Column column = null;
				// 优先考虑注解放在字段上的
				if (totalFields[i].getAnnotation(Column.class) != null) {
					column = totalFields[i].getAnnotation(Column.class);
				} else {
					if (currentMethods[i] != null) {
						column = currentMethods[i].getAnnotation(Column.class);
					}
				}
				if (column != null) {
					setPrimaryColumn(totalFields[i], currentMethods[i], column);
					dbFiledMap.put(totalFields[i].getName(), column.name());
				}else {
					dbFiledMap.put(totalFields[i].getName(), totalFields[i].getName());
				}
			}
			
		}
	}

	/**
	 * 设置主键字段
	 * 
	 * @param field
	 * @param method
	 * @param column
	 */
	private void setPrimaryColumn(Field field, Method method, Column column) {
		Id id = null;
		if (field.getAnnotation(Id.class) != null)
			id = field.getAnnotation(Id.class);
		if (method.getAnnotation(Id.class) != null)
			id = method.getAnnotation(Id.class);
		if (id != null) {
			primaryDbFiled = column.name();
			primaryFiled = field.getName();
		}

	}

	/**
	 * 按顺序设置Query参数
	 *
	 * @param query
	 * @param paras
	 */
	protected void setParameters(Query query, Map<String, Object> paras) {
		if (paras == null || paras.isEmpty()) {
			return;
		}
		Set<String> set = paras.keySet();
		for (String key : set) {
			query.setParameter(key, paras.get(key));
		}
	}

	protected StringBuffer constructSQL(List<String> FiledNames, String condition, String order) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select ");
		// 要查询的字段
		sql.append(this.constructQueryField(FiledNames));
		sql.append(" from ");
		sql.append(getTalbeName());

		// 额外的条件
		if (condition != null) {
			sql.append(" ");
			sql.append(condition);
		}
		// 排序语句
		if (order != null) {
			sql.append(" ");
			sql.append(order);
		}
		return sql;
	}

	private StringBuffer constructQueryField(List<String> filedNames) {
		StringBuffer sql = new StringBuffer();
		for (int i = 0; i < filedNames.size(); i++) {
			sql.append(filedNames.get(i));
			if (filedNames.size() - 1 == i) {
				sql.append("");
			} else {
				sql.append(",");
			}
		}
		return sql;
	}
}
