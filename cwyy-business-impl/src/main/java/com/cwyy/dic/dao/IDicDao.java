package com.cwyy.dic.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.cwyy.dic.entity.DicEntity;

public interface IDicDao extends PagingAndSortingRepository<DicEntity, Serializable>, JpaSpecificationExecutor<DicEntity> {

	@Query(value = "select * from cy_bd_dic f left join cy_bd_dic k on f.dic_id = k.id where k.code = ?1 ", nativeQuery = true)
	List<DicEntity> getDicByTypeCode(String code);
	
}
