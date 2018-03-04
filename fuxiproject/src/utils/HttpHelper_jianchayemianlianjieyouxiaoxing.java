package utils;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * 本类是检查页面链接有效性的工具
 * 
 * @author Administrator
 *
 */
public class HttpHelper_jianchayemianlianjieyouxiaoxing {

	// 本方法的入参是单个url
	public static synchronized boolean URLisAvailable(String url) {
		boolean flag = false;// 先定义一个标志位
		int counts = 0;
		while (counts < 3) {
			int state = -1;// 有时候200等状态码可能是上一次循环遗留的，所以最好在开始前先把状态重置一下
			try {
				HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
				state = con.getResponseCode();
				if (state == 200) {
					System.out.println("URL is available: " + url);
					flag = true;
					break;// 结束所有循环
				}
			} catch (Exception e) {
				counts++;
				System.out.println("URL不可用，第" + counts + "次链接" + url + "为" + state);
				continue;// 结束本次循环，继续下次循环
			}
		}
		return flag;
	}

	// 本方法的入参是url列表
	public static synchronized boolean URLisAvailable(List<String> url) {
		boolean flag = true;// 先定义一个标志位
		int counts = 0;
		if (url == null || url.size() <= 0) {
			flag = false;
			System.out.println("链接数组为空！");
		}
		for (int i = 0; i < url.size(); i++) {
			if (url.get(i).equalsIgnoreCase(null)) {
				System.out.println(url.get(i) + "为空链接！");
				flag = false;
			}
			while (counts < 3) {
				int state = -1;
				try {
					HttpURLConnection con = (HttpURLConnection) new URL(url.get(i)).openConnection();
					state = con.getResponseCode();
					if (state == 200) {
						// Log.info("URL is available: "+url.get(i));
						System.out.println("URL is available: " + url.get(i));
						break;
					}
				} catch (Exception e) {
					counts++;
					System.out.println("URL不可用，第" + counts + "次链接" + url.get(i) + "为" + state);
					flag = false;
					continue;
				}
			}
		}
		return flag;
	}
}
