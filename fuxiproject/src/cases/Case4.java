package cases;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import base.WebSingle;
import base.WebSuite;

/**
 * 本case实现对[首页-精品推荐区-所有商品价格]的遍历验证的功能
 * 
 * @author Administrator
 *
 */
//public class Case4 extends WebSuite {// 批量正式测试的模板
public class Case4 extends WebSingle {// 单个用例调试运行的模板
	
	// 1.方式一，使用driver.和api.，属于初级封装
//	@Test
	public void demo() {
		driver.get("http://www.huicewang.com/ecshop/index.php");
		api.scrollToBottom();

		List<WebElement> goodsTab = driver.findElements(By.xpath("//div[@id='itemBest']/h2/a"));// 首页的商品展示区的Tab页签
		for (WebElement tab : goodsTab) {
			tab.click();
			api.wait(1);
			/**
			 * 下面这行代码的作用：传入的参数是一个by对象
			 * 1.这是每个页签内的所有商品价格的集合
			 * 2.为商品的价格new一个容器
			 * 3.将商品价格加进容器中，返回
			 */
			List<String> goodsPrice = api
					.getElementsText(By.xpath("//div[@id='show_best_area']/div[@class='goodsItem']/font"));
			cp.notEquals(goodsPrice, null, "商品价格异常：null！");
			cp.notEquals(goodsPrice, " ", "商品价格异常：\" \"！");
		}
		cp.result("首页-精品推荐区-所有商品价格无null值！");
	}
	
	// 2.方式二，使用locator.且使用数据驱动（xml文件），属于高级封装
	@Test(dataProvider = "getData")// 这个数据驱动是指TestBase类中的getData方法（因为在那个方法上面有@DataProvider标志）
	public void case04(Map<String,String> data) {
		locator.linkTo(data.get("主页地址"));
		locator.scrollToMiddle();// 滚屏到中间精品推荐区
		locator.wait(2);
		List<WebElement> links = locator.elements("首页", "精品推荐-tab签");// 找到精品推荐区的所有Tab页签
		for (WebElement link : links) {// 循环每个Tab页签
			link.click();// 点击循环到的Tab页签
			locator.wait(2);
			List<String> textStrings = locator.elementsText("首页", "精品推荐-产品价格");// 得到该Tab页签下的所有产品的价格
			cp.notEquals(textStrings, "null", "价格异常: null");
			cp.notEquals(textStrings, " ", "价格异常: \" \"");
		}
		cp.result("首页精品推荐区域的所有产品价格正常！");
	}
}
