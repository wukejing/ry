package com.mv.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mv.domain.OperateLog;
import com.mv.domain.common.Page;

@Controller
@RequestMapping(value = "/operate")
public class OperateLogController extends BaseController{
	 private static final Logger LOGGER = LoggerFactory.getLogger(OperateLogController.class);

    
     /**
      * 列表展示
      * 
      * @param brand
      *            实体对象
      * @param page
      *            分页对象
      * @return
      */
     @RequestMapping(method = { RequestMethod.GET, RequestMethod.POST })
     public String list(OperateLog operateLog,  Page<OperateLog> page,  Model view) throws Exception {
        try {
        	operateLog.setOrderField("created");
        	operateLog.setOrderFieldType("desc");
            view.addAttribute("page", operateService.selectPage(operateLog, page));
            view.addAttribute("operateLog", operateLog);
        } catch (Exception e) {
            LOGGER.error("失败:" + e.getMessage(), e);
            throw e;
        } finally {
        }
        return "operate/list";
     }
}   
