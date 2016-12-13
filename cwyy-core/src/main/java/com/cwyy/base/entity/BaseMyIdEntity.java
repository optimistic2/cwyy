package com.cwyy.base.entity;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseMyIdEntity extends BaseEntity {
	private static final long serialVersionUID = -813150714553235181L;
	@Id
	private String id;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
