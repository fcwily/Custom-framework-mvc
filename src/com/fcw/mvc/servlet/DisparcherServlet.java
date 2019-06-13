package com.fcw.mvc.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fcw.mvc.mapping.Mapping;

public class DisparcherServlet extends HttpServlet {

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 获取请求地址
		String uri = req.getRequestURI();
		uri = uri.substring(uri.lastIndexOf("/") + 1);
		// 拿到请求方法名
		String requestName = uri.substring(0, uri.lastIndexOf("."));
		// 从application中取出键值对
		Map<String, Mapping> map = (Map<String, Mapping>) req.getServletContext().getAttribute("map");
		//根据方法名拿到对应的 Mapping
		Mapping mapping = map.get(requestName);
		//通过映射信息中的classinfo实例化业务控制器
		Class c=mapping.getClassInfo();
		
		try {
			//实例化mapping
			Object object=c.newInstance();
			//获取方法
			Method method=mapping.getMethod();
			//执行方法
			String result=(String) method.invoke(object, null);
			//判断业务控制器返回的字符串是否包含 来决定转发还是重定向
			if(result.indexOf(":")==-1) {
				req.getRequestDispatcher(result).forward(req, resp);
			}else {
				if("redirect".equals(result.split(":")[0])) {
					resp.sendRedirect(result.split(":")[1]);
				}
			}
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
