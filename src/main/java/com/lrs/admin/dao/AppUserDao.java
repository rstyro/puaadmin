package com.lrs.admin.dao;

import java.util.List;

import com.lrs.admin.util.ParameterMap;

public interface AppUserDao {
	public List<ParameterMap> getAppUserList(ParameterMap pm);
}
