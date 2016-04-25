package com.mv.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mv.domain.Company;
import com.mv.domain.common.Page;
import com.mv.domain.enums.OperatorStatusEnum;
import com.mv.domain.enums.YnEnum;
import com.mv.service.CompanyService;
import com.mv.web.RemoteResult;

@Controller
@RequestMapping(value = "/company")
public class CompanyController extends BaseController {
	private static final Logger LOGGER = LoggerFactory.getLogger(CompanyController.class);
	@Resource
	private CompanyService companyService;

	/**
	 * 列表展示
	 * 
	 * @param 实体对象
	 * @param page
	 *            分页对象
	 * @return
	 */
	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
	public String list(Company company, Page<Company> page, Model view) throws Exception {
		try {
			company.setOrderField("id");
			company.setOrderFieldType("DESC");
			view.addAttribute("query", company);
			view.addAttribute("page", companyService.selectPage(company, page));
		} catch (Exception e) {
			LOGGER.error("失败:" + e.getMessage(), e);
			throw e;
		} finally {
		}
		return "company/list";
	}

	/**
	 * 添加公司
	 * 
	 * @param view
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model view) throws Exception {
		return "company/add";
	}

	/**
	 * 保存
	 */
	@RequestMapping(value = "/save", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json")
	public @ResponseBody RemoteResult save(Company company, Model view) throws Exception {
		try {
			Assert.hasText(company.getExpressName(), "名称不能为空");
			Date now = new Date();
			company.setCreated(now);
			company.setModified(now);
			company.setYn(YnEnum.Y.getKey());

			Company companyForQuery = new Company();
			companyForQuery.setExpressName(company.getExpressName());
			List<Company> wmExpressCompanies = companyService.selectEntryList(companyForQuery);
			if (CollectionUtils.isNotEmpty(wmExpressCompanies)) {
				return RemoteResult.failure("公司名称已存在，请重新输入");
			}
			companyService.insertEntryCreateId(company);
			operateService.saveOperate(OperatorStatusEnum.SUCCESS, "新增公司成功，公司id：" + company.getId(), getOperateLog());
			return RemoteResult.success(null);
		} catch (Exception e) {
			LOGGER.error("失败:" + e.getMessage(), e);
			return RemoteResult.failure(e.getMessage());
		}
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String edit(@PathVariable Long id, Model view) throws Exception {
		try {
			if (id != null && id > 0) {
				Company company = companyService.selectEntry(id);
				if (company == null) {
					return null;
				}
				view.addAttribute("company", company);
			}
		} catch (Exception e) {
			LOGGER.error("失败:" + e.getMessage(), e);
			throw e;
		} finally {
		}

		return "company/edit";
	}

	@RequestMapping(value = "/update", method = { RequestMethod.POST, RequestMethod.GET }, produces = "application/json")
	public @ResponseBody RemoteResult update(Company company, Model view) throws Exception {
		try {
			Assert.hasText(company.getExpressName(), "公司名称不能为空");
			Date now = new Date();
			company.setModified(now);
			Company oldCompany = companyService.selectEntry(company.getId());

			// 检查名字重复性
			Company companyForQuery = new Company();
			companyForQuery.setExpressName(company.getExpressName());
			List<Company> wmExpressCompanies = companyService.selectEntryList(companyForQuery);
			if (CollectionUtils.isNotEmpty(wmExpressCompanies)) {
				if (oldCompany.getId().longValue() != wmExpressCompanies.get(0).getId().longValue()) {
					return RemoteResult.failure("公司已存在，请重新输入");
				}
			}
			companyService.updateByKey(company);
			operateService.saveOperate(OperatorStatusEnum.SUCCESS, "修改公司成功，id：" + company.getId(), getOperateLog());
			return RemoteResult.success(null);
		} catch (Exception e) {
			LOGGER.error("失败:" + e.getMessage(), e);
			return RemoteResult.failure(e.getMessage());
		}
	}
}