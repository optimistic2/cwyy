package com.cwyy.dic.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.cwyy.base.annotation.Refer;
import com.cwyy.base.entity.BaseAutoIdEntity;

/**
 * 
 * <p>
 * <b>本类主要完成以下功能：</b>
 * 
 * <ul>
 * <li>字典实体
 * <li>...
 * </ul>
 *
 * <p>
 * 
 * @version 本版本号
 * @author cby
 * @time 2016年12月20日 上午9:54:15
 */
@Refer(id = "id", code = "code", name = "name", parentId = "dicId", referCode = "dic")
@Entity
@Table(name = "cy_bd_dic")
@NamedQuery(name = "DicEntity.findAll", query = "SELECT d FROM DicEntity d")
public class DicEntity extends BaseAutoIdEntity implements Serializable {

	private static final long serialVersionUID = -3833096620163084820L;

	private String code;

	@Column(name = "dic_id")
	private String dicId;

	private String name;

	private String description;

	public DicEntity() {
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