package com.lrs.admin.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lrs.admin.dao.PublicDao;
import com.lrs.admin.entity.ReturnModel;
import com.lrs.admin.es.dao.Dao;
import com.lrs.admin.service.IPublicService;
import com.lrs.admin.util.DateUtil;
import com.lrs.admin.util.ParameterMap;
import com.lrs.admin.util.Tools;

@Service
public class PublicService implements IPublicService{

	@Autowired
	private PublicDao publicDao;
	
	@Autowired
	private Dao dao;
	
	private Logger log = Logger.getLogger(this.getClass());
	
	@Override
	public List<ParameterMap> getDraftList(ParameterMap pm) {
		List<ParameterMap> draftList = publicDao.getDraftList();
		return draftList;
	}
	
	@Override
	public Map<String, Object> getDraftDetail(ParameterMap pm) {
		try {
			String id = pm.getString("id");
			if(Tools.isEmpty(id)){
				ReturnModel.getModel("参数不全","success",null);
			}
			ParameterMap draft = publicDao.getDraftDetail(pm);
			return ReturnModel.getModel("ok", "success", draft);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ReturnModel.getModel(e.getMessage(), "failed",null);
		}
	}
	
	@Override
	public Map<String, Object> editDraft(ParameterMap pm) {
		try {
			String status = pm.getString("status");
			if(Tools.notEmpty(status)){//审核稿子的结果，发给用户一个系统通知 
				String userId = pm.getString("user_id");
				ParameterMap parame = new ParameterMap();
				parame.put("push_id", userId);
				parame.put("create_time", DateUtil.getTime());
				parame.put("title", "投稿结果");
				if("1".equals(status)){
					parame.put("msg_content", "非常感谢您的投稿，您的稿子已审核通过了");
					publicDao.saveSystemMsg(parame);
					
					ParameterMap draft = publicDao.getDraftDetail(pm);
					if(draft != null && !"".equals(draft.getString("id"))){
						//把稿子 放入elasticsearch
						ParameterMap esoterica = new ParameterMap();
						esoterica.put("speechcraft_id", Tools.getUUID());
						esoterica.put("create_time", DateUtil.getTime());
						esoterica.put("type", draft.getString("type"));
						esoterica.put("praise_num", 0);
						esoterica.put("content", draft.getString("content"));
						esoterica.put("user_id", draft.getString("user_id"));
						dao.save(esoterica, "pua", "speechcraft");
					}
					
				}else if("2".equals(status)){
					parame.put("msg_content",pm.getString("msg_content"));
					publicDao.saveSystemMsg(parame);
				}
			}
			publicDao.editDraft(pm);
			return ReturnModel.getModel("ok", "success", null);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ReturnModel.getModel(e.getMessage(), "failed",null);
		}
	}
	
	@Override
	public List<ParameterMap> getSysteMsgList(ParameterMap pm) {
		List<ParameterMap> systemMsgList = publicDao.getSystemMsglist();
		return systemMsgList;
	}

	@Override
	public Map<String, Object> delSysteMsg(ParameterMap pm) {
		try {
			String msgId = pm.getString("msg_id");
			if(Tools.isEmpty(msgId)){
				ReturnModel.getModel("参数不全", "failed", null);
			}
			String[] ids = msgId.split(",");
			publicDao.delSystemMsg(ids);
			return ReturnModel.getModel("ok", "success", null);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ReturnModel.getModel(e.getMessage(), "failed",null);
		}
	}

	@Override
	public Map<String, Object> addSysteMsg(ParameterMap pm) {
		try {
			String crateTime = pm.getString("create_time");
			if(Tools.isEmpty(crateTime)){
				pm.put("create_time", DateUtil.getTime());
			}
			publicDao.saveSystemMsg(pm);
			return ReturnModel.getModel("ok", "success", null);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ReturnModel.getModel(e.getMessage(), "failed",null);
		}
	}

	@Override
	public Map<String, Object> editSysteMsg(ParameterMap pm) {
		try {
			String msgId = pm.getString("msg_id");
			if(Tools.isEmpty(msgId)){
				ReturnModel.getModel("参数不全", "failed", null);
			}
			publicDao.editSystemMsg(pm);
			return ReturnModel.getModel("ok", "success", null);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ReturnModel.getModel(e.getMessage(), "failed",null);
		}
	}
	
	@Override
	public List<ParameterMap> getFeedbackList(ParameterMap pm) {
		List<ParameterMap> feedback = publicDao.getfeedBacklist();
		return feedback;
	}
	
	@Override
	public Map<String, Object> editFeedBack(ParameterMap pm) {
		try {
			String id = pm.getString("id");
			String status = pm.getString("status");
			if(Tools.isEmpty(id) || Tools.isEmpty(status)){
				ReturnModel.getModel("参数不全", "failed", null);
			}
			publicDao.editFeedBack(pm);
			String replyContent = pm.getString("reply_content");
			if(Tools.notEmpty(replyContent)){
				ParameterMap parame = new ParameterMap();
				parame.put("title", "反馈答复");
				parame.put("push_id", pm.getString("user_id"));
				parame.put("msg_content", replyContent);
				parame.put("create_time", DateUtil.getTime());
				publicDao.saveSystemMsg(parame);
			}
			return ReturnModel.getModel("ok", "success", null);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ReturnModel.getModel(e.getMessage(), "failed",null);
		}
	}
	
	
}
