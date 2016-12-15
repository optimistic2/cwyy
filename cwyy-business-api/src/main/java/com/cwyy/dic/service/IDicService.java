package com.cwyy.dic.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.cwyy.dic.vo.DicVO;

public interface IDicService {
	/**
	 * 保存字典
	 * @param vo
	 * @return
	 */
	boolean saveDic(DicVO vo);

	/**
	 * 字典分页查询
	 * @param pageRequest
	 * @param param
	 * @return
	 */
	Page<DicVO> queryDicPage(PageRequest pageRequest, String param);

	/**
	 * 根据id查询字典信息
	 * @param id
	 * @return
	 */
	DicVO getDicById(String id);
	
	/**
	 * 根据code查询字典信息
	 * @param code
	 * @return
	 */
	DicVO getDicByCode(String code);
	
	/**
	 * 根据字典类型code查询字典列表
	 * @param code
	 * @return
	 */
	List<DicVO> getDicByTypeCode(String code);

	/**
	 * 根据id删除字典
	 * @param ids
	 * @return
	 */
	boolean deleteDicByIds(List<String> ids);
}
