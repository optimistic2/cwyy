package com.cwyy.dic.web;

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
import com.cwyy.dic.service.IDicService;
import com.cwyy.dic.vo.DicVO;

@Controller
@RequestMapping("dic")
public class DicController {

	@Autowired
	private IDicService dicService;

	/**
	 * 字典保存修改
	 * 
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "addDic", method = RequestMethod.POST)
	@ResponseBody
	public SimpleResponse addDic(@RequestBody DicVO vo) {
		SimpleResponse result = new SimpleResponse();
		boolean flag = false;
		String id = vo.getId();
		String code = vo.getCode();
		DicVO codeVO = dicService.getDicByCode(code);
		// 判断是否是新增
		if (StringUtils.isBlank(id)) {
			if (StringUtils.isBlank(code) || StringUtils.isBlank(vo.getName())) {
				result.setCode(false);
				result.setMsg("字典编码和名称不能为空");
				return result;
			}
			if(null!=codeVO) {
				result.setCode(false);
				result.setMsg("字典编码已经存在");
				return result;
			}
		}else {
			if (StringUtils.isBlank(code) || StringUtils.isBlank(vo.getName())) {
				result.setCode(false);
				result.setMsg("字典编码和名称不能为空");
				return result;
			}
			// 根据id查询字典
			DicVO oldVO = dicService.getDicById(id);
			if(!code.equals(oldVO.getCode()) && null!=codeVO) {
				result.setCode(false);
				result.setMsg("字典编码已经存在");
				return result;
			}
		}
		flag = dicService.saveDic(vo);
		return new SimpleResponse(flag);
	}

	/**
	 * 字典分页查询
	 * 
	 * @param ps
	 * @param pn
	 * @param param
	 * @param sortType
	 * @return
	 */
	@RequestMapping(value = "pageDic", method = RequestMethod.GET)
	@ResponseBody
	public ObjectResponse<Page<DicVO>> pageDic(@RequestParam int ps, @RequestParam int pn,
			@RequestParam(required = false) String param, @RequestParam(required = false) String sortType) {
		ObjectResponse<Page<DicVO>> result = new ObjectResponse<>();
		PageRequest pageRequest = QueryTool.buildPageRequest(pn, ps, sortType);
		Page<DicVO> page = dicService.queryDicPage(pageRequest, param);
		result.setCode(true);
		result.setMsg("查询成功");
		result.setData(page);
		return result;
	}
	
	/**
	 * 根据字典id查询字典
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "getDicById", method = RequestMethod.GET)
	@ResponseBody
	public ObjectResponse<DicVO> getDicById(@RequestParam String id) {
		ObjectResponse<DicVO> result = new ObjectResponse<>();
		DicVO dic = dicService.getDicById(id);
		result.setCode(true);
		result.setMsg("查询成功");
		result.setData(dic);
		return result;
	}
}
