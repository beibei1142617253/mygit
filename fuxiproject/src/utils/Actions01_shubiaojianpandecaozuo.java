package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import base.DriverFactory;

/**
 * 本类中的方法是模拟对鼠标、键盘的操作
 * @author Administrator
 *
 */
public class Actions01_shubiaojianpandecaozuo {
	public static String url = "file:///C:/Users/Administrator/workspace/fuxiproject/demo/demo.html";
	public static WebDriver driver = DriverFactory.getDriver("firefox");

	public static void main(String[] args) throws InterruptedException {
		driver.get(url);
		driver.manage().window().maximize();
		Actions actions = new Actions(driver);
		
		//1.单一操作
//		actions.click(driver.findElement(By.linkText("baidu"))).perform();
		
		//2.组合操作 方式一
//		WebElement over = driver.findElement(By.className("over"));
//		((JavascriptExecutor)driver).executeScript("scrollTo(0,10000)");
//		actions.moveToElement(over);
//		actions.click();
//		actions.perform();
//		Thread.sleep(6000);
		
		//2.组合操作 方式二
//		WebElement over = driver.findElement(By.className("over"));
//		((JavascriptExecutor)driver).executeScript("scrollTo(0,10000)");
//		actions.moveToElement(over).click().build().perform();
//		Thread.sleep(6000);
		
		//3.键盘操作
		driver.manage().window().maximize();
		driver.get("http://www.baidu.com");
		WebElement user = driver.findElement(By.id(("kw")));
		user.click();
		actions.sendKeys("慧测");
		actions.sendKeys(Keys.ENTER);// 按回车键
		actions.sendKeys(Keys.CONTROL+"a");// 按CONTROL+"a"，全选组合键
//		actions.keyDown(Keys.ALT).sendKeys(Keys.F4).keyUp(Keys.ALT);//这行相当于（Alt+F4）快捷键，但很难用，一般不用
		actions.perform();
	}

}
