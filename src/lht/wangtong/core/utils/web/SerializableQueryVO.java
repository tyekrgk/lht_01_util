package lht.wangtong.core.utils.web;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * 
 *  @author fengjian1
 *  实现查询条件VO对象的序列化
 *  1.每个有查询功能的模块的查询条件分别序列化到自己的二进制文件中
 *    文件命名规则    模块名称.dat
 *  2.同一个查询模块的查询条件如果保存多次序列化到同一个文件中用数组保存
 *  文件保存路径：  
 *  1.先读取web-inf 下配置文件(lht.properties) queryVoPath
 *   如果文件不存在或者没有配置路径属性 则文件默认存放路径
 *    System.getProperty("user.home")+img/
 */
public class SerializableQueryVO {
	private static final String FILE_PATH = System.getProperty("user.home");
	private static final String IMG = "img";
	private String realfilePath;
	private static SerializableQueryVO instance = null;
	
	private SerializableQueryVO() {
	//	if(!PropertyUtils.isfileExists()||PropertyUtils.getProperty("queryVoPath")==null){
			realfilePath=FILE_PATH+File.separator+IMG;//+File.separator;
	//	}else{
		//	realfilePath=PropertyUtils.getProperty("queryVoPath");
	//	}
			File f = new File(realfilePath);
			if(!f.exists()){
				f.mkdirs();
			}
	}
	
	public static SerializableQueryVO getInstance() {
		if (instance == null) {
			instance = new SerializableQueryVO();
		}
		return instance;
	}
	
	public boolean toSave(Object vo,String filenName) throws Exception{
		Object voValue[]=toLoad(filenName);
		File f = new File(realfilePath +File.separator+ filenName);
		ObjectOutputStream oos = null;
		if(voValue==null){//第一次保存
			OutputStream out = new FileOutputStream(f);
			oos = new ObjectOutputStream(out);
			oos.writeObject(new Object[]{vo});
			oos.close();
			return true;
		}else {
			Object[] object=new Object[voValue.length+1];
			for(int i=0;i<voValue.length;i++){
				object[i]=voValue[i];
			}
			object[voValue.length]=vo;
			OutputStream out = new FileOutputStream(f);
			oos = new ObjectOutputStream(out);
			oos.writeObject(object);
			oos.close();
			return true;
		}
	}
	
	
	public Object[] toLoad(String filenName) throws Exception{
		File f = new File(realfilePath +File.separator+ filenName);
		if (!f.exists()) {
			return null;
		}
		ObjectInputStream ois = null;
		InputStream ipt = new FileInputStream(f);
		ois = new ObjectInputStream(ipt);
		Object obj[] = (Object[]) ois.readObject();
		ois.close();
		return obj;
	}
	
	
}
