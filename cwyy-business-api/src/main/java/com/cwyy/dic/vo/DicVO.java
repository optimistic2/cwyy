package com.cwyy.dic.vo;

import com.cwyy.base.vo.BaseVO;

public class DicVO extends BaseVO {
	private static final long serialVersionUID = 6900535217793326645L;

	private String code;
	
	private String dicId;

	private String name;

	private String description;

	public DicVO() {
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDicId() {
		return this.dicId;
	}

	public void setDicId(String dicId) {
		this.dicId = dicId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}