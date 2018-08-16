package com.lrs.admin.service;

import java.util.List;
import java.util.Map;

import com.lrs.admin.util.ParameterMap;

public interface IPublicService {
	
	public List<ParameterMap> getDraftList(ParameterMap pm);
	public Map<String, Object> getDraftDetail(ParameterMap pm);
	public Map<String, Object> editDraft(ParameterMap pm);
	
	
	public List<ParameterMap> getSysteMsgList(ParameterMap pm);
	public Map<String, Object> delSysteMsg(ParameterMap pm);
	public Map<String, Object> addSysteMsg(ParameterMap pm);
	public Map<String, Object> editSysteMsg(ParameterMap pm);
	
	public List<ParameterMap> getFeedbackList(ParameterMap pm);
	public Map<String, Object> editFeedBack(ParameterMap pm);
	
}
