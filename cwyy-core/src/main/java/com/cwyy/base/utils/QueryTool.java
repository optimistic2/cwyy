package com.cwyy.base.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;


public final class QueryTool {
	/**
	 * 默认修改日期倒序
	 */
	public static final String DEFAULT_SORT = "auto";
	public static final Direction DESC = Direction.DESC;
	public static final Direction ASC = Direction.ASC;
	/**
	 * 构建分页查询分页条件
	 * @param pageNumber
	 * @param pagzSize
	 * @param sortType
	 * @return
	 */
	public static PageRequest buildPageRequest(int pageNumber, int pageSize, String sortType) {
		Sort sort = null;
		if ("auto".equals(sortType)||org.apache.commons.lang.StringUtils.isBlank(sortType)) {
			sort = new Sort(Direction.DESC, "updateTime");
		} else {
			sort = new Sort(Direction.ASC, sortType);
		}
		return new PageRequest(pageNumber - 1, pageSize, sort);
	}
	
	public static PageRequest buildPageRequest(int pageNumber, int pageSize, String... sortType) {
		Sort sort = null;
		sort = new Sort(Direction.ASC, sortType);
		return new PageRequest(pageNumber - 1, pageSize, sort);
	}
	
}
