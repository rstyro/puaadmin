package com.lrs.admin.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.lrs.admin.entity.ReturnModel;
import com.lrs.admin.es.dao.Dao;
import com.lrs.admin.plugin.Page;
import com.lrs.admin.service.IEsotericaService;
import com.lrs.admin.util.DateUtil;
import com.lrs.admin.util.ParameterMap;
import com.lrs.admin.util.Tools;



@Service
public class EsotericaService implements IEsotericaService{


	private Logger log = Logger.getLogger(getClass());
	
	//索引名称（数据库名）
	private String index = "pua";
	
	//类型名称（表名）
	private String type = "speechcraft"; 
	
	@Autowired
	private RedisTemplate<String, String> redis;
	
	@Autowired
	private Dao dao;
	
	
	@Override
	public Map<String, Object> save(ParameterMap pm) {
		try {
			pm.put("speechcraft_id", Tools.getUUID());
			String createTime = pm.getString("create_time");
			if(Tools.isEmpty(createTime)){
				pm.put("create_time", DateUtil.getTime());
			}
			String userId = pm.getString("user_id");
			if(Tools.isEmpty(userId)){
				pm.put("user_id","1");
			}
			String praise_num = pm.getString("praise_num");
			pm.put("praise_num", Integer.parseInt(praise_num));
			pm.remove("_id");
			pm.remove("t_");
			pm.remove("ip");
			dao.save(pm, index, type);
		} catch (Exception e) {
			log.error("错误", e);
			e.printStackTrace();
			return ReturnModel.getModel("服务好像出了点故障,麻烦联系管理员", "failed", null);
		}
		return ReturnModel.getModel("ok", "success", null);
	}

	@Override
	public Map<String, Object> find(ParameterMap pm) {
		try {
			String id = pm.getString("_id");
			Map<String,Object> map = null;
			String tagType = pm.getString("type");
			if(tagType != null && !"".equals(tagType)){
				map = dao.find(id, index, tagType);
			}else{
				map = dao.find(id, index, type);
			}
			return ReturnModel.getModel("ok", "success", map);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
			return ReturnModel.getModel(e.getMessage(), "failed", null);
					
		}
	}

	@Override
	public Map<String, Object> del(ParameterMap pm) {
		try {
			String id = pm.getString("_id");
			if(Tools.isEmpty(id)){
				return ReturnModel.getModel("参数不全", "failed", null);
			}
			int issuccess = dao.deltele(id, index, type);
			return ReturnModel.getModel("ok", "success", issuccess);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
			return ReturnModel.getModel(e.getMessage(), "failed", null);
		}
	}

	@Override
	public Map<String, Object> update(ParameterMap pm) {
		try {
			pm.remove("ip");
			pm.remove("t_");
			pm.remove("SessionID");
			String praise_num = pm.getString("praise_num");
			pm.put("praise_num", Integer.parseInt(praise_num));
			int issuccess = dao.update(pm, index, type);
			return ReturnModel.getModel("ok", "success", issuccess);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			return ReturnModel.getModel(e.getMessage(), "failed", null);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getSpeechraftList(ParameterMap pm) {
		Page page = new Page();
		String pageNo =pm.getString("page_no");
		String pageSize =pm.getString("page_size");
		try {
			if(Tools.notEmpty(pageNo)){
				page.setCurrentPage(Integer.parseInt(pageNo));
			}
			if(Tools.notEmpty(pageSize)){
				page.setShowCount(Integer.parseInt(pageSize));
			}else{
				page.setShowCount(10);
			}
			String keyword = pm.getString("kw");
			String speechraftType = pm.getString("type");
			keyword = QueryParser.escape(keyword);
			ParameterMap mm = new ParameterMap();
			if(Tools.notEmpty(keyword)){
				mm.put("must_key", "content");
				mm.put("content_value", keyword);
			}else{
				//默认列表按时间排序
				mm.put("sort", "time");
			}
			if(Tools.notEmpty(speechraftType)){
				mm.put("must_key2", "type");
				mm.put("type_value", speechraftType);
			}
			List<Map<String,Object>> list = (List<Map<String, Object>>) dao.query(mm, index, type, page);
			if(list == null){
				list = new ArrayList<>();
			}
			for(Map<String,Object> speechraft:list){
				try {
					String authId = (String) speechraft.get("user_id");
					String nickName = redis.opsForValue().get(authId);
					if(nickName != null && !"".equals(nickName)){
						speechraft.put("auther", nickName);
					}else{
						speechraft.put("auther", "帅大叔");
					}
				} catch (Exception e) {
					log.error(e.getMessage(), e);
					speechraft.put("auther", "帅大叔");
				}
			}
			//当在elasticsearch 查询的时候，page 的页数减一，所以这里重置回来
			if(Tools.notEmpty(pageNo)){
				page.setCurrentPage(Integer.parseInt(pageNo));
			}
			
			return ReturnModel.getModel("ok", "success", list,page);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
			return ReturnModel.getModel(e.getMessage(),"failed", null);
		}
	}

}
