package com.lrs.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lrs.admin.controller.base.BaseController;
import com.lrs.admin.entity.ReturnModel;
import com.lrs.admin.service.IPublicService;
import com.lrs.admin.util.Jurisdiction;

@Controller
@RequestMapping("/feedback")
public class FeedbackController extends BaseController{
	private String qxurl = "feedback/list";
	
	@Autowired
	private IPublicService publicService;
	
	@RequestMapping("/list")
	public Object list(Model model){
		if(!Jurisdiction.buttonJurisdiction(qxurl,"query", this.getSession())){return ReturnModel.getNotAuthModel();}
		model.addAttribute("feedbacks", publicService.getFeedbackList(getParameterMap()));
		return "page/feedback/list";
	}
	
	@PostMapping("/update")
	@ResponseBody
	public Object update(Model model){
		if(!Jurisdiction.buttonJurisdiction(qxurl,"edit", this.getSession())){return ReturnModel.getNotAuthModel();}
		return publicService.editFeedBack(getParameterMap());
	}
}
