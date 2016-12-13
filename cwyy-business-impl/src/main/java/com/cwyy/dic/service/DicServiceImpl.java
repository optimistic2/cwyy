package com.cwyy.dic.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.cwyy.dic.dao.IDicDao;
import com.cwyy.dic.entity.DicEntity;
import com.cwyy.dic.vo.DicVO;

@Service
public class DicServiceImpl implements IDicService {
	@Autowired
	private IDicDao dicDao;

	@Override
	public boolean saveDic(DicVO vo) {
		DicEntity entity = new DicEntity();
		BeanUtils.copyProperties(vo, entity);
		entity = dicDao.save(entity);
		if (entity != null && StringUtils.isNotBlank(entity.getId())) {
			return true;
		}
		return true;
	}

	@Override
	public Page<DicVO> queryDicPage(PageRequest pageRequest, String param) {
		final String paramFinal = param;
		Specification<DicEntity> spec = new Specification<DicEntity>() {

			@Override
			public Predicate toPredicate(Root<DicEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				if (StringUtils.isNotBlank(paramFinal)) {
					Predicate p1 = cb.like(root.get("code"), "%" + paramFinal + "%");
					Predicate p2 = cb.like(root.get("name"), "%" + paramFinal + "%");
					return cb.or(p1, p2);
				}
				return null;
			}

		};
		Page<DicEntity> page = dicDao.findAll(spec, pageRequest);
		List<DicVO> list = new ArrayList<>();
		for (DicEntity po : page) {
			DicVO vo = new DicVO();
			BeanUtils.copyProperties(po, vo);
			list.add(vo);
		}
		return new PageImpl<DicVO>(list, pageRequest, page.getTotalElements());
	}

	@Override
	public DicVO getDicById(String id) {
		DicVO vo = new DicVO();
		DicEntity po = dicDao.findOne(id);
		BeanUtils.copyProperties(po, vo);
		return vo;
	}

	@Override
	public DicVO getDicByCode(String code) {
		DicVO vo = new DicVO();
		final String codeFinal = code;
		Specification<DicEntity> spec = new Specification<DicEntity>() {
			@Override
			public Predicate toPredicate(Root<DicEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate p1 = cb.equal(root.get("code"), codeFinal);
				return p1;
			}
		};
		DicEntity po = dicDao.findOne(spec);
		BeanUtils.copyProperties(po, vo);
		return vo;
	}

	@Override
	public List<DicVO> getDicByTypeCode(String code) {
		List<DicVO> result = new ArrayList<>();
		List<DicEntity> list = dicDao.getDicByTypeCode(code);
		for(DicEntity po:list) {
			DicVO vo = new DicVO();
			BeanUtils.copyProperties(po, vo);
			result.add(vo);
		}
		return result;
	}

}
