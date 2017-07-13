package lht.wangtong.core.utils.web;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import lht.wangtong.core.utils.vo.SaveVO;

public class SerializableUtils {
	private static SerializableUtils instance = null;
	private SerializableUtils() {
	}
	public static SerializableUtils getInstance() {
		if (instance == null) {
			instance = new SerializableUtils();
		}
		return instance;
	}
	public String  deleteAll(String path,String filenName)throws Exception{
		File f = new File(path+File.separator+ filenName);
		if (!f.exists()) {
			return "-1";
		}
		f.delete();
		return "1";
	}
	
	public String  deleteByCode(String path,String filenName,String code)throws Exception{
		File f = new File(path+File.separator+ filenName);
		ObjectOutputStream oos = null;
		if (!f.exists()) {
			return "-1";
		}
		Object voValue[]=toLoad(path,filenName);
		if(voValue==null||voValue.length<1){
			return "-1";
		}
		if(voValue.length==1){
			return deleteAll(path,filenName);
		}
		SaveVO svo;
		Object newVoValue[]= new Object[voValue.length-1];
		int j=0;
		for(int i=0;i<voValue.length;i++){
			svo=(SaveVO)voValue[i];
			if(!svo.getCode().equals(code)){
				newVoValue[j++]=voValue[i];
			}
		}
		OutputStream out = new FileOutputStream(f);
		oos = new ObjectOutputStream(out);
		oos.writeObject(newVoValue);
		oos.close();
		return "1";
	}
	
	
	public boolean toSave(Object vo,String path,String filenName) throws Exception{
		Object voValue[]=toLoad(path,filenName);
		File f =null;
		f=new File(path);
		if(!f.exists()){
			f.mkdirs();
		}
		f = new File(path +File.separator+ filenName);
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
	
	
	public Object[] toLoad(String path,String filenName) throws Exception{
		File f = new File(path+File.separator+ filenName);
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
