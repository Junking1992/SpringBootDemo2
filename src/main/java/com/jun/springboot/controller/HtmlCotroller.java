package com.jun.springboot.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HtmlCotroller {
	private Logger logger = LoggerFactory.getLogger(HtmlCotroller.class);
	
	@GetMapping("/html/{path}/{page}")
    public String html(@PathVariable("path") String path, @PathVariable("page") String page){
		logger.info("访问了:/html/" + path + "/" + page + ".html");
		return path + "/" + page;
    }
	
	@GetMapping("/")
    public String html(){
		logger.info("访问了:/html/main/index.html");
        return "main/index";
    }

}
