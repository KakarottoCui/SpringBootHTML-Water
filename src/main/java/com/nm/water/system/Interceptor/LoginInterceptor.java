package com.nm.water.system.Interceptor;
import com.nm.water.service.MainService;
import com.nm.water.system.utils.SessionUtils;
import com.nm.water.system.utils.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor{

    public MainService mainService;

    public LoginInterceptor(MainService mainService){
        this.mainService = mainService;
    }
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        String token = request.getHeader("token");
        if(StringUtils.isNotEmpty(token)){
           return mainService.setLogin(token,request);
        }else{
            SessionUtils.removeSessionAttribute("user");
            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView) throws Exception {


    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) throws Exception {


    }
}
