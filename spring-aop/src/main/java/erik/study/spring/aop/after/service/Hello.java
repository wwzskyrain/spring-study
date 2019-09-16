package erik.study.spring.aop.after.service;

/**
 * Description:
 * <br/>网站: <a href="http://www.crazyit.org">疯狂Java联盟</a>
 * <br/>Copyright (C), 2001-2016, Yeeku.H.Lee
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:
 * <br/>Date:
 * @author Yeeku.H.Lee kongyeeku@163.com
 * @version 1.0
 */
public interface Hello
{
	// 定义一个简单方法，模拟应用中的业务逻辑方法
	void foo();
	// 定义一个addUser()方法，模拟应用中的添加用户的方法
	int addUser(String name , String pass);
}
