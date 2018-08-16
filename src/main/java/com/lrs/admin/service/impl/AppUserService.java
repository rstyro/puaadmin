package com.lrs.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lrs.admin.dao.AppUserDao;
import com.lrs.admin.service.IAppUserService;
import com.lrs.admin.util.ParameterMap;

@Service
public class AppUserService implements IAppUserService{

	
	@Autowired
	private AppUserDao appUserDao;
	
	@Override
	public List<ParameterMap> getUserList(ParameterMap pm) {
		List<ParameterMap> users = appUserDao.getAppUserList(pm);
		return users;
	}

}
