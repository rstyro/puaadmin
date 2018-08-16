package com.lrs.admin.entity;

import java.util.HashMap;

import com.lrs.admin.util.Tools;

public class ReturnModel {

	private static HashMap<String, Object> model = null;

	private ReturnModel() {
	}

	public static HashMap<String, Object> getModel(String msg, String status, Object data) {
		model = new HashMap<>();
		if (Tools.notEmpty(msg)) {
			model.put("msg", msg);
		}
		if (Tools.notEmpty(status)) {
			model.put("status", status);
		}
		if (data != null) {
			model.put("data", data);
		} else {
			model.put("data", null);
		}
		return model;
	}

	public static HashMap<String, Object> getModel(String msg, String status, Object data, Object page) {
		model = new HashMap<>();
		if (Tools.notEmpty(msg)) {
			model.put("msg", msg);
		}
		if (Tools.notEmpty(status)) {
			model.put("status", status);
		}
		if (data != null) {
			model.put("data", data);
		} else {
			model.put("data", null);
		}
		if (page != null) {
			model.put("page", page);
		} else {
			model.put("page", null);
		}
		return model;
	}

	public static HashMap<String, Object> getErrorModel() {
		model = new HashMap<>();
		model.put("status", "failed");
		model.put("msg", "你请求的是一个冒牌接口");
		return model;
	}

	public static HashMap<String, Object> getNotAuthModel() {
		model = new HashMap<>();
		model.put("status", "notauth");
		model.put("msg", "你权限不足");
		return model;
	}
}
