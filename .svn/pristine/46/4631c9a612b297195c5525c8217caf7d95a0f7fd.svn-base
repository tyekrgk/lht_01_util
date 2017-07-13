package lht.wangtong.core.utils.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lht.wangtong.core.utils.vo.LoginUser;

import org.apache.log4j.Logger;

public class ScriptEngineManagerToolTest {
	public static Logger logger = Logger.getLogger(ScriptEngineManagerToolTest.class.getClass());
	public ScriptEngineManagerToolTest(){		
	}

	//@Test
	public void test1() throws Exception {
		LoginUser loginUser = new LoginUser();
	    Map<String,Object> map = new HashMap<String,Object>();
	    map.put("loginUser",loginUser );
	    String script = " loginUser.setUserNm(\"超级周晓龙\"); ";
	    
	    ScriptEngineManagerTool.execScript(map, script);
	    
	    logger.debug(loginUser.getUserNm());	    
	}	
	
	public void test2() throws Exception{
		//获取模板  
		String template=" the 学生 is ${name} ,and 他今年${age}岁,身高:${height}cm!\n"+  
		        "他的朋友包括:\n"+  
		        "<#list friends as friend>朋友 ${friend}\n</#list>";  
		Map<String,Object> params = new HashMap<String,Object>();             	 
        params.put("name", "tom");  
        params.put("age", 11);  
        params.put("height", 175.2);  
        params.put("friends", new String[]{"jack","linda","better"});  
  
        //执行模板渲染  
        Object result = ScriptEngineManagerTool.execFreeMarker(params, template);
        //输出结果  
        logger.debug(result);  
	}
	
	//测试传递字符串数组, 成功!!!!!!!!---放入Map必须是 简单对象接口
	public void test3() throws Exception {
		TestInterface testVO = new TestVO();		
	    Map<String,Object> map = new HashMap<String,Object>();
	    map.put("testVO",testVO);
	    String script = " testVO.addSql(\"update sql\") ; testVO.addSql(\"update sql2\");";
	    ScriptEngineManagerTool.execScript(map,script);
	    if(testVO!=null){
		    TestVO vo = (TestVO)testVO;
	    	for(String s:vo.getSqls()){
	    	    logger.debug(s);	    	    		
	    	}
	    }
	}	
	
	//测试传参执行 js 自定义函数, 这段js在 html也能执行的
	public void testFunction() throws Exception {			
	    List<String> args = new ArrayList<String>();
	    String script = " function myfunction(p1,p2){ \n"
	    		       +"   return '123'+p1+p2;\n"
	    		       +" }\n";
	    args.add("ab");//函数的入参
	    args.add("cd");//函数的入参
	    logger.debug(script);
	    Object obj = ScriptEngineManagerTool.execScriptFunction(script, "myfunction", args);
	    if(obj!=null){		    
	       logger.debug(obj);	    	    		
	    }
	}	

	
	public final static  void main(String[] args){
		try {
			new ScriptEngineManagerToolTest().testFunction();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.debug(e);
		}
	}
}
