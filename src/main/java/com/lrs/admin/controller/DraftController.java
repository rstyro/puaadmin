package com.lrs.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lrs.admin.controller.base.BaseController;
import com.lrs.admin.entity.ReturnModel;
import com.lrs.admin.service.IPublicService;
import com.lrs.admin.util.Jurisdiction;


@Controller
@RequestMapping("/draft")
public class DraftController extends BaseController{
	
	private String qxurl = "draft/list";
	
	@Autowired
	private IPublicService publicService;
	
	@GetMapping("/list")
	public Object list(Model model){
		if(!Jurisdiction.buttonJurisdiction(qxurl,"query", this.getSession())){return ReturnModel.getNotAuthModel();}
		model.addAttribute("drafts", publicService.getDraftList(getParameterMap()));
		return "page/draft/list";
	}
	
	@GetMapping("/detail")
	@ResponseBody
	public Object detail(Model model){
		if(!Jurisdiction.buttonJurisdiction(qxurl,"query", this.getSession())){return ReturnModel.getNotAuthModel();}
		return publicService.getDraftDetail(getParameterMap());
	}
	
	@PostMapping("/update")
	@ResponseBody
	public Object update(Model model){
		if(!Jurisdiction.buttonJurisdiction(qxurl,"edit", this.getSession())){return ReturnModel.getNotAuthModel();}
		return publicService.editDraft(getParameterMap());
	}
}
