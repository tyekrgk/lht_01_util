package lht.wangtong.core.utils.email;

import java.util.ArrayList;
import java.util.List;

import lht.wangtong.core.utils.vo.BaseVO;

@SuppressWarnings("serial")
public class MailContext  extends BaseVO {

	private String subject;//标题
	private String text;   //内容
	private String senderMail=null;//发送人邮箱--必须跟邮箱配置的发信帐户一致。
	private String senderNm="admin";          //发送人姓名   
	private List<MailReceiver> toReceivers=new ArrayList<MailReceiver>(); //接收人

	@Deprecated
	private List<MailReceiver> ccReceivers=new ArrayList<MailReceiver>();//抄送	
	@Deprecated
	private List<MailReceiver> bccReceivers=new ArrayList<MailReceiver>();//暗送
	@Deprecated
	private List<MailAttach> attachs=new ArrayList<MailAttach>();//附件
	
	
	public List<MailAttach> getAttachs() {
		return attachs;
	}

	public void setAttachs(List<MailAttach> attachs) {
		this.attachs = attachs;
	}
	
	public void addAttach(MailAttach attach){
		attachs.add(attach);
	}
	
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getSenderMail() {
		return senderMail;
	}

	public void setSenderMail(String senderMail) {
		this.senderMail = senderMail;
	}

	public String getSenderNm() {
		return senderNm;
	}

	public void setSenderNm(String senderNm) {
		this.senderNm = senderNm;
	}

	public List<MailReceiver> getToReceivers() {
		return toReceivers;
	}

	public void setToReceivers(List<MailReceiver> toReceivers) {
		this.toReceivers = toReceivers;
	}
	
	/**
	 * 追加接收人
	 * @param rece
	 */
	public void addToReceiver(MailReceiver rece){
		toReceivers.add(rece);
	}
	
	/**
	 * 清空接收人列表
	 */
	public void clearToReceivers(){
		toReceivers.clear();
	}

	public List<MailReceiver> getCcReceivers() {
		return ccReceivers;
	}

	public void setCcReceivers(List<MailReceiver> ccReceivers) {
		this.ccReceivers = ccReceivers;
	}

	public List<MailReceiver> getBccReceivers() {
		return bccReceivers;
	}

	public void setBccReceivers(List<MailReceiver> bccReceivers) {
		this.bccReceivers = bccReceivers;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	/**
	 * 追加内容
	 */
	public void appendContent(String str){
		text = text + str;
	}
}
