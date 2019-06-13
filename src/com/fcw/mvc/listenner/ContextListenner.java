package com.fcw.mvc.listenner;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import com.fcw.mvc.mapping.Mapping;
import com.fcw.mvc.annotation.Controller;
import com.fcw.mvc.annotation.RequestMapping;

/**
 * Application Lifecycle Listener implementation class ContextListenner
 *
 */
@WebListener
public class ContextListenner implements ServletContextListener {

	/**
	 * Default constructor.
	 */
	public ContextListenner() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see ServletContextListener#contextDestroyed(ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
	}

	/**
	 * @see ServletContextListener#contextInitialized(ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent event) {
		// 扫描某个包中所有的类
		// 读取mvc.xml 获取用户存放在业务控制器的包名
		SAXReader saxReader = new SAXReader();
		// 通过类加载器来获取类加载器Java根目录
		InputStream is = this.getClass().getClassLoader().getResourceAsStream("mvc.xml");
		try {
			Document document = saxReader.read(is);
			Element root = document.getRootElement();
			// 获取包名
			String text = root.element("package").getText();
			String packageName = text.replace(".", "/");
			// 去掉最前面的斜杠
			String path = this.getClass().getClassLoader().getResource("/").getPath().substring(1);
			// 获取该文件夹下所有的.class文件
			File file = new File(path + packageName);
			File[] listFiles = file.listFiles();
			Map<String,Mapping> map=new HashMap<String,Mapping>();
			// 判断目录是否为空
			if (listFiles != null) {
				for (File f : listFiles) {
					if (f.getName().endsWith(".class")) {
						// 获取类全路径名
						String claeePrefix = f.getName().substring(0, f.getName().lastIndexOf("."));
						System.out.println(text + "." + claeePrefix);
						// 获取类信息
						Class c = Class.forName(text + "." + claeePrefix);
						// 判断是否有Controller注解
						if (c.isAnnotationPresent(Controller.class)) {
							Method[] methods = c.getDeclaredMethods();
							for (Method m : methods) {
								// 判断方法是否有RequestMapping注解
								if (m.isAnnotationPresent(RequestMapping.class)) {
									//创建mapping 并赋值
									Mapping mapping = new Mapping();									
									mapping.setClassInfo(c);
									mapping.setName(m.getAnnotation(RequestMapping.class).value());
									mapping.setMethod(m);
								//添加在map集合中
									map.put(m.getName(), mapping);
								}
							}
						}
					}
				}
			}
			//将map集合添加到ServletContext中
			event.getServletContext().setAttribute("map", map);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
