package com.cwyy.base.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Component;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface Refer {

	String value() default "";

	/**
	 * 对应编码
	 * 
	 * @return
	 */
	public String referCode() default "";

	/**
	 * ID
	 * 
	 * @return
	 */
	public String id() default "id";

	/**
	 * CODE
	 * 
	 * @return
	 */
	public String code() default "code";

	/**
	 * 名称
	 * 
	 * @return
	 */
	public String name() default "name";
	
	/**
	 * 对应上级主键
	 * @return
	 */
	public String parentId() default  "";
	
}
