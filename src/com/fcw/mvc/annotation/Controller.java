package com.fcw.mvc.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
/**
 * 
 *标记一个类作为控制器
 * @author fcw
 *
 */
@Retention(RUNTIME)
@Target(TYPE)
public @interface Controller {

}
