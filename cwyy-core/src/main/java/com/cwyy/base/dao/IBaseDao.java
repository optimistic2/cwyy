package com.cwyy.base.dao;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.cwyy.base.entity.BaseAutoIdEntity;

public interface IBaseDao<ET extends BaseAutoIdEntity, ID extends Serializable>
		extends PagingAndSortingRepository<ET, ID>, JpaSpecificationExecutor<ET> {

}
