package lht.wangtong.core.utils.email;

import java.io.File;

import lht.wangtong.core.utils.vo.BaseVO;

@SuppressWarnings("serial")
public class MailAttach  extends BaseVO  {

	private  File file;
	private  String fileName;
	
	public MailAttach(File file,String fileName) {
		this.file=file;
		this.fileName=fileName;
	}
	public MailAttach() {
		
	}


	public File getFile() {
		return file;
	}


	public void setFile(File file) {
		this.file = file;
	}


	public String getFileName() {
		return fileName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
