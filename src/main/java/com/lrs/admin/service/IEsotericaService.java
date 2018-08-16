package com.lrs.admin.service;

import java.util.Map;

import com.lrs.admin.util.ParameterMap;


public interface IEsotericaService {
	public Map<String,Object> save(ParameterMap pm);
	public Map<String,Object> find(ParameterMap pm);
	public Map<String,Object> del(ParameterMap pm);
	public Map<String,Object> update(ParameterMap pm);
	public Map<String,Object> getSpeechraftList(ParameterMap pm);
}
