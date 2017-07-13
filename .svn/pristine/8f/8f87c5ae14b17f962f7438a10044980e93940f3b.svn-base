package lht.wangtong.core.utils.email;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.SendFailedException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.mail.internet.MimeUtility;

import org.apache.log4j.Logger;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.sun.mail.smtp.SMTPAddressFailedException;

/**
 * 工具类
 * 扩展 Spring提供的 JavaMailSenderImpl 用于获取 Transport 通道后 保持，能够发送大量邮件
 * 
 * 配置信息不再来源于: spring xml , 而是数据库。因为不同的项目，采用的邮件服务器地址不一样。
 * 
 * 有些邮件系统会拒绝使用:  553 this mail is rejected by antispam system, visit http://61.140.60.21/cgi-bin/falsecomplain?session_id=free-smtp-cdn-netcom1.26591.1305790922E&language=gb2312
 *  
 *                        535 5.7.8 authentication failed    重新设置一下用户名密码,smtp设置要保持和你的邮箱一致.
 *                        501 mail from address must be same as authorization user 发送人跟配置的邮箱帐户一致。
 * @author abio zeng
 * 
 */

public class JavaMailsenderImplEx extends JavaMailSenderImpl{
	public Logger logger = Logger.getLogger(this.getClass());

	public JavaMailsenderImplEx() {
		super();
	}



	/**
	 * 打开一个发邮件的通道 适用于大量发送邮件
	 * 
	 * @return
	 * @throws MessagingException
	 */
	public Transport newTransport() {
		Transport transport=null;
		try {
			transport = getSession().getTransport(getProtocol());
			transport.connect(getHost(), getPort(), getUsername(),
					getPassword());
			return transport;
		} catch (NoSuchProviderException e) {
			logger.error(e);
			
		} catch (MessagingException e2) {
			logger.error(e2);
		}
		finally
		{
			try {
				transport.close();
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				logger.error(e);
			}
		}
		return null;
	}

	/**
	 * 关闭一个发邮件的通道 适用于大量发送邮件
	 * 
	 * @return
	 */
	public void closeTransport(Transport transport) {
		try {
			transport.close();
		} catch (MessagingException e) {
			logger.error(e);
		}
	}

	/**
	 * 根据制定的通道，发送一个邮件 适用于小量发送邮件
	 * 
	 * @param mimeMessages
	 * @param transport
	 */
	public void sendMail(MimeMessage mimeMessage, Transport transport) {
		try {
			if (mimeMessage.getSentDate() == null) {
				mimeMessage.setSentDate(new Date());
			}
			String messageId = mimeMessage.getMessageID();
			mimeMessage.saveChanges();
			if (messageId != null) {
				// Preserve explicitly specified message id...
				mimeMessage.setHeader("Message-ID", messageId);
			}
			transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
		} catch (MessagingException ex) {
			logger.error(ex);
		}
	}

	/**
	 * 根据制定的通道，批量发送邮件 适用于大量发送邮件
	 * 
	 * @param mimeMessages
	 * @param transport
	 */
	public void sendMail(MimeMessage[] mimeMessages, Transport transport) {
		try {
			for (int i = 0; i < mimeMessages.length; i++) {
				sendMail(mimeMessages[i], transport);
			}
		} finally {
		}

	}

	/**
	 * 快速发送一个文本邮件 
	 * 适应：测试场合
	 * 
	 * @param fromAddr
	 *            发件人地址
	 * @param toAddr
	 *            收信人地址
	 * @param subject
	 *            标题
	 * @param content
	 *            内容
	 * @return
	 */
	public boolean sendText(String fromAddr, String toAddr, String subject,
			String content) {
		// 建立邮件消息
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		// 设置收件人，寄件人
		mailMessage.setTo(toAddr);
		mailMessage.setFrom(fromAddr);
		mailMessage.setSubject(subject);
		mailMessage.setText(content);
		// 发送邮件
		try {
			logger.debug("邮件开始发送.....");
			send(mailMessage);
			logger.debug("邮件发送成功.....");
		} catch (Exception e) {
			logger.error(e);
			return false;
		}
		return true;
	}
	
	private Address[] getMailReceivers(List<MailReceiver> toReceivers) throws UnsupportedEncodingException,Exception{
		Address[] ads=null;
		if(toReceivers==null){
			return ads;
		}
		List<Address> addrs = new ArrayList<Address>();
		for(int i=0;i<toReceivers.size();i++){
			MailReceiver mr=toReceivers.get(i);
			if(mr==null || mr.getReceiverMail()==null){
				continue;
			}
			String email = mr.getReceiverMail();
			if(email==null || "".equals(email) || email.indexOf("@")<=0 ){
				continue;
			}
			try{
			    addrs.add(new InternetAddress(email,mr.getReceiverNm(),"utf-8"));
			}catch(Exception e){
				//log.error("转换为邮件地址对象（new InternetAddress）错误: email="+email+",mr.getReceiverNm()="+mr.getReceiverNm());
				logger.error(e);
			}
		}
		if(addrs==null ||addrs.size()<=0 ){
			return ads;
		}
		ads = new Address[addrs.size()];
		for(int j=0;j<addrs.size();j++){
			ads[j] = (Address)addrs.get(j);
		}		
		return ads;
	}
	
	public String send(MailContext mailContext) throws Exception{
		if(mailContext==null){
			logger.error("send(MailContext mailContext) mailContext is null");
			return "fail";
		}
		MimeMessage mime =createMimeMessage();   
        MimeMessageHelper helper;   
        
        //当收件人列表为空，转发给系统管理员，同时加上警告信息
        String warnMsg = ""; 
        try {
        	Address[] receivers = getMailReceivers(mailContext.getToReceivers());
        	if(receivers==null){
        		//mime.setRecipient(RecipientType.TO, getSystemAddress());
        		warnMsg = "[收件人列表为空或无效]";
        	}
        	else{
               mime.setRecipients(RecipientType.TO, receivers);
        	}
        	Address[] receiversCC = getMailReceivers(mailContext.getCcReceivers());
        	if(receiversCC!=null){
               mime.setRecipients(RecipientType.CC, receiversCC);
        	}
        	Address[] receiversBCC = getMailReceivers(mailContext.getBccReceivers());
        	if(receiversBCC!=null){
               mime.setRecipients(RecipientType.BCC, receiversBCC);
        	}
            //501提示  getUsername()就是发生帐户
            mime.setFrom(new InternetAddress(getUsername(),mailContext.getSenderNm(),"utf-8"));
         
            helper = new MimeMessageHelper(mime,true,"utf-8");

            helper.setSubject(mailContext.getSubject()+warnMsg);  

            mime.setContent(mailContext.getText(), "text/html; charset=UTF-8");

            if(mailContext.getAttachs()!=null && mailContext.getAttachs().size()>0){
            	for(MailAttach attachfile:mailContext.getAttachs()){
            		helper.addAttachment(
            				MimeUtility.encodeText(
            						attachfile.getFileName(),
            						"UTF-8",
            						"B"
            					), 
            					attachfile.getFile());
            	}
            }
            //log.info("begin to send to mail:"+receivers.toString());
            send(mime);  
            //log.info("end to send mail.");
            return "success";
        }catch(UnsupportedEncodingException e){
        	logger.error(e);
        	//log.error("UnsupportedEncodingException="+ExceptionHelper.getStackTraceAsString(e));
        	return "fail";
        } 
		catch(SMTPAddressFailedException smtpfe){
			logger.error(smtpfe);
        	//log.error("SMTPAddressFailedException="+ExceptionHelper.getStackTraceAsString(smtpfe));
        	return "fail";
			
		}
		catch(SendFailedException sfe){							
			logger.error(sfe);
        	//log.error("SendFailedException="+ExceptionHelper.getStackTraceAsString(sfe));
        	return "fail";
		}
        catch (MessagingException e) {
        	logger.error(e);
            //log.error("MailSenderException="+ExceptionHelper.getStackTraceAsString(e));
            return "fail";
        }   
        catch (MailException e) {   
        	logger.error(e);
            //log.error("MailException="+ExceptionHelper.getStackTraceAsString(e));
            return "fail";
        }  
        catch (Exception e) {   
        	logger.error("send(MailContext mailContext)",e);
            //log.error("Other Exception="+ExceptionHelper.getStackTraceAsString(e));
            return "fail";
        }  
        

	}	
	
	public static void main(String[] args){
		JavaMailsenderImplEx mail = new JavaMailsenderImplEx();
		mail.setHost("smtp.exmail.qq.com");
		//mail.setPort(465);465是SSL. 普通端口是25
		mail.setUsername("futianlin@lvhetao.com");//仅供测试
		mail.setPassword("ftl123$");//仅供测试
		Properties prop = new Properties();
		prop.setProperty("mail.smtp.auth", "true");
		mail.setJavaMailProperties(prop);
		mail.sendText("futianlin@lvhetao.com","zengyubo@lvhetao.com","2014年8月中旬绿核桃三周年庆-补充","可带家属,这是测试的邮件，请不要相信里面的内容！谢谢。");
	}
	

}
