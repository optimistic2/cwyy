package com.cwyy.user.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.cwyy.user.vo.UserVO;

public interface IUserService {

	/**
	 * 条件不为空，按名称和code分页模糊查询符合条件的用户
	 * @param pageRequest
	 * @param param
	 * @return
	 */
	Page<UserVO> queryUserPage(PageRequest pageRequest, String param);

	/**
	 * 根据code查询用户
	 * @param code
	 * @return
	 */
	UserVO getUserByCode(String code);

	/**
	 * 根据id查询用户信息
	 * @param id
	 * @return
	 */
	UserVO getUserById(String id);

	/**
	 * 新增修改用户信息
	 * @param vo
	 * @return
	 */
	boolean saveUser(UserVO vo);

}
