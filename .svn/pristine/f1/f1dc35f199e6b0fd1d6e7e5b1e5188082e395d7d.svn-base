package lht.wangtong.core.utils.image;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


import org.apache.log4j.Logger;

/**
 * 图片水印
 * @author li youlong
 * 
 */
public final class ImageUtils {
	
	 public static Logger logger = Logger.getLogger(ImageUtils.class.getClass());
	
	/**
	 * 图片水印
	 * 
	 * @param pressImg
	 *            水印图片
	 * @param targetImg
	 *            目标图片
	 * @param x
	 *            修正值 默认在中间
	 * @param y
	 *            修正值 默认在中间
	 * @param alpha
	 *            透明度
	 */
	public final static void pressImage(String pressImg, String targetImg,int x, int y, float alpha) {
		try {
			File img = new File(targetImg);
			Image src = ImageIO.read(img);
			int wideth = src.getWidth(null);
			int height = src.getHeight(null);
			BufferedImage image = new BufferedImage(wideth, height,BufferedImage.TYPE_INT_RGB);
			Graphics2D g = image.createGraphics();
			g.drawImage(src, 0, 0, wideth, height, null);
			// 水印文件
			Image src_biao = ImageIO.read(new File(pressImg));
			int wideth_biao = src_biao.getWidth(null);
			int height_biao = src_biao.getHeight(null);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,alpha));
			g.drawImage(src_biao, (wideth - wideth_biao) / 2,(height - height_biao) / 2, wideth_biao, height_biao, null);
			// 水印文件结束
			g.dispose();
			ImageIO.write((BufferedImage) image, "jpg", img);
		} catch (Exception e) {
			logger.error(e);
		}
	}

	/**
	 * 文字水印
	 * 
	 * @param pressText
	 *            水印文字
	 * @param targetImg
	 *            目标图片
	 * @param fontName
	 *            字体名称
	 * @param fontStyle
	 *            字体样式
	 * @param color
	 *            字体颜色
	 * @param fontSize
	 *            字体大小
	 * @param x
	 *            修正值
	 * @param y
	 *            修正值
	 * @param alpha
	 *            透明度
	 */
	public static void pressText(String pressText, String targetImg,String fontName, int fontStyle, Color color, int fontSize, int x,int y, float alpha) {
		try {
			File img = new File(targetImg);
			Image src = ImageIO.read(img);
			int width = src.getWidth(null);
			int height = src.getHeight(null);
			BufferedImage image = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
			Graphics2D g = image.createGraphics();
			g.drawImage(src, 0, 0, width, height, null);
			g.setColor(color);
			g.setFont(new Font(fontName, fontStyle, fontSize));
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,alpha));
			g.drawString(pressText, (width - (getLength(pressText) * fontSize)) / 2 + x, (height - fontSize) / 2 + y);
			g.dispose();
			ImageIO.write((BufferedImage) image, "jpg", img);
		} catch (Exception e) {
			logger.error(e);
		}
	}

	public static void main(String[] args) throws IOException {
		// pressImage("G:\\imgtest\\sy.jpg", "G:\\imgtest\\test1.jpg", 0, 0,
		// 0.5f);
		// pressText("我是文字水印", "G:\\imgtest\\test1.jpg", "黑体", 36, Color.white,
		// 80, 0, 0, 0.3f);
	}

	public static int getLength(String text) {
		int length = 0;
		for (int i = 0; i < text.length(); i++) {
			if (new String(text.charAt(i) + "").getBytes().length > 1) {
				length += 2;
			} else {
				length += 1;
			}
		}
		return length / 2;
	}
}
