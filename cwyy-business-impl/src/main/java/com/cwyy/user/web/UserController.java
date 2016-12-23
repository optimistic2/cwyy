package com.cwyy.user.web;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cwyy.base.response.ObjectResponse;
import com.cwyy.base.response.SimpleResponse;
import com.cwyy.base.utils.QueryTool;
import com.cwyy.user.service.IUserService;
import com.cwyy.user.vo.UserVO;

@Controller
@RequestMapping("user")
public class UserController {
	@Autowired
	private IUserService userService;
	
	/**
	 * 根据条件分页查询用户
	 * @param ps
	 * @param pn
	 * @param param
	 * @param sortType
	 * @return
	 */
	@RequestMapping(value = "pageUser", method = RequestMethod.GET)
	@ResponseBody
	public ObjectResponse<Page<UserVO>> pageDic(@RequestParam int ps, @RequestParam int pn,
			@RequestParam(required = false) String param, @RequestParam(required = false) String sortType) {
		ObjectResponse<Page<UserVO>> result = new ObjectResponse<>();
		PageRequest pageRequest = QueryTool.buildPageRequest(pn, ps, sortType);
		Page<UserVO> page = userService.queryUserPage(pageRequest, param);
		result.setCode(true);
		result.setMsg("查询成功");
		result.setData(page);
		return result;
	}
	
	/**
	 * 新增或修改用户
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "addUser", method = RequestMethod.POST)
	@ResponseBody
	public SimpleResponse addUser(@RequestBody UserVO vo) {
		SimpleResponse result = new SimpleResponse();
		boolean flag = false;
		String id = vo.getId();
		String code = vo.getCode();
		UserVO codeVO = userService.getUserByCode(code);
		// 判断是否是新增
		if (StringUtils.isBlank(id)) {
			if (StringUtils.isBlank(code) || StringUtils.isBlank(vo.getName())) {
				result.setCode(false);
				result.setMsg("用户编码和名称不能为空");
				return result;
			}
			if(null!=codeVO) {
				result.setCode(false);
				result.setMsg("用户编码已经存在");
				return result;
			}
		}else {
			if (StringUtils.isBlank(code) || StringUtils.isBlank(vo.getName())) {
				result.setCode(false);
				result.setMsg("用户编码和名称不能为空");
				return result;
			}
			// 根据id查询用户
			UserVO oldVO = userService.getUserById(id);
			if(!code.equals(oldVO.getCode()) && null!=codeVO) {
				result.setCode(false);
				result.setMsg("用户编码已经存在");
				return result;
			}
		}
		flag = userService.saveUser(vo);
		return new SimpleResponse(flag);
	}
}
