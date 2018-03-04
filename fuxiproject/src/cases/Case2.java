package cases;

import java.util.Map;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import base.WebSingle;
import base.WebSuite;

/**
 * 本case是在ecshop系统中提交留言的功能
 * 
 * @author Administrator
 *
 */
//public class Case2 extends WebSuite {// 批量正式测试的模板
public class Case2 extends WebSingle {// 单个用例调试运行的模板
	
	// 1.方式一，使用driver.和api.，属于初级封装
//	@Test
	public void case2() {
		driver.get("http://www.huicewang.com/ecshop/");
		
		driver.findElement(By.linkText("留言板")).click();// 点击首页上的留言板按钮
		driver.findElement(By.name("user_email")).sendKeys("123@163.com");// 填邮箱
		driver.findElement(By.xpath("//input[@name='msg_type' and @value='1']")).click();// 点[投诉]单选按钮
		driver.findElement(By.name("msg_title")).sendKeys("我要投诉");// 填标题
		driver.findElement(By.name("msg_content")).sendKeys("上午上课桌面老是断，请速解决！");// 填内容
		driver.findElement(By.className("bnt_blue_1")).click();// 点提交按钮
		
		String promtText = driver.findElement(By.xpath("//div[@class='boxCenterList RelaArticle']//p")).getText();
		// 上一行代码中的xpath是通过用LR录脚本，然后从响应信息里找到的，
		// 因为提示"您的留言已成功发表"这个页面总是一闪而过，我从页面找不到这个xpath，所以使用了这个曲折的办法
		cp.contains(promtText, "您的留言已成功发表", "发表留言失败！");
		cp.result("发表留言功能正常！");
	}
	
	// 2.方式二，使用locator.且使用数据驱动（xml文件），属于高级封装
	@Test(dataProvider = "getData")// 这个数据驱动是指TestBase类中的getData方法（因为在那个方法上面有@DataProvider标志）
	public void case02(Map<String,String> data) {
		locator.linkTo(data.get("主页地址"));
		
		locator.element("首页", "留言板").click();// 点"留言板"标签
		locator.element("留言板页面", "电子邮件地址").sendKeys(data.get("邮箱"));// 填邮箱
		locator.element("留言板页面", "留言类型-投诉").click();// 点“投诉”单选按钮
		locator.element("留言板页面", "主题").sendKeys(data.get("标题"));// 填标题
		locator.element("留言板页面", "留言内容").sendKeys(data.get("留言内容"));// 填内容
		locator.element("留言板页面", "我要留言").click();// 点提交按钮
		
		String promtText = locator.elementText("留言板页面", "成功提示");
		cp.contains(promtText, "您的留言已成功发表", "发表留言失败");
		cp.result("发表留言功能正常");
		locator.wait(31);// 写这个长的wait是因为，系统要求发表留言的间隔要大于30秒
	}
}
