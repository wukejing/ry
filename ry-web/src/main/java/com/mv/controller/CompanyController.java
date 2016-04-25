package com.mv.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mv.domain.Company;
import com.mv.service.CompanyService;

@Controller
@RequestMapping("logged/company")
public class CompanyController {
	
	@Autowired
	CompanyService companyService;
	
	@RequestMapping("list")
	public String list(Model model) {
		Company company = null;
		List<Company> companies = companyService.selectEntryList(company);
		model.addAttribute("companies", companies);
		return "company_list";
	}

	@RequestMapping("hello")
	@ResponseBody
	public List<Company> hello(Model model) {
		Company company = null;
		List<Company> companies = companyService.selectEntryList(company);
		return companies;
	}
}
