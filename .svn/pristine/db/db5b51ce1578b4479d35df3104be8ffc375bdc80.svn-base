package lht.wangtong.core.utils.engine;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.script.Bindings;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.log4j.Logger;

/**
 * JavaScript 引擎
 * 基于JDK提供的JavaScript执行引擎实现
 * @author lyl
 *
 */
public class ScriptEngineManagerTool {
    public static ScriptEngineManager manager = new ScriptEngineManager(); 
    public static Logger logger = Logger.getLogger(ScriptEngineManagerTool.class.getClass());

    public ScriptEngineManagerTool(){    	
    }
    
    //JDK6版本则默认具有Javascript引擎支持，使用Javascript作为动态语言的话不需要加载额外的jar文件
    public static void execScript(Map<? extends String, ? extends Object> map , String script){
    	ScriptEngine engine = manager.getEngineByName("JavaScript");
    	Bindings bindings = engine.createBindings();
    	bindings.putAll(map);
    	try {
			engine.eval(script,bindings);
			//return obj;
		} catch (ScriptException e) {
			logger.error("ScriptException in script:"+script);
			logger.info("Bindings key with:");
			Iterator<?> parameterKeys = map.entrySet().iterator();
			while(parameterKeys.hasNext()){
				String key = (String)parameterKeys.next();
				logger.info(key);
			}
			logger.error(e);
	    }catch(NullPointerException nulle){	
	    	logger.error(nulle);
		}
    	//return null;
    }
    
    public static Object execScriptFunction(String functionScriptCode ,String functionName , List<String> funcArgs) {  
        ScriptEngine engine=manager.getEngineByName("JavaScript");  
        //Bindings bindings = engine.createBindings();
    	//bindings.putAll(funcParam);
        try{   
        	Object obj = engine.eval(functionScriptCode);   
            Invocable invFunction = (Invocable) engine;   
             return invFunction.invokeFunction(functionName,funcArgs.toArray());   
        }   
        catch(Exception e){   
            logger.error(e);  
        }
        return "ERROR";
    }  
    
    
    public static Object execFreeMarker(Map<? extends String, ? extends Object> map , String script){
    	ScriptEngine engine = manager.getEngineByName("FreeMarker");
    	Bindings bindings = engine.createBindings();
    	bindings.putAll(map);
    	//需要引入FreeMarker类库 bindings.put(FreeMarkerScriptConstants.STRING_OUTPUT, Boolean.TRUE);
    	try {
			return engine.eval(script,bindings);
		} catch (ScriptException e) {
			logger.error(e);
	    }catch(NullPointerException nulle){		
		}
    	return null;
    }
    
}
