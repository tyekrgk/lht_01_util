package lht.wangtong.core.utils.web;

import javax.servlet.http.HttpSession;

import lht.wangtong.core.utils.exception.GenericBusinessException;
import lht.wangtong.core.utils.vo.LoginUser;

/**
 * 获取Session的接口
 * 实现类在  spring xml 中配置。
 * 
 * 注：必须要求浏览器支持 cookie，存放关键的标识  uid 登录用户的登录名 , token 登录令牌
 *     会话管理中存放 安全性要求比较高的 权限、销售组织结构等信息。
 * @author aibozeng
 *
 */
public interface SessionService {
	
	/**
	 * 从会话中获取当前 LoginUser , 获取的过程中设置了最后访问的 url ,ip ，访问日期等
	 * @param session
	 * @return 如果为空,自动创建一个LoginUser对象出来,注:新创建的，不放入缓存中
	 * @throws GenericBusinessException
	 */
    public LoginUser getLoginUser(HttpSession session) throws GenericBusinessException;

    /**
     * 保存 新的loginUser对象 到 缓存或Session中 (同时设置了最后访问的 url ,ip ，访问日期)
     * @param request
     * @param loginUser 放入缓存中
     * @throws GenericBusinessException 如果 没有登录，不放入
     */
    public void putLoginUser(HttpSession session,LoginUser loginUser  ) throws GenericBusinessException;
    
    public void removeLoginUser(HttpSession session);
    
    /**
     * 初始化方法
     * 对于Cache，则需要启动cache引擎
     */
    public void init();
    
    /**
     * 对于cache,可以停止cache引擎
     */
    public void stop();
    
    /**
     * 剔除用户
     * 场景: 管理员手工踢人,业务服务端手动替人
     * @param id
     */
	void b2bKickOut(Long id);

	/**
	 * session超时，自动退出
	 * @param id
	 */
	void b2bAutoOut(Long id,String sessionId);
	
	/**
	 * applicationContext-jpa.xml 里注入
	 */
	void setOwnerId(Long ownerId);
	Long getOwnerId();

}
