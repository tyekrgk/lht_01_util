package lht.wangtong.core.utils.email;

import lht.wangtong.core.utils.vo.BaseVO;

@SuppressWarnings("serial")
public class MailReceiver  extends BaseVO  {

	private String receiverNm;
	private String receiverMail;
	
	public MailReceiver(String receiverNm, String receiverMail){
		this.receiverMail = receiverMail;
		this.receiverNm = receiverNm;
	}
	
	public MailReceiver( String receiverMail){
		this.receiverMail = receiverMail;
		this.receiverNm = "";
	}
	public String getReceiverNm() {
		return receiverNm;
	}

	public void setReceiverNm(String receiverNm) {
		this.receiverNm = receiverNm;
	}

	public String getReceiverMail() {
		return receiverMail;
	}

	public void setReceiverMail(String receiverMail) {
		this.receiverMail = receiverMail;
	}

}
