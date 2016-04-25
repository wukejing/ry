package com.mv.nb.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 资源码注解
 * @author perry liu
 * 2015年1月28日
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface WmPermission {
	
	public String code() default "";
	
	public String testCode() default "";
	
}
