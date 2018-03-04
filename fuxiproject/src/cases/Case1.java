package cases;

import java.util.Map;

import org.openqa.selenium.By;
import org.testng.annotations.Test;

import base.WebSingle;
import base.WebSuite;

/**
 * 本case是在ecshop首页搜索诺基亚手机的功能
 * 
 * @author Administrator
 *
 */
// public class Case1 extends WebSuite {// 批量正式测试的模板
public class Case1 extends WebSingle {// 单个用例调试运行的模板

	// 1.方式一，使用driver.和api.，属于初级封装
//	@Test
	public void case1() {
		driver.get("http://www.huicewang.com/ecshop/");
//		driver.manage().window().maximize();// 在DriverFactory类中已经封装了最大化窗口的代码，只要启了driver，窗口就自动最大化了，不用在这里单独写

		api.selectByValue(By.id("category"), "3");// 搜索下拉框内选择【GSM手机】
		driver.findElement(By.id("keyword")).sendKeys("诺基亚");// 搜索框内输入【诺基亚】
		driver.findElement(By.name("imageField")).click();// 点击搜索按钮

		cp.equals(api.elementsIsPresent(By.xpath("//div[@class='goodsItem']")), true, "未搜索到商品：GSM-诺基亚！");
		cp.result("首页搜索功能正常！");
	}
	
	// 2.方式二，使用locator.，属于中级封装
	// locator类是用来跟对象库文件打交道的，而api类则是跟系统、页面等相关的
//	@Test
	public void case12() {
		locator.linkTo("http://www.huicewang.com/ecshop/");
		
		locator.selectByValue("首页", "搜索分类", "3");// 下拉列表中选择一个值
		locator.element("首页", "关键词").sendKeys("诺基亚");// 搜索框中输入一个值
		locator.element("首页", "搜索").click();// 点搜索按钮
		
		cp.equals(api.elementsIsPresent(By.xpath("//div[@class='goodsItem']")), true, "未搜索到产品:GSM-诺基亚");
		cp.result("首页搜索功能正常");
	}
	
	// 3.方式三，使用locator.且使用数据驱动（xml文件），属于高级封装
//	@Test(dataProvider = "getData")// 这个数据驱动是指TestBase类中的getData方法（因为在那个方法上面有@DataProvider标志）
	public void case01(Map<String,String> data) {// case01是本方法名，同样也是Case1.xml里的节点名
		                                         // 而data是Global.xml和Case1.xml中的数据合并后的大Map
		locator.linkTo(data.get("主页地址"));
		
		locator.selectByValue("首页", "搜索分类", data.get("手机类型"));// 而data.get中的词都是从Global.xml和Case1.xml合并后的map里获取的
		locator.element("首页", "关键词").sendKeys(data.get("手机品牌"));
		locator.element("首页", "搜索").click();// 凡是locator方法中的词，都是从元素对象库中获取的
		
		boolean isfound = api.elementsIsPresent(By.xpath("//div[@class='goodsItem']"));
		cp.equals(isfound, true, "未搜索到产品：" + data.get("手机品牌"));
		cp.result("搜索到产品：" + data.get("手机品牌"));
	}
	
	// 4.方式四，使用locator.且使用数据驱动（excel文件）
	@Test(dataProvider = "getDataExcel")
	public void Sousuo(Map<String,String> data) {// 和方式三一样，本方法名也要用所取数据的excel文件里的sheet页名
		locator.linkTo(data.get("BaseUrl") + data.get("HomeUrl"));
		
		locator.selectByValue("首页", "搜索分类", data.get("手机类型"));
		locator.element("首页", "关键词").sendKeys(data.get("手机品牌"));
		locator.element("首页", "搜索").click();
		
		boolean isfound=api.elementsIsPresent(By.xpath("//div[@class='goodsItem']"));
		cp.equals(isfound, true, "未搜索到产品：" + data.get("手机品牌"));
		cp.result("搜索到产品：" + data.get("手机品牌"));
	}

}
