package lht.wangtong.core.utils.entity;

import java.util.Date;


/**
 * 实体类的基类，每一个有 ID(long型)的，都有一个 UUID(String(32))的字段
 * 
 * 2011-05-16 移到 core 工程下,各个业务entity要引用该 core 工程
 *  
 * @author aibo zeng
 */

public interface BaseEntity extends java.io.Serializable{
	public long getId();
	public void setId(long id);	
	public void setCreatedDate(Date createdDate);
	public Date getCreatedDate();
	public Long getCreatorId() ;
	public void setCreatorId(Long creatorId);
	public String getCreatorName();
	public void setCreatorName(String creatorName);
	public Date getModiDate() ;
	public void setModiDate(Date modiDate) ;
	public Long getModifierId();
	public void setModifierId(Long modifierId);
	public String getModifierName() ;
	public void setModifierName(String modifierName);
	public void setOwnerId(Long ownerId);
	public Long getOwnerId();
	
}
