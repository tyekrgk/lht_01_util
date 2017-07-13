package lht.wangtong.core.utils.lang;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

/**
 * 2009-12-02
 * @author liyoulong 
 *
 */
public class ListUtils {
	public static Logger logger = Logger.getLogger(ListUtils.class.getClass());
	/**
	 * 传入一个list 得到一个它的随机排列的list 
	 * @param oldList 原始的list
	 * @param i 要得到的list的大小，如果i大于原始的list的size，或者小于等于0，在返回原始list长度的新list
	 * @return List<T>
	 */
	 public static <T> List<T>  randomList (List<T> oldList,int i) {
		if (oldList == null || oldList.size() == 0){
			return null;
		}
		int listSize = oldList.size();
		int whileVale;
		if (i <= 0 || i > listSize){
			whileVale = listSize;
		} else {
			whileVale = i;
		}
		List<T> newList = new ArrayList<T>();
		Random r = new Random();
		while (whileVale > 0) {
			int a = r.nextInt(listSize);
			int t = 0;
			if (newList.size() != 0)
				t = r.nextInt(newList.size());
			newList.add(t,oldList.get(a));
			oldList.remove(a);				
			whileVale --;
			listSize -- ;
		}
		return newList;
	}
	
	public static void main(String[] args) {
		List<Integer> list = new ArrayList<Integer>();
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		list.add(5);
		list.add(6);
		list.add(7);
		List<Integer> newList = ListUtils.randomList(list, 7);
		for (Integer i : newList){
			logger.debug(i);
		}
	}

}
