package cases;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import base.Actions;
import base.WebSingle;
import base.WebSuite;

/**
 * 本case是在ecshop系统中下订单并且打印订单信息的功能
 * @author Administrator
 *
 */
//public class Case3 extends WebSuite {// 批量正式测试的模板
public class Case3 extends WebSingle {// 单个用例调试运行的模板
	
	// 1.方式一，使用driver.和api.，属于初级封装
//	@Test
	public void order() {
		String baseUrl = "http://www.huicewang.com/ecshop/";
		String orderID = null;// 声明变量，订单号
		
		// 1.登录
		driver.get(baseUrl + "user.php");// 进入登录页面
		driver.findElement(By.name("username")).sendKeys("bulanni");
		driver.findElement(By.name("password")).sendKeys("123456");
		driver.findElement(By.name("submit")).click();
		
		// 2.搜索商品
		api.selectByValue(By.id("category"), "3");// 搜索下拉框内选择【GSM手机】
		driver.findElement(By.id("keyword")).sendKeys("诺基亚");// 搜索框内输入【诺基亚】
		driver.findElement(By.name("imageField")).click();// 点击搜索按钮

		// 3.下订单并打印订单信息
		List<WebElement> goodsList = driver.findElements(By.xpath("//div[@class='goodsItem']"));// 进入商品列表页，找到所有搜索出的商品并放进list中
		if (goodsList.size() > 0) {
			WebElement good1 = goodsList.get(0);// 商品列表中的第一个商品
			good1.findElement(By.linkText("购买")).click();// 点第一个商品的[购买]按钮
			api.wait(1);
			driver.findElement(By.xpath("//div[@id='speDiv']/center/a[contains(text(),'购买')]")).click();// 点击弹窗的的[购买]按钮
			api.wait(1);
			driver.findElement(By.xpath("//a[@href='flow.php?step=checkout']")).click();// 点击“结算中心”按钮
			// 要想使用脚本自动化下单，必须之前下单成功过，因为有个填收货地址的页面必须之前下过单系统才会记住，以后下单时就不用再填了
			driver.findElements(By.name("shipping")).get(0).click();// 配送方式选“申通快递”
			driver.findElements(By.name("payment")).get(1).click();// 支付方式选“银行汇款/转帐”
			driver.findElement(By.xpath("//input[@type='image']")).click();// 点击“提交订单”按钮
			orderID = driver.findElement(By.xpath("//div[@class='flowBox']/h6/font")).getText().trim();// trim()方法用来去除字符串的首尾空格

			// assertEquals方法判断[实际结果]和[预期结果]不一致就是错的，而assertNotEquals判断[实际结果]和[预期结果]一致就是错的
			cp.assertNotEquals(orderID, null, "下单失败");// 如果实际结果为null就走失败，如果实际结果是一个订单号就走成功
			Map<String, String> orderInfo = api.searchOrderInfo(orderID);
			System.out.println(orderInfo);
		} else {
			cp.isFailed("未搜索到产品，无法下单！");
		}
		cp.result("下单成功，用户中心已生成订单号：" + orderID);
	}
	
	// 2.方式二，使用locator.且使用数据驱动（xml文件），属于高级封装
	@Test(dataProvider = "getData")// 这个数据驱动是指TestBase类中的getData方法（因为在那个方法上面有@DataProvider标志）
	public void case03(Map<String,String> data) {
		Actions action = new Actions(driver);
		String orderID = null;// 声明变量-订单号
		
		// 1.登录
//		locator.linkTo(data.get("主页地址")+"user.php");//打开登录页面
//		locator.element("登录页", "用户名").sendKeys(data.get("用户名"));
//		locator.element("登录页", "密码").sendKeys(data.get("密码"));
//		locator.element("登录页", "立即登录").click();
		
		action.loginAction(data.get("用户名"), data.get("密码"));// 把上面4行封装成了一行
		locator.linkTo(data.get("主页地址"));
		
		// 2.搜索产品
		locator.selectByValue("首页", "搜索分类", data.get("手机类型"));// 下拉列表中选择一个值
		locator.element("首页", "关键词").sendKeys(data.get("手机品牌"));// 搜索框中输入一个值
		locator.element("首页", "搜索").click();// 点搜索按钮
		
		// 3.下订单并打印订单信息
		List<WebElement> goodsList = locator.elements("下订单", "搜索到的商品");// 找到并声明产品列表
		if (goodsList.size() > 0) {
			WebElement goodsFirst = goodsList.get(1);// 声明第一个产品
			goodsFirst.findElement(By.linkText("购买")).click();// 点击第一个产品的“购买”按钮
			locator.wait(1);
			locator.element("下订单", "弹窗的购买按钮").click();// 点击弹窗的的“购买”按钮
			locator.wait(1);
			locator.element("下订单", "结算中心按钮").click();// 点击“结算中心”按钮
			locator.elements("下订单", "配送方式").get(0).click();// 配送方式选“申通快递”
			locator.elements("下订单", "支付方式").get(1).click();// 支付方式选“银行汇款/转帐”
			locator.element("下订单", "提交订单按钮").click();// 点击“提交订单”按钮
			orderID = locator.elementText("下订单", "下成功的订单号").trim();
			
			cp.assertNotEquals(orderID, null, "下单失败");// 加此检查点，如果失败就不往下走了，如果成功就继续往下
			
			Map<String,String> info = action.searchOrderInfo(orderID);// 获取订单信息
			System.out.println(info);
			cp.equalsNotNull(info, "订单号不存在");
		}else{
			cp.isFailed("未搜索到产品，无法下单");
		}
		cp.result("下单成功，用户中心已生成订单号:" + orderID);
	}

}
