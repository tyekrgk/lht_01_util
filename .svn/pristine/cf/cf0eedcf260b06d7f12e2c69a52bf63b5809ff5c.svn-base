package lht.wangtong.core.utils.image;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.FileChannel;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.swing.ImageIcon;





import org.apache.log4j.Logger;

import sun.misc.BASE64Decoder;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * 图片处理
 * @author li youlong
 *
 */
public class ImageTool{
	
	  public static Logger logger = Logger.getLogger(ImageTool.class.getClass());
	
	 /**
	  * 指定区域裁剪：是指定区域, 裁剪后有些地方丢失的。
	  * 一般用于用户头像、发布范儿
	  * @param sourceImgFileName 源文件名
	  * @param x 截取的起始x位置 
	  * @param y 截取的起始y位置
	  * @param w 截取的宽度  0 代表不截取
	  * @param h 截取的高度  0 代表不截取
	  * @param sx 缩放的X比例（宽） 尺寸大小 1.0表示不缩放
	  * @param sy 缩放的Y比例（高） 尺寸大小 1.0表示不缩放
	  * @param targetImgFileName 目标文件名，含扩展名，暂时固定必须以 png作为扩展名。
	  * @return
	  */
	 public static int clipImage(String sourceImgFileName,int x,int y,int w,int h,double sx, double sy,String targetImgFileName){
		 BufferedImage sourceImage=null;
		 BufferedImage targetImage=null;
		 File sourceFile = null;
		 File targetFile = null;

		 sourceFile = new File(sourceImgFileName);
		 if(!sourceFile.exists()){
//			 logger.debug("输入文件不存在！"+sourceImgFileName);
			 return 1;			 
		 }
		 try {
			 sourceImage=ImageIO.read(sourceFile);
	     } catch (IOException e) {
//			 logger.debug("输入文件不是一个图片文件！"+sourceImgFileName);
			 return 2;
		 }
	     
		 //需要判读位置是否超出范围     
	     //超出部分，暂不补白
	     if(sourceImage.getHeight()*sy<y+h){
//			 logger.debug("截取位置的高度超出范围 "+sourceImgFileName);
			 return 3;	    	 
	     }
	     if(sourceImage.getWidth()*sx<x+w){
//			 logger.debug("截取位置的宽度超出范围 "+sourceImgFileName);
			 return 4;	    	 
	     }

	     //增加缩放效果  质量效果见参数：AffineTransformOp.TYPE_NEAREST_NEIGHBOR
	     if(sx!=1.0d || sy!=1.0d){
//		     logger.debug("缩放");
		     AffineTransform transform = AffineTransform.getScaleInstance(sx, sy);
		     AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		     //缩放结果
		     sourceImage = op.filter(sourceImage, null);  	    	 
	     }
	     else{
//	    	 logger.debug("不缩放");
	     }
	     //判断是否需要截取 也许是源图即可
	     if((sourceImage.getHeight()==y+h && sourceImage.getWidth()==x+w)
	    	|| y+h+x+w==0	 ){
//	    	 logger.debug("不截取");
	    	 targetImage = sourceImage;
	     }
	     else{
//		      logger.debug("截取");
			  int rgbs[]=new int[w*h];
			  rgbs=sourceImage.getRGB(x,y, w, h, rgbs,0,w);
			  targetImage = new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
			  targetImage.setRGB(0, 0, w, h, rgbs,0,w);
	     }
	     
		  targetFile = new File(targetImgFileName);
		  //targetFile.deleteOnExit();

    	  //注意如果是 gif 类型，只能转成 png（才能保留好的效果，如透明）  
		  //使用 jpg 时，缩小正常，但要截取后发现颜色失真
		  String formatName = "png";
		  try {
			if(ImageIO.write(targetImage,formatName, targetFile)){
//				 logger.debug("保存文件成功！"+targetImgFileName);		
				 return 0;				
			}else{
//				 logger.debug("保存文件失败！"+sourceImgFileName);		
				 return 5;								
			}

		} catch (Exception e) {
			logger.error(e);
//			 logger.debug("保存文件错误！"+targetImgFileName);
			 return 6;
		}	    	 
	     
		//return 0;
	 }

	 /**
	  * 缩小图片: 原来所有图片内容都在。不等比缩放图片（变形），不截取
	  * @param sourceImgFileName 源文件名
	  * @param w 缩放后的宽度  >0 
	  * @param h 缩放后的高度  >0 
	  * @param targetImgFileName 目标文件名，含扩展名，暂时固定必须以 png作为扩展名。
	  * @return
	  */
	 public static int scaleImage(String sourceImgFileName,int w,int h,String targetImgFileName){
		 BufferedImage sourceImage=null;
		 BufferedImage targetImage=null;
		 File sourceFile = null;
		 File targetFile = null;

		 sourceFile = new File(sourceImgFileName);
		 if(!sourceFile.exists()){
			 logger.debug("输入文件不存在！"+sourceImgFileName);
			 return 1;			 
		 }
		 try {
			 sourceImage=ImageIO.read(sourceFile);
	     } catch (IOException e) {
			 logger.debug("输入文件不是一个图片文件！"+sourceImgFileName);
			 return 2;
		 }
	     
		 //需要取出原图宽高，计算出缩小比例
	     double sw = (double)w / (double)sourceImage.getWidth();
	     double sh = (double)h / (double)sourceImage.getHeight();
	     
	     

	     //增加缩放效果  质量效果见参数：AffineTransformOp.TYPE_NEAREST_NEIGHBOR     TYPE_BICUBIC和TYPE_BILINEAR都是蒙上一层 暗黄;
		     logger.debug("缩放");
		     AffineTransform transform = AffineTransform.getScaleInstance(sw, sh);
		     AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		     //缩放结果
		     targetImage = op.filter(sourceImage, null);  	    	 
	     
		  targetFile = new File(targetImgFileName);
		  //targetFile.deleteOnExit(); 退出时删除

    	  //注意如果是 gif 类型，只能转成 png（才能保留好的效果，如透明）  
		  //使用 jpg 时，缩小正常，但要截取后发现颜色失真
		  String formatName = "png";
		  try {
			if(ImageIO.write(targetImage,formatName, targetFile)){
				 logger.debug("保存文件成功！"+targetImgFileName);		
				 return 0;				
			}else{
				 logger.debug("保存文件失败！"+sourceImgFileName);		
				 return 5;								
			}

		} catch (Exception e) {
			logger.error(e);
			 logger.debug("保存文件错误！"+targetImgFileName);
			 return 6;
		}	    	 
	 }
	 
	 /**
	  * 等比缩放图片（不变形），根据参数确定是否需要补空白
	  * @param sourceImgFileName 源文件名
	  * @param w 缩放后的宽度  >0 
	  * @param h 缩放后的高度  >0 
	  * @param bb 是否补空白
	  * @param targetImgFileName 目标文件名，含扩展名，暂时固定必须以 png作为扩展名。
	  * @return
	  */
	 public static int scaleImage(String sourceImgFileName,int w,int h,boolean bb,String targetImgFileName){
		 BufferedImage sourceImage=null;
		 BufferedImage targetImage=null;
		 BufferedImage tempImage=null;
		 File sourceFile = null;
		 File targetFile = null;

		 sourceFile = new File(sourceImgFileName);
		 if(!sourceFile.exists()){
			 logger.debug("输入文件不存在！"+sourceImgFileName);
			 return 1;			 
		 }
		 try {
			 sourceImage=ImageIO.read(sourceFile);
	     } catch (IOException e) {
			 logger.debug("输入文件不是一个图片文件！"+sourceImgFileName);
			 return 2;
		 }
	     
	     //需要取出原图宽高，计算出缩小比例
	     double sw = (double)w / (double)sourceImage.getWidth();
	     double sh = (double)h / (double)sourceImage.getHeight();
	     //取最小的比例值
	     double sx = sw>sh?sh:sw;
	     double sy = sx;
	     

	     //增加缩放效果  质量效果见参数：AffineTransformOp.TYPE_NEAREST_NEIGHBOR
	     if(sx!=1.0d || sy!=1.0d){
		     logger.debug("缩放");
		     AffineTransform transform = AffineTransform.getScaleInstance(sx, sy);
		     AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		     //缩放结果
		     tempImage = op.filter(sourceImage, null);  	    	 
	     }

	     if (bb) {
		     targetImage = new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB); 
	    	 Graphics2D g = targetImage.createGraphics();   
	    	 g.setColor(Color.white);   
	    	 g.fillRect(0, 0, w, h);   
	    	 if (w == tempImage.getWidth())   
	    		 g.drawImage(tempImage, 0, (h - tempImage.getHeight()) / 2, tempImage.getWidth(), tempImage.getHeight(), Color.white, null);   
	    	 else  
	    		 g.drawImage(tempImage, (w - tempImage.getWidth()) / 2, 0, tempImage.getWidth(), tempImage.getHeight(), Color.white, null);   
	    	 g.dispose();  
	     } else {
	    	 targetImage = tempImage;
	     }
	     
	     targetFile = new File(targetImgFileName);

	     //注意如果是 gif 类型，只能转成 png（才能保留好的效果，如透明）  
	     //使用 jpg 时，缩小正常，但要截取后发现颜色失真
	     String formatName = "png";
	     try {
	    	 if(ImageIO.write(targetImage,formatName, targetFile)){
	    		 logger.debug("保存文件成功！"+targetImgFileName);		
				 return 0;				
	    	 }else{
				 logger.debug("保存文件失败！"+sourceImgFileName);		
				 return 5;								
	    	 }

	     } catch (Exception e) {
	    	 logger.error(e);
			 logger.debug("保存文件错误！"+targetImgFileName);
			 return 6;
	     }	    	 
	 }
	 
 
	 /**
	  * 字符串形式的图片保存为图片格式的文件
	  * 一般用于 手机端将图片转为字符串，传给后台接口服务，接口服务再转为图片保存。
	  * @param imgStr
	  * @param imgFilePath
	  * @return
	  */
     public static  boolean inputImageByBaese64(String imgStr,String imgFilePath){   //对字节数组字符串进行Base64解码并生成图片  
         if (imgStr == null) //图像数据为空  
             return false;  
         BASE64Decoder decoder = new BASE64Decoder();  
         try   
         {  
             //Base64解码  
             byte[] b = decoder.decodeBuffer(imgStr);  
             for(int i=0;i<b.length;++i)  
             {  
                 if(b[i]<0)  
                 {//调整异常数据  
                     b[i]+=256;  
                 }  
             }  
             //生成图片
             OutputStream out = new FileOutputStream(imgFilePath);      
             out.write(b);  
             out.flush();  
             out.close();  
             return true;  
         }   
         catch (Exception e)   
         {  
             return false;  
         }  
     }

	 /** 
	  * 原图缩放：减轻图片缩放后颜色失真
	  * @author zhouxiaolong
	  * @param imgsrc
	  * @param widthdist
	  * @param heightdist
	  * @param imgdist
	  */
     public static void reduceImg(String imgsrc, int widthdist, int heightdist, String imgdist) {   
 	    try {   
 	        File srcfile = new File(imgsrc);   
 	        if (!srcfile.exists()) {   
 	            return;   
 	        }   
 	        Image src = javax.imageio.ImageIO.read(srcfile);   
 	  
 	        BufferedImage tag= new BufferedImage((int) widthdist, (int) heightdist,   
 	                BufferedImage.TYPE_INT_RGB);   
 	  
 	        tag.getGraphics().drawImage(src.getScaledInstance(widthdist, heightdist,  Image.SCALE_SMOOTH), 0, 0,  null);   
 	        tag.getGraphics().drawImage(src.getScaledInstance(widthdist, heightdist,  Image.SCALE_AREA_AVERAGING), 0, 0,  null);   	           
 	        FileOutputStream out = new FileOutputStream(imgdist);
 	        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);   
 	        JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(tag);  
 	        jep.setQuality(1.0f,true);
 	        encoder.encode(tag,jep);   
 	        out.close();   
 	  
 	    } catch (IOException ex) {   
 	        logger.error(ex);   
 	    }   
 	} 
     
     /**
      * 原图缩放效果 比scaleImage()方法好  ！！！！  网上找的,说是老外写的
      * @param originalFileName
      * @param resizedFileName
      * @param newWidth 新的宽度,按比例计算出高度
      * @param quality
      * @throws IOException
      */
     public static void resize(String originalFileName,  
             int newWidth, float quality, String resizedFileName) throws IOException {  
		 File sourceFile = new File(originalFileName);
		 if(!sourceFile.exists()){
			 logger.debug("输入文件不存在！"+originalFileName);
			 return;			 
		 } 
         if (quality > 1) {  
             throw new IllegalArgumentException(  
                     "Quality has to be between 0 and 1");  
         }  
         ImageIcon ii = new ImageIcon(sourceFile.getCanonicalPath());  
         Image i = ii.getImage();  
         Image resizedImage = null;  
   
         int iWidth = i.getWidth(null);  //原图宽
         int iHeight = i.getHeight(null);//原图高  
         int newHeight = (int)((iHeight*newWidth*1.0)/iWidth); //按比例减少高度
         resizedImage = i.getScaledInstance(newWidth,newHeight, Image.SCALE_SMOOTH);  
   
         // This code ensures that all the pixels in the image are loaded.  
         Image temp = new ImageIcon(resizedImage).getImage();  
   
         // Create the buffered image.  
         BufferedImage bufferedImage = new BufferedImage(temp.getWidth(null),  
                 temp.getHeight(null), BufferedImage.TYPE_INT_RGB);  
   
         // Copy image to buffered image.  
         Graphics g = bufferedImage.createGraphics();  
   
         // Clear background and paint the image.  
         g.setColor(Color.white);  
         g.fillRect(0, 0, temp.getWidth(null), temp.getHeight(null));  
         g.drawImage(temp, 0, 0, null);  
         g.dispose();  
   
         // Soften.  
         float softenFactor = 0.05f;  
         float[] softenArray = { 0, softenFactor, 0, softenFactor,  
                 1 - (softenFactor * 4), softenFactor, 0, softenFactor, 0 };  
         Kernel kernel = new Kernel(3, 3, softenArray);  
         ConvolveOp cOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);  
         bufferedImage = cOp.filter(bufferedImage, null);  
   
         // Write the jpeg to a file.  
         FileOutputStream out = new FileOutputStream(new File(resizedFileName));  
   
         // Encodes image as a JPEG data stream  
         JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);  
   
         JPEGEncodeParam param = encoder  
                 .getDefaultJPEGEncodeParam(bufferedImage);  
   
         param.setQuality(quality, true);  
   
         encoder.setJPEGEncodeParam(param);  
         encoder.encode(bufferedImage);  
         logger.debug("保存文件成功！"+resizedFileName);	
     }     
 
     /**
      * 原图缩放效果 比scaleImage()方法好  ！！！！  网上找的,说是老外写的
      * @param originalFileName
      * @param resizedFileName
      * @param newWidth 新的宽度
      * @param newHeight 新的高度
      * @throws IOException
      */
     public static void resize(String originalFileName,  
             int newWidth, int newHeight, String resizedFileName) throws IOException {  
		 File sourceFile = new File(originalFileName);
		 if(!sourceFile.exists()){
			 logger.debug("输入文件不存在！"+originalFileName);
			 return;			 
		 } 
		 float quality=1.0f;//质量最好的
         ImageIcon ii = new ImageIcon(sourceFile.getCanonicalPath());  
         Image i = ii.getImage();  
         Image resizedImage = null;  
         resizedImage = i.getScaledInstance(newWidth,newHeight, Image.SCALE_SMOOTH);  
   
         // This code ensures that all the pixels in the image are loaded.  
         Image temp = new ImageIcon(resizedImage).getImage();  
   
         // Create the buffered image.  
         BufferedImage bufferedImage = new BufferedImage(temp.getWidth(null),  
                 temp.getHeight(null), BufferedImage.TYPE_INT_RGB);  
   
         // Copy image to buffered image.  
         Graphics g = bufferedImage.createGraphics();  
   
         // Clear background and paint the image.  
         g.setColor(Color.white);  
         g.fillRect(0, 0, temp.getWidth(null), temp.getHeight(null));  
         g.drawImage(temp, 0, 0, null);  
         g.dispose();  
   
         // Soften.  
         float softenFactor = 0.05f;  
         float[] softenArray = { 0, softenFactor, 0, softenFactor,  
                 1 - (softenFactor * 4), softenFactor, 0, softenFactor, 0 };  
         Kernel kernel = new Kernel(3, 3, softenArray);  
         ConvolveOp cOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);  
         bufferedImage = cOp.filter(bufferedImage, null);  
   
         // Write the jpeg to a file.  
         FileOutputStream out = new FileOutputStream(new File(resizedFileName));  
   
         // Encodes image as a JPEG data stream  
         JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);  
   
         JPEGEncodeParam param = encoder  
                 .getDefaultJPEGEncodeParam(bufferedImage);  
   
         param.setQuality(quality, true);  
   
         encoder.setJPEGEncodeParam(param);  
         encoder.encode(bufferedImage);  
         logger.debug("保存文件成功！"+resizedFileName);	
     }     
     
     /**
      * 压缩容量：像素不变,只是减少质量(quality影响放大)，降低存储容量,不缩放
      * 这是为了WEB界面显示用,压缩质量比率,自动计算, 如果原来的文件容量大,则比率更低,最终生成的文件希望在 300K以内
      * 如果本身小于300K则不处理，直接保存为新文件即可返回
      * 
      *注意：srcFilePath 与 descFilePath 这两个路径中，图片的名称不能一样，不然图片会压缩失败！！！
      * @param srcFilePath
      * @param descFilePath
      * @return
      * @throws IOException
      */
     public static boolean compressJpg4Web(String srcFilePath, String descFilePath) throws IOException {
         File srcFile = null;
         File descFile = null;
         if (isBlank(srcFilePath)) {
             return false;
         }
         srcFile = new File(srcFilePath);
         int srcFileLength = (int)(srcFile.length()/1024);
         logger.debug("src file size(KB):"+srcFileLength);
         if(srcFileLength<300){
        	 //太小的文件不需要再减容量
        	 descFile = new File(descFilePath);
        	 copyFile(srcFile,descFile);
        	 return true;
         }
         BufferedImage src = null;
         FileOutputStream out = null;
         ImageWriter imgWrier;
         ImageWriteParam imgWriteParams;

         // 指定写图片的方式为 jpg
         imgWrier = ImageIO.getImageWritersByFormatName("jpg").next();
         imgWriteParams = new javax.imageio.plugins.jpeg.JPEGImageWriteParam(null);
         // 要使用压缩，必须指定压缩方式为MODE_EXPLICIT
         imgWriteParams.setCompressionMode(imgWriteParams.MODE_EXPLICIT);
          float quality = 1f;
         // 这里指定压缩的程度，参数qality是取值0~1范围内
         if(srcFileLength>=2*1024){
        	 quality = 0.3f;
         }else if(srcFileLength>=1024){
        	 quality = 0.5f;
         }else if(srcFileLength>=500){
        	 quality = 0.7f;
         }else{
        	 quality = 0.9f;
         }
         imgWriteParams.setCompressionQuality(quality);

         imgWriteParams.setProgressiveMode(imgWriteParams.MODE_DISABLED);
         ColorModel colorModel =ImageIO.read(new File(srcFilePath)).getColorModel();// ColorModel.getRGBdefault();
         // 指定压缩时使用的色彩模式
         imgWriteParams.setDestinationType(new javax.imageio.ImageTypeSpecifier(
                 colorModel, colorModel.createCompatibleSampleModel(16, 16)));
         try {
                 src = ImageIO.read(srcFile);
                 out = new FileOutputStream(descFilePath);

                 imgWrier.reset();
                 // 必须先指定 out值，才能调用write方法, ImageOutputStream可以通过任何OutputStream构造
                 imgWrier.setOutput(ImageIO.createImageOutputStream(out));
                 // 调用write方法，就可以向输入流写图片
                 imgWrier.write(null, new IIOImage(src, null, null),imgWriteParams);
                 out.flush();
                 out.close(); 
                 File fNew = new File(descFilePath);
                 logger.debug("descfile size(KB):"+fNew.length()/1024);
                 logger.debug("quality :"+quality);
         } catch (Exception e) {
             logger.error(e);
             return false;
         }
         return true;
     }

     /**
      * 压缩容量：像素不变,只是减少质量(quality影响放大)，降低存储容量,不缩放
      * @param srcFilePath
      * @param descFilePath
      * @param quality 图像质量系数 0.0 至 1.0
      * @return
      * @throws IOException
      */
     public static boolean compressJpg(String srcFilePath,float quality,  String descFilePath) throws IOException {
         File srcFile = null;
         File descFile = null;
         if (isBlank(srcFilePath)) {
             return false;
         }
         srcFile = new File(srcFilePath);
         int srcFileLength = (int)(srcFile.length()/1024);
         logger.debug("src file size(KB):"+srcFileLength);
         if(quality==1.0){
        	 //原图一样
        	 descFile = new File(descFilePath);
        	 copyFile(srcFile,descFile);
        	 return true;
         }
         BufferedImage src = null;
         FileOutputStream out = null;
         ImageWriter imgWrier;
         ImageWriteParam imgWriteParams;

         // 指定写图片的方式为 jpg
         imgWrier = ImageIO.getImageWritersByFormatName("jpg").next();
         imgWriteParams = new javax.imageio.plugins.jpeg.JPEGImageWriteParam(null);
         // 要使用压缩，必须指定压缩方式为MODE_EXPLICIT
         imgWriteParams.setCompressionMode(imgWriteParams.MODE_EXPLICIT);
         imgWriteParams.setCompressionQuality(quality);

         imgWriteParams.setProgressiveMode(imgWriteParams.MODE_DISABLED);
         ColorModel colorModel =ImageIO.read(new File(srcFilePath)).getColorModel();// ColorModel.getRGBdefault();
         // 指定压缩时使用的色彩模式
         imgWriteParams.setDestinationType(new javax.imageio.ImageTypeSpecifier(
                 colorModel, colorModel.createCompatibleSampleModel(16, 16)));
         try {
                 src = ImageIO.read(srcFile);
                 out = new FileOutputStream(descFilePath);

                 imgWrier.reset();
                 // 必须先指定 out值，才能调用write方法, ImageOutputStream可以通过任何OutputStream构造
                 imgWrier.setOutput(ImageIO.createImageOutputStream(out));
                 // 调用write方法，就可以向输入流写图片
                 imgWrier.write(null, new IIOImage(src, null, null),imgWriteParams);
                 out.flush();
                 out.close(); 
                 File fNew = new File(descFilePath);
                 logger.debug("descfile size(KB):"+fNew.length()/1024);
                 logger.debug("quality :"+quality);
         } catch (Exception e) {
             logger.error(e);
             return false;
         }
         return true;
     }
     
     public static boolean isBlank(String string) {
         if (string == null || string.length() == 0 || string.trim().equals("")) {
             return true;
         }
         return false;
     }  
     
     public static void copyFile(File s, File t) {
         FileInputStream fi = null;
         FileOutputStream fo = null;
         FileChannel in = null;
         FileChannel out = null;
         try {
             fi = new FileInputStream(s);
             fo = new FileOutputStream(t);
             in = fi.getChannel();//得到对应的文件通道
             out = fo.getChannel();//得到对应的文件通道
             in.transferTo(0, in.size(), out);//连接两个通道，并且从in通道读取，然后写入out通道
         } catch (IOException e) {
             logger.error(e);
         } finally {
             try {
                 if(fi!=null){fi.close();}
                 if(in!=null){in.close();}
                 if(fo!=null){fo.close();}
                 if(out!=null){out.close();}
             } catch (IOException e) {
                 logger.error(e);
             }
         }
     }
     public static void main(String args[]){
     	//测试 
//     	ClipImageTool.clipImage("D:\\IMG_2396.JPG",1000,500,500,500,0.5,0.5,"D:\\IMG_new7.jpg");
      
 //  		ImageTool.scaleImage("F:\\temp\\cliptImgtest\\1200_1600.jpg",150,200,"F:\\temp\\cliptImgtest\\150_200.jpg");
 // 		ImageTool.scaleImage("F:\\temp\\cliptImgtest\\1200_1600.jpg",300,400,"F:\\temp\\cliptImgtest\\300_400.jpg");
 // 		ImageTool.scaleImage("F:\\temp\\cliptImgtest\\1200_1600.jpg",600,800,"F:\\temp\\cliptImgtest\\600_800.jpg");
  		
//  		ImageTool.reduceImg("F:\\temp\\cliptImgtest\\600_800.jpg", 600, 800,"F:\\temp\\cliptImgtest\\600_800new.jpg");
  		try {
			//ImageTool.resize("F:\\temp\\cliptImgtest\\1200_1600.jpg", 600,1,"F:\\temp\\cliptImgtest\\600_800.jpg");
			//ImageTool.resize("F:\\temp\\cliptImgtest\\1200_1600.jpg", 300,1,"F:\\temp\\cliptImgtest\\300_400.jpg");
			//ImageTool.resize("F:\\temp\\cliptImgtest\\1200_1600.jpg", 150,1,"F:\\temp\\cliptImgtest\\150_200.jpg");
//  			ImageTool.compressJpg4Web("F:\\aa.jpg","F:\\aas.jpg");
  			download("http://diy.deelkall.com/shirt3/upimg/2015120103814.jpg", "2015120103814.jpg","D:/workspacewt/lht_12_img_web/WebContent/diy");  
		} catch (Exception e) {
			logger.error(e);
		}
      } 
     
     
     public static void download(String urlString, String filename,String savePath) throws Exception {  
         // 构造URL  
         URL url = new URL(urlString);  
         // 打开连接  
         URLConnection con = url.openConnection();  
         //设置请求超时为5s  
         con.setConnectTimeout(5*1000);  
         // 输入流  
         InputStream is = con.getInputStream();  
       
         // 1K的数据缓冲  
         byte[] bs = new byte[1024];  
         // 读取到的数据长度  
         int len;  
         // 输出的文件流  
        File sf=new File(savePath);  
        if(!sf.exists()){  
            sf.mkdirs();  
        }  
        OutputStream os = new FileOutputStream(sf.getPath()+"\\"+filename);  
         // 开始读取  
         while ((len = is.read(bs)) != -1) {  
           os.write(bs, 0, len);  
         }  
         // 完毕，关闭所有链接  
         os.close();  
         is.close();  
     }   
   
} 