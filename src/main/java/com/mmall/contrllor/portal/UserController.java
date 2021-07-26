package com.mmall.contrllor.portal;

import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
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

        @Autowired

        private IUserService iUserService;
    /**
     *
     * @param username
     * @param password
     * @param session
     * @return
     */
        @RequestMapping(value = "login.do",method = RequestMethod.POST)
        @ResponseBody
        public ServerResponse<User> login(String username, String password, HttpSession session){
            ServerResponse<User> response = iUserService.login(username,password);
            if(response.isSuccess()){
                session.setAttribute(Const.CURRENT_USER,response.getData());
            }
            return response;
        }
}
