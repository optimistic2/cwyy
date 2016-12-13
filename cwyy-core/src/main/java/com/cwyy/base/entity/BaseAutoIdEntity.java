package com.cwyy.base.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

@MappedSuperclass
public class BaseAutoIdEntity extends BaseEntity {
	private static final long serialVersionUID = -813150714553235181L;
	@Id
	@GenericGenerator(name="uuid", strategy="uuid")
    @GeneratedValue(generator="uuid")
	private String id;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
