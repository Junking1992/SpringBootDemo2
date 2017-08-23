package com.jun.springboot.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;

/**
 * ApplicationStartedEvent：spring boot启动开始时执行的事件
 * 
 * 在该事件中可以获取到SpringApplication对象，可做一些执行前的设置.
 * 
 * @author lx
 *
 */
public class MyApplicationStartedEventListener implements ApplicationListener<ApplicationStartedEvent> {

	private Logger logger = LoggerFactory.getLogger(MyApplicationStartedEventListener.class);

	@Override
	public void onApplicationEvent(ApplicationStartedEvent event) {
		SpringApplication app = event.getSpringApplication();
		logger.info("===MyApplicationStartedEventListener===");
	}

}
