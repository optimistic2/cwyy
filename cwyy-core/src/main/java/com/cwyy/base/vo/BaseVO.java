package com.cwyy.base.vo;

import java.io.Serializable;
import java.util.Date;

public class BaseVO implements Serializable {
	private static final long serialVersionUID = -8246178334338536445L;

	private String id;

	private Date createTime;
	
	private Date updateTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	
}
