package com.lrs.admin.es.dao;

import java.util.Map;

import com.lrs.admin.plugin.Page;
import com.lrs.admin.util.ParameterMap;


public interface Dao {
	public String save(ParameterMap pm,String index,String type);
	public int update(ParameterMap pm,String index,String type);
	public int deltele(String id,String index,String type);
	public Map<String, Object> find(String id,String index,String type);
	public Object query(ParameterMap pm,String index,String type,Page page);
	public Object multiMatchQuery(ParameterMap pm,String index,String type,Page page);
	
}
