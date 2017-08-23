package com.jun.springboot.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.context.ApplicationListener;

/**
 * ApplicationFailedEvent:spring boot启动异常时执行事件
 * 
 * 在异常发生时，最好是添加虚拟机对应的钩子进行资源的回收与释放，能友善的处理异常信息。
 * 
 * @author lx
 *
 */
public class MyApplicationFailedEventListener implements ApplicationListener<ApplicationFailedEvent> {

	private Logger logger = LoggerFactory.getLogger(MyApplicationFailedEventListener.class);

	@Override
	public void onApplicationEvent(ApplicationFailedEvent event) {
		logger.info("===MyApplicationFailedEventListener===");
		Throwable throwable = event.getException();
		handleThrowable(throwable);
	}

	/**
	 * 处理异常
	 */
	private void handleThrowable(Throwable throwable) {
		logger.info("Junking处理异常:" + throwable.getMessage());
	}

}
