package cases;

import java.util.Map;

import org.testng.annotations.Test;

import base.Actions;
import base.WebSingle;
import base.WebSuite;

/**
 * 本类是测试登录的用例，使用数据驱动
 * 
 * @author Administrator
 *
 */
// public class Case5 extends WebSuite {// 批量正式测试的模板
public class Case5 extends WebSingle {// 单个用例调试运行的模板

	@Test(dataProvider = "getData") // 这个数据驱动是指TestBase类中的getData方法（因为在那个方法上面有@DataProvider标志）
	public void case5(Map<String, String> data) {
		Actions action = new Actions(driver);

		locator.linkTo(data.get("主页地址"));
		if (locator.elementExistNoWait("首页", "退出登录")) {//
			locator.element("首页", "退出登录").click();
		}

		// locator.linkTo(data.get("登录地址"));
		// locator.element("登录页", "用户名").sendKeys(data.get("用户名"));
		// locator.element("登录页", "密码").sendKeys(data.get("密码"));
		// locator.element("登录页", "立即登录").click();

		action.loginAction(data.get("用户名"), data.get("密码"));// 把上面4行封装成了一行
		locator.element("首页", "退出登录").click();//
		locator.wait(2);
		cp.result("登录功能正常");
	}
}
