package cn.kungreat.flybbs.util;

import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class UserContext {
	private UserContext(){}
	
	public static HttpSession getSession(){
		RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
		ServletRequestAttributes ServletRequestAttributes = (ServletRequestAttributes) attributes;
		return ServletRequestAttributes.getRequest().getSession();
	}
	
	public static void setCurrentName(String currentName){
		if (currentName != null){
			getSession().setAttribute("KUN_CURRENT_NAME",currentName);
		}
	}
	
	public static String getCurrentName(){
		Object obj = getSession().getAttribute("KUN_CURRENT_NAME");
		if(obj != null){
			return obj.toString();
		}
		return "";
	}
}