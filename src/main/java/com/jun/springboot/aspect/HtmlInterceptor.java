package com.jun.springboot.aspect;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.alibaba.fastjson.JSON;

/**
 * 切入点表达式的格式：execution([可见性] 返回类型 [声明类型].方法名(参数) [异常])
 * *：匹配所有字符;..：一般用于匹配多个包，多个参数 ;+：表示类及其子类 ;运算符有：&&、||、!
 */
@Aspect
@Component
public class HtmlInterceptor {
	
	private Logger logger = LoggerFactory.getLogger(HtmlInterceptor.class);

	/**
	 * 指定切入点
	 */
	@Pointcut("execution(* com.jun.springboot.controller.*.*(..))")
	public void htmlPointcut() {
	}
	
	/**
	 * 
	 * @param joinPoint
	 */
	@Before("htmlPointcut()")
	public void doBefore(JoinPoint joinPoint){
		logger.info("我是前置通知!");
		//获取目标方法的参数信息  
        Object[] obj = joinPoint.getArgs();  
        //AOP代理类的信息  
        joinPoint.getThis();  
        //代理的目标对象  
        joinPoint.getTarget();  
        //用的最多 通知的签名  
        Signature signature = joinPoint.getSignature();  
        //代理的是哪一个方法  
//        System.out.println(signature.getName());  
        //AOP代理类的名字  
//        System.out.println(signature.getDeclaringTypeName());  
        logger.info("后台逻辑：" + signature.getDeclaringTypeName() + "." + signature.getName());
        //AOP代理类的类（class）信息  
        signature.getDeclaringType();  
        //获取RequestAttributes  
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();  
        //从获取RequestAttributes中获取HttpServletRequest的信息  
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);  
        logger.info("URL:" + request.getRequestURL().toString());
        //如果要获取Session信息的话，可以这样写：  
        //HttpSession session = (HttpSession) requestAttributes.resolveReference(RequestAttributes.REFERENCE_SESSION);  
        Enumeration<String> enumeration = request.getParameterNames();  
        Map<String,String> parameterMap = new HashMap<String, String>(); 
        while (enumeration.hasMoreElements()){  
            String parameter = enumeration.nextElement();  
            parameterMap.put(parameter,request.getParameter(parameter));  
        }  
        String str = JSON.toJSONString(parameterMap);  
        if(obj.length > 0) {  
            logger.info("请求的参数信息为：" + str);
        }
	}
	
	/** 
	 * 后置返回通知 
	 * 这里需要注意的是: 
	 *      如果参数中的第一个参数为JoinPoint，则第二个参数为返回值的信息 
	 *      如果参数中的第一个参数不为JoinPoint，则第一个参数为returning中对应的参数 
	 * returning 限定了只有目标方法返回值与通知方法相应参数类型时才能执行后置返回通知，否则不执行，对于returning对应的通知方法参数为Object类型将匹配任何目标返回值 
	 * @param joinPoint 
	 * @param keys 
	 */  
	@AfterReturning(value="htmlPointcut()",returning = "keys")
	public void doAfterReturning(JoinPoint joinPoint,Object keys){
		logger.info("第一个后置返回通知的返回值：" + keys);
	}
	
	@AfterReturning(value="htmlPointcut()",returning = "keys", argNames = "keys")
	public void doAfterReturning(String keys){
		logger.info("第二个后置返回通知的返回值：" + keys);
	}
	
	/** 
	 * 后置异常通知 （有@Around环绕通知时不会执行）
	 *  定义一个名字，该名字用于匹配通知实现方法的一个参数名，当目标方法抛出异常返回后，将把目标方法抛出的异常传给通知方法； 
	 *  throwing 限定了只有目标方法抛出的异常与通知方法相应参数异常类型时才能执行后置异常通知，否则不执行， 
	 *      对于throwing对应的通知方法参数为Throwable类型将匹配任何异常。 
	 * @param joinPoint 
	 * @param exception 
	 */  
	@AfterThrowing(value="htmlPointcut()",throwing = "exception")  
	public void doAfterThrowing(JoinPoint joinPoint,Throwable exception){  
	    //目标方法名：  
		logger.info(joinPoint.getSignature().getName());  
	    if(exception instanceof NullPointerException){  
	    	logger.info("发生了空指针异常!!!!!");
	    }  
	}
	
	/** 
	 * 后置最终通知（目标方法只要执行完了就会执行后置通知方法） 
	 * @param joinPoint 
	 */  
	@After("htmlPointcut()")  
	public void doAfter(JoinPoint joinPoint){  
		logger.info("后置通知执行了!!!!");
	}
	
	/** 
	 * 环绕通知： 
	 *   环绕通知非常强大，可以决定目标方法是否执行，什么时候执行，执行时是否需要替换方法参数，执行完毕是否需要替换返回值。 
	 *   环绕通知第一个参数必须是org.aspectj.lang.ProceedingJoinPoint类型 
	 */  
	@Around("htmlPointcut()")
	public Object doAroundAdvice(ProceedingJoinPoint proceedingJoinPoint){  
		logger.info("环绕通知的目标方法名："+proceedingJoinPoint.getSignature().getName());  
	    try {  
	        Object obj = proceedingJoinPoint.proceed();  
	        logger.info("环绕通知的目标返回值："+obj.toString());  
	        return obj;  
	    } catch (Throwable throwable) {  
	        throwable.printStackTrace();  
	    }  
	    return null;  
	} 
	
	
}
