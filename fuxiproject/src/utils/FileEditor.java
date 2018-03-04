package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.channels.FileChannel;

import base.ErrorHandler_zidingyiyichanglei;

/**
 * 本类是封装的创建文件和复制文件的方法，主要是讲对数据流的操作
 * 
 * @author Administrator
 *
 */
public class FileEditor {

	// 本方法是用来创建文件的
	public static void createFile(String context, String targetPath, String encoding) {
		OutputStreamWriter osw = null;
		try {
			osw = new OutputStreamWriter(new FileOutputStream(targetPath), encoding);
			osw.write(context);
			osw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (osw != null)
					osw.close();
			} catch (IOException e) {
				ErrorHandler_zidingyiyichanglei.continueRunning(e, "");
			}
		}
	}

	// 本方法是用来复制文件的
	public static void copyFile(String sourcePath, String targetPath) {
		File file = new File(sourcePath);
		FileChannel in = null;
		FileChannel out = null;
		FileInputStream inStream = null;
		FileOutputStream outStream = null;

		try {
			inStream = new FileInputStream(file);
			outStream = new FileOutputStream(targetPath);
			in = inStream.getChannel();
			out = outStream.getChannel();
			in.transferTo(0, in.size(), out);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
				inStream.close();
				out.close();
				outStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
