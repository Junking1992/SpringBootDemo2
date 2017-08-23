package com.jun.springboot.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.jun.springboot.service.TestServer;

@RestController
@RequestMapping("/test")
public class HwController {
	
	@Autowired
	public TestServer testServer;
	
	@RequestMapping("/aop")
	public String testAop(){
//		throw new NullPointerException();
		return "XXXXXXXXXXXXX";
	}
	
	@GetMapping("/get")
	public String testGet(String id){
		return id;
	}
	
	@GetMapping("/insert")
	public String insert(String name, int age){
		testServer.insertVo(name, age);
		return "ok";
	}
	
	@PostMapping("/work")
	public String workflow(String a, String b, String c){
		Map<String, String> map = new HashMap<String, String>();
		map.put("a", a);
		map.put("b", b);
		map.put("c", c);
		return JSON.toJSONString(map);
	}
	
}
