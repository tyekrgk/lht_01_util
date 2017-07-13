package lht.wangtong.core.utils.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import lht.wangtong.core.utils.vo.LoginUser;

import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

/**
 * Velocity转换
 * 
 * @author
 */
public class VelocityTest {
	
	 public static Logger logger = Logger.getLogger(VelocityTest.class.getClass());
	/**
	 * 主函数
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// 获取模板引擎
		VelocityEngine ve = new VelocityEngine();
		// 模板文件所在的路径
		String path = "D:/velocityTemple";
		// 设置参数
		ve.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, path);
		// 处理中文问题
		ve.setProperty(Velocity.INPUT_ENCODING, "GBK");
		ve.setProperty(Velocity.OUTPUT_ENCODING, "GBK");
		try {
			// 初始化模板
			ve.init();
			// 获取模板(hello.html)
			// Velocity模板的名称
			Template template = ve.getTemplate("hello.txt");
			// 获取上下文
			VelocityContext root = new VelocityContext();
			// 把数据填入上下文
			List<LoginUser> list = new ArrayList<LoginUser>();
			for (int i = 0; i < 10; i++) {
				LoginUser sso = new LoginUser();
				sso.setId(Long.valueOf(i));
				list.add(sso);
			}
			root.put("list", list); // （注意：与上面的对应）
			// 输出路径
			String outpath = "d:/velocityTemple/helloworld.html";
			// 输出
			Writer mywriter = new PrintWriter(new FileOutputStream(new File(outpath)));
			template.merge(root, mywriter);
			mywriter.flush();
		} catch (Exception e) {
			logger.error(e);
		}
	}
}
