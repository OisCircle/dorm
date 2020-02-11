package com.qcq.dorm.bean;
import org.springframework.util.StringUtils;

import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

import java.util.Locale;

//为了支持国际化，在http请求里面设置区域信息时需要实现以下方法

public class MyLocaleResolver implements LocaleResolver {
	@Override
	public Locale resolveLocale(HttpServletRequest request) {
		String l = request.getParameter("l");
		Locale locale = Locale.getDefault();
		if(!StringUtils.isEmpty(l)){
			String[] split = l.split("_");
			locale = new Locale(split[0],split[1]);
		}
		return locale;
	}
	@Override
	public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
	}
}
