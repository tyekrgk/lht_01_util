package lht.wangtong.core.utils.str;

/**
 * 针对json字符串,进行转义，让其符合规范
 * @author zengyubo
 *
 */
public class JsonUtil {
	
	/**
	 * 把里面的双引号换为单引号
	 * @param valueStr " i say:"hello" "
	 * @return  " i say:'hello' "
	 */
	public static String parseDoubleQuo(String valueStr){
		return valueStr.replace("\"", "'");
	}
	
	/**
	 * 把里面的单引号换为双引号
	 * @param valueStr " i say:'hello' "
	 * @return  " i say:"hello" "
	 */
	public static String parseSingleQuo(String valueStr){
		return valueStr.replace("'", "\"");
	}	

	/**
	 * 把里面的单引号 双引号 换为 空格
	 * @param valueStr " i say:'hello' "
	 * @return  " i say: hello  "
	 */
	public static String parseIgnoreQuo(String valueStr){
		String newStr= valueStr.replace("'", " ");
		return newStr.replace("\""," ");
	}	
	
	/**
	 * 处理属性值里面有双引号为空格
	 * 把对方给的整个json字符串进行检查是否符合规范
	 * 应用场景：对方没有按规定给出 " i say:"hello" ", 某个属性值里有引号，造成我们解析出错
	 * 要求，必须使用""作为属性名和属性值，不要使用单引号。本方法只处理属性值里面有双引号 或双引号＋逗号
	 * @param s
	 * @return
	 */
	public static String parseToOK(String s){
        char[] temp = s.toCharArray();       
        int n = temp.length;
        for(int i =0;i<n;i++){
            if(temp[i]==':'){
            	if(i>n-4){
            		//最后至少有三个字符,否则整个不合法
            		return "";
            	}
                if( temp[i+1]=='"'){
                	//合法双引号开始
                    for(int j=i+2;j<n-1;j++){
                        if( temp[j]=='"'){
                           if(temp[j+1]=='}'){
                        	  //合法的,跳出继续找冒号:
                        	  break;
                            }else if(temp[j+1]==','){
                            	if(temp[j+2]=='"'){
                            		//合法的,跳出继续找冒号:
                            		break;
                            	}else{
                            		//不合法的，换为空格
                            		temp[j]=' ';
                            	}
                            }else{
                            	//其余情况都是不合法的，换为空格
                        		temp[j]=' ';
                            }
                        }
                    }   
               }
            }
        }   
        return new String(temp);
    }
	
	public static void main(String[] args) {
		//里面有孤独的双引号
        System.out.println(JsonUtil.parseToOK("{\"id\":\"i have \"OK?\"}"));
      //里面有孤独的双引号+有欺骗性的单引号
        System.out.println(JsonUtil.parseToOK("{\"id\":\"i have \",OK?\"}"));
      //里面有合法的单引号
        System.out.println(JsonUtil.parseToOK("{\"id\":\"i have',OK?\"}"));
	}

}
