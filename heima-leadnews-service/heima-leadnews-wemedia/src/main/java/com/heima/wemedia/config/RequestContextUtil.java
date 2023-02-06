package com.heima.wemedia.config;

import com.heima.utils.common.AppJwtUtil;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/****
 * 用来获取用户请求信息
 */
public class RequestContextUtil {

    /****
     * 获取请求头   token  HttpServletRequest
     */
    public static String getHeader(String key){
        //RequestContextHolder 获取当前请求的请求封装对象和响应封装对象
        //1)获取HttpServletRequest
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        //2)通过HttpServletRequest获取指定请求头
        return request.getHeader(key);
    }

    /***
     * 获取token
     */
    public static Map<String,Object> token(){
        String token = getHeader("token");
        return AppJwtUtil.getClaimsBody(token);
    }

    /***
     * 获取token指定的key
     */
    public static <T>T get(String key){
        Map<String, Object> token = token();
        return (T) token.get(key);
    }

}
