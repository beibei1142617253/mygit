package utils;

import java.util.Map;

import org.openqa.selenium.remote.DesiredCapabilities;

import utils.XmlParser_zhengshi;

/**
 * 本类是被EmailSender类调用的一个参数类，包含几个set、get和isExist方法
 * 它的作用主要是解析Config.xml，从中获取发邮件的各种参数。然后在EmailSender类中new本类的构造器时就初始化了这些参数值
 * 
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
public class Parameter extends DesiredCapabilities {

	public static void main(String[] args) {
		XmlParser_zhengshi xmljx = new XmlParser_zhengshi("./config/Config.xml");
		Map<String, String> mailParameters = xmljx.getChildrenInfo("/config/mail");
		System.out.println(mailParameters);
		System.out.println(mailParameters.get("smtp"));
		for (Map.Entry<String, String> map : mailParameters.entrySet()) {
			System.out.println(map.getKey() + "，" + map.getValue());
		}
	}

	public void set(String name, String value) {
		XmlParser_zhengshi xmljx = new XmlParser_zhengshi("./config/Config.xml");
		Map<String, String> mailParameters = xmljx.getChildrenInfo("/config/mail");
		setCapability(mailParameters.get(name), value);
	}

	public void set(String name, boolean value) {
		setCapability(name, value);
	}

	public void set(String name, Object value) {
		setCapability(name, value);
	}

	public String get(String name) {
		Object cap = getCapability(name);
		if (cap == null) {
			return null;
		} else
			return cap.toString();
	}

	public boolean isExist(String name) {
		Object cap = getCapability(name);
		if (cap == null) {
			return false;
		} else
			return true;
	}

}
