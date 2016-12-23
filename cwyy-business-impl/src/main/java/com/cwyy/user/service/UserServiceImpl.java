package com.cwyy.user.service;

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

import com.cwyy.user.dao.IUserDao;
import com.cwyy.user.entity.UserEntity;
import com.cwyy.user.vo.UserVO;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private IUserDao userDao;
	
	@Override
	public Page<UserVO> queryUserPage(PageRequest pageRequest, String param) {
		final String paramFinal = param;
		Specification<UserEntity> spec = new Specification<UserEntity>() {

			@Override
			public Predicate toPredicate(Root<UserEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				if (StringUtils.isNotBlank(paramFinal)) {
					Predicate p1 = cb.like(root.get("code"), "%" + paramFinal + "%");
					Predicate p2 = cb.like(root.get("name"), "%" + paramFinal + "%");
					return cb.or(p1, p2);
				}
				return null;
			}

		};
		Page<UserEntity> page = userDao.findAll(spec, pageRequest);
		List<UserVO> list = new ArrayList<>();
		for (UserEntity po : page) {
			UserVO vo = new UserVO();
			BeanUtils.copyProperties(po, vo);
			list.add(vo);
		}
		return new PageImpl<UserVO>(list, pageRequest, page.getTotalElements());
	}

	@Override
	public UserVO getUserByCode(String code) {
		UserVO vo = new UserVO();
		final String codeFinal = code;
		Specification<UserEntity> spec = new Specification<UserEntity>() {
			@Override
			public Predicate toPredicate(Root<UserEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate p1 = cb.equal(root.get("code"), codeFinal);
				return p1;
			}
		};
		UserEntity po = userDao.findOne(spec);
		if (null != po) {
			BeanUtils.copyProperties(po, vo);
		}else {
			return null;
		}
		return vo;
	}

	@Override
	public UserVO getUserById(String id) {
		UserVO vo = new UserVO();
		UserEntity po = userDao.findOne(id);
		if (null != po) {
			BeanUtils.copyProperties(po, vo);
		}else {
			return null;
		}
		return vo;
	}

	@Override
	public boolean saveUser(UserVO vo) {
		UserEntity entity = new UserEntity();
		BeanUtils.copyProperties(vo, entity);
		entity = userDao.save(entity);
		if (entity != null && StringUtils.isNotBlank(entity.getId())) {
			return true;
		}
		return true;
	}

}
