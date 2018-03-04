package cases;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import base.WebSingle;
import base.WebSuite;
import utils.HttpHelper_jianchayemianlianjieyouxiaoxing;

/**
 * 本类是测试链接有效性的用例
 * 
 * @author Administrator
 *
 */
// public class Case6 extends WebSuite {// 批量正式测试的模板
public class Case6 extends WebSingle {// 单个用例调试运行的模板

	@Test // 因为本测试没用数据驱动（dataProvider="getData"），所以不用在test-data文件夹下建Case06的xml数据文件
	public void case06() {
		locator.linkTo("http://www.huicewang.com/ecshop/");
		locator.scrollToMiddle();

		List<WebElement> links = locator.elements("首页", "精品推荐-tab签");
		for (WebElement link : links) {
			link.click();
			locator.wait(1);
			List<String> imageLink = locator.elementsAttribute("首页", "精品推荐-产品图片", "src");
			cp.assertEquals(HttpHelper_jianchayemianlianjieyouxiaoxing.URLisAvailable(imageLink), true);
		}
		cp.result("首页精品推荐区域的所有产品图片链接正常");
	}
}
