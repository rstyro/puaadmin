package com.lrs.admin.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lrs.admin.controller.base.BaseController;
import com.lrs.admin.entity.ReturnModel;
import com.lrs.admin.service.IEsotericaService;
import com.lrs.admin.util.Jurisdiction;
import com.lrs.admin.util.ParameterMap;

@Controller
@RequestMapping("/esoterica")
public class EsotericaController extends BaseController{
	
	private String qxurl = "esoterica/list";
	
	@Autowired
	private IEsotericaService esotericaService;
	
	@GetMapping("/list")
	public Object list(Model model){
		if(!Jurisdiction.buttonJurisdiction(qxurl,"query", this.getSession())){return ReturnModel.getNotAuthModel();}
		ParameterMap pm = this.getParameterMap();
		Map<String,Object> map = esotericaService.getSpeechraftList(pm);
		model.addAttribute("lists", map.get("data"));
		model.addAttribute("page", map.get("page"));
		return "page/esoterica/list";
	}
	
	@GetMapping("/list/{page_no}")
	public Object listPage(Model model,@PathVariable("page_no") String pageNo){
		if(!Jurisdiction.buttonJurisdiction(qxurl,"query", this.getSession())){return ReturnModel.getNotAuthModel();}
		ParameterMap pm = this.getParameterMap();
		pm.put("page_no", pageNo);
		System.out.println("pageNo="+pageNo);
		Map<String,Object> map = esotericaService.getSpeechraftList(pm);
		model.addAttribute("lists", map.get("data"));
		model.addAttribute("page", map.get("page"));
		model.addAttribute("type", pm.getString("type"));
		model.addAttribute("kw", pm.getString("kw"));
		return "page/esoterica/list";
	}
	
	/**
	 * 保存
	 * @return
	 */
	@PostMapping("/save")
	@ResponseBody
	public Object save(){
		if(!Jurisdiction.buttonJurisdiction(qxurl,"add", this.getSession())){return ReturnModel.getNotAuthModel();}
		return esotericaService.save(getParameterMap());
	}
	/**
	 * 更新
	 * @return
	 */
	@PostMapping("/update")
	@ResponseBody
	public Object update(){
		if(!Jurisdiction.buttonJurisdiction(qxurl,"edit", this.getSession())){return ReturnModel.getNotAuthModel();}
		return esotericaService.update(getParameterMap());
	}
	/**
	 * 删除
	 * @return
	 */
	@PostMapping("/del")
	@ResponseBody
	public Object del(){
		if(!Jurisdiction.buttonJurisdiction(qxurl,"del", this.getSession())){return ReturnModel.getNotAuthModel();}
		return esotericaService.del(getParameterMap());
	}
}
