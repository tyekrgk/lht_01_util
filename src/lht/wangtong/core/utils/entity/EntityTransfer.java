package lht.wangtong.core.utils.entity;

import java.util.Date;

import lht.wangtong.core.utils.vo.LoginUser;


/**
 * 实体与 VO转化工具类
 * @author aibozeng
 *
 */

public class EntityTransfer {

	/**
	 * 从登录用户信息，取出6个常用数据(创建人，修改人信息)，赋予当前新建的实体对象
	 * @see BaseBOBean 的部分方法已经调用该方法
	 * @param u
	 * @param e
	 */
	public static void toBaseEntity(LoginUser u , BaseEntity e){
		if(u!=null){
			if(e.getCreatedDate()==null || e.getCreatorId()==null){
				e.setCreatedDate(new Date());
				e.setCreatorId(u.getId());
			}
			if(e.getCreatorName()==null){
				e.setCreatorName(u.getUserNm());
			}
			
			//2015-11-07 aibozeng 如果原有实体的wonerId已经有值，则不用改变。菜单实体本身绑定不同的子系统的ownerId
			if(e.getOwnerId()==null || e.getOwnerId().longValue()==0){
				if(u.getOwnerId()!=null && u.getOwnerId().longValue()>0){
					e.setOwnerId(u.getOwnerId());
				}else{
					e.setOwnerId(1L);
				}				
			}
			e.setModiDate(new Date());
			e.setModifierId(u.getId());
			e.setModifierName(u.getUserNm());
		}
	}
	
	public static void toBaseEntity_F(LoginUser u , BaseEntity e){
		if(u!=null){
			if(u.getOwnerId()==null || u.getOwnerId()==0){
				e.setOwnerId(1l);
			}else{
				e.setOwnerId(u.getOwnerId());
			}
			e.setModiDate(new Date());
			e.setModifierId(u.getId());
			e.setModifierName(u.getUserNm());
		}
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
