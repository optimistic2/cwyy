package com.cwyy.dic.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import com.cwyy.dic.entity.DicEntity;

public interface IDicDao extends CrudRepository<DicEntity, String> ,PagingAndSortingRepository<DicEntity, String>, JpaSpecificationExecutor<DicEntity> {

	@Query(value = "select * from cy_bd_dic f left join cy_bd_dic k on f.dic_id = k.id where k.code = ?1 ", nativeQuery = true)
	List<DicEntity> getDicByTypeCode(String code);

	@Modifying
	@Transactional
	@Query(value = "delete from cy_bd_dic where id in (?1) ", nativeQuery = true)
	void deleteBatch(List<String> ids);
	
	
	
}
