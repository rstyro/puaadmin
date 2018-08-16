package com.lrs.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lrs.admin.controller.base.BaseController;
import com.lrs.admin.entity.Const;
import com.lrs.admin.entity.ReturnModel;
import com.lrs.admin.entity.User;
import com.lrs.admin.service.IAppUserService;
import com.lrs.admin.util.Jurisdiction;


@Controller
@RequestMapping("/appuser")
public class AppuserController extends BaseController{
	
	private String qxurl = "appuser/list";
	
	@Autowired
	private IAppUserService appUserService;
	
	@RequestMapping("/list")
	public Object list(Model model){
		if(!Jurisdiction.buttonJurisdiction(qxurl,"query", this.getSession())){return ReturnModel.getNotAuthModel();}
		model.addAttribute("users", appUserService.getUserList(this.getParameterMap()));
		model.addAttribute("meid", ((User)this.getSession().getAttribute(Const.SESSION_USER)).getUserId());
		return "page/appuser/list";
	}
}
