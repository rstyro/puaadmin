package com.lrs.admin.dao;

import java.util.List;

import com.lrs.admin.util.ParameterMap;

public interface PublicDao {
	/**
	 * 获取稿子列表
	 * @return
	 */
	public List<ParameterMap> getDraftList();
	public ParameterMap getDraftDetail(ParameterMap pm);
	public int editDraft(ParameterMap pm);
	
	
	/**
	 * 保存系统消息
	 * @param pm
	 * @return
	 */
	public int saveSystemMsg(ParameterMap pm);
	/**
	 * 修改系统消息
	 * @param pm
	 * @return
	 */
	public int editSystemMsg(ParameterMap pm);
	/**
	 * 删除系统消息
	 * @param pm
	 * @return
	 */
	public int delSystemMsg(String[] ids);
	/**
	 * 获取系统消息列表
	 * @return
	 */
	public List<ParameterMap> getSystemMsglist();
	
	/**
	 * 获取反馈列表
	 * @return
	 */
	public List<ParameterMap> getfeedBacklist();
	/**
	 * 更新反馈状态
	 * @return
	 */
	public int editFeedBack(ParameterMap pm);
}
