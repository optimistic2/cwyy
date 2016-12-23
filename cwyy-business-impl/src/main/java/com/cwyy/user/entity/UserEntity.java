package com.cwyy.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.cwyy.base.entity.BaseAutoIdEntity;


/**
 * The persistent class for the cy_bd_user database table.
 * 
 */
@Entity
@Table(name="cy_bd_user")
@NamedQuery(name="CyBdUser.findAll", query="SELECT c FROM UserEntity c")
public class UserEntity extends BaseAutoIdEntity {

	private static final long serialVersionUID = -5900876320552398255L;

	private String code;

	private String name;

	private String password;

	@Column(name="user_type")
	private String userType;

	public UserEntity() {
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserType() {
		return this.userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

}