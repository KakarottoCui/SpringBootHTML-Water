package com.nm.water.system.utils;

import com.nm.water.pojo.SysUser;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SessionUtils {

    public static RequestAttributes getAttributes(){
        return RequestContextHolder.currentRequestAttributes();
    }

    public static HttpServletRequest getRequest(){
        return ((ServletRequestAttributes)getAttributes()).getRequest();
    }

    public static HttpServletResponse getResponse(){return ((ServletRequestAttributes)getAttributes()).getResponse();}

    public static HttpSession getSession(){
        return getRequest().getSession();
    }

    public static void addSessionAttribute(String key,Object valueObject){getSession().setAttribute(key,valueObject);}

    public static void removeSessionAttribute(String key){getSession().removeAttribute(key);}

    public static void clearSession(){getSession().invalidate();}

    public static SysUser getUser(){
        return (SysUser)getRequest().getSession().getAttribute("user");
    }

    public static String getUserName(){return getUser().getNickName();}

    public static Integer getUserId(){
        return getUser().getUid();
    }

    public static String getHeader(String key){
        return getRequest().getHeader(key);
    }

}
