package com.cwyy.base.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@MappedSuperclass
public class BaseEntity implements Serializable {
	private static final long serialVersionUID = -813150714553235181L;
	
	@Temporal(TemporalType.TIMESTAMP) 
	@Column(name="create_time", updatable = false)  
	@CreationTimestamp
	private Date createTime;
	
	@Temporal(TemporalType.TIMESTAMP) 
	@UpdateTimestamp  
	@Column(name="update_time")
	private Date updateTime;

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
