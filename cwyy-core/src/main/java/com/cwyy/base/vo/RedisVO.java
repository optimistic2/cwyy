package com.cwyy.base.vo;

import java.io.Serializable;

public class RedisVO implements Serializable {
	private static final long serialVersionUID = 3885373430687434379L;

	private String id;
	
	private String code;
	
	private String name;
	
	private Boolean selected;
	
	private String parentId;
	
	/**
	 * RedisVO 的构造子
	 * @param id
	 * @param code
	 * @param name
	 * @param selected
	 */
	public RedisVO(String id, String code, String name, String parentId) {
		super();
		this.id = id;
		this.code = code;
		this.name = name;
		this.parentId = parentId;
	}

	public RedisVO(){}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getSelected() {
		return selected;
	}

	public void setSelected(Boolean selected) {
		this.selected = selected;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
}
