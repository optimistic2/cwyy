package com.cwyy.base.response.constant;

/**
 * http请求返回码枚举
 */
public enum ReturnCode {

	/**
	 * 返回成功
	 */
	SUCCESS("success"),
	/**
	 * 返回失败
	 */
	FAILURE("failure");

	private String value;

	ReturnCode(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
