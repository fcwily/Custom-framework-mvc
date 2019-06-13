package com.fcw.mvc.mapping;

import java.lang.reflect.Method;

public class Mapping {
	private String name;
	private Method method;
	private Class classInfo;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Method getMethod() {
		return method;
	}
	public void setMethod(Method method) {
		this.method = method;
	}
	public Class getClassInfo() {
		return classInfo;
	}
	public void setClassInfo(Class classInfo) {
		this.classInfo = classInfo;
	}

}
