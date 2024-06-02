package com.ichirinko.luce.utils;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.core.util.Json;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.Console;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName JWTInterceptor
 * @Description
 * @Author Ichirinko
 * @Date 2024/6/1 21:21
 * @Version 1.0
 **/
public class JWTInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 创建一个Map对象，用于存储响应信息
        Map<String, Object> map = new HashMap<>();

        // 从请求头中获取令牌
        String token = request.getHeader("Authorization");

        try {
            if (token != null && token.startsWith("Bearer ")) {
                // 移除"Bearer "前缀，得到实际的token值
                token = token.substring(7);
                JWTService.verify(token); // 验证令牌的有效性
                return true; // 放行请求
            } else {
                throw new Exception("不是有效token");
            }
        } catch (SignatureVerificationException e) {
            e.printStackTrace();
            map.put("msg", "无效签名");
        } catch (TokenExpiredException e) {
            e.printStackTrace();
            map.put("msg", "token过期");
        } catch (AlgorithmMismatchException e) {
            e.printStackTrace();
            map.put("msg", "token算法不一致");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("msg", "token无效");
        }

        map.put("state", false); // 设置状态为false

        // 将Map转化为JSON字符串（使用Jackson库）
        String json = new ObjectMapper().writeValueAsString(map);

        response.setContentType("application/json;charset=UTF-8"); // 设置响应的Content-Type
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().println(json); // 将JSON字符串写入响应中

        return false; // 不放行请求
    }
}
