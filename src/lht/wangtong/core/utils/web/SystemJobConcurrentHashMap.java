package lht.wangtong.core.utils.web;

import java.util.concurrent.ConcurrentHashMap;

public class SystemJobConcurrentHashMap {

	public static ConcurrentHashMap<String, String> taskMap;

	public static ConcurrentHashMap<String, String> getTaskMap() {
		if (taskMap == null) {
			taskMap = new ConcurrentHashMap<String, String>();
		}
		return taskMap;
	}
}
