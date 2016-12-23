package com.cwyy.user.vo;

import com.cwyy.base.annotation.ReferJsonSerialize;
import com.cwyy.base.serialize.ReferObjectSerializer;
import com.cwyy.base.vo.BaseVO;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class UserVO extends BaseVO{

	private static final long serialVersionUID = 3680044432735027103L;

	private String code;

	private String name;

	private String password;
	
	private String userType;

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@ReferJsonSerialize(referCode = "dic")
	@JsonSerialize(using = ReferObjectSerializer.class)
	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}
	
	
}
