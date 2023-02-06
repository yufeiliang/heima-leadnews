package com.heima.wemedia.interceptor;

import com.alibaba.fastjson.JSON;
import com.heima.model.wemedia.pojo.WmUser;
import com.heima.utils.common.AppJwtUtil;
import com.heima.utils.thread.WmThreadLocalUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Component
@Slf4j
public class WmTokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        if (StringUtils.isNotBlank(token)) {
    //        log.info("========================");
            String requestURI = request.getRequestURI();
            log.info("地址为==>{}",requestURI);
            Claims claims = AppJwtUtil.getClaimsBody(token);
           // WmUser wmUser = JSON.parseObject(claims.get("id").toString(), WmUser.class);
            Integer id = (Integer) claims.get("id");
            WmUser wmUser = new WmUser();
            wmUser.setId(id);
            WmThreadLocalUtil.setUser(wmUser);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        WmThreadLocalUtil.clear();
    }
}
