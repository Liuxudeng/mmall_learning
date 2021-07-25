package com.mmall.contrllor.portal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 *
 */
//在class上面添加注解
    @Controller
    //将请求地址全部链接到user下面
    @RequestMapping("/user/")
public class UserController {
    /**
     *
     * @param username
     * @param password
     * @param session
     * @return
     */
        @RequestMapping(value = "login.do",method = RequestMethod.POST)
        @ResponseBody
        public Object login(String username, String password, HttpSession session){
            //service-->mybatis-->dao


            return null;
        }
}
