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



        @RequestMapping(value = "logout.do",method = RequestMethod.POST)
        @ResponseBody
        //第二个功能，登出功能
    public ServerResponse<String> logout(HttpSession session){
        session.removeAttribute(Const.CURRENT_USER);
        return ServerResponse.createBySuccess();
    }


    //注册接口
    @RequestMapping(value = "register.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> register(User user){
        return iUserService.register(user);
    }


    //校验接口
    @RequestMapping(value = "checkvalid.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> checkValid(String str,String type){
    return iUserService.checkValid(str,type);
    }


    //获取用户信息
    @RequestMapping(value = "get_user_info.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<User> getUserInfo(HttpSession session){
            User user = (User) session.getAttribute(Const.CURRENT_USER);
            if(user!=null){

                return ServerResponse.createBySuccess(user);
            }
        return ServerResponse.createByErrorMessage("用户未登录，无法获取当前用户的信息");

    }

    //查询密码提示问题
    @RequestMapping(value = "get_user_question.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> forgetGetQuestion(String username){
            return iUserService.selectQuestion(username);
    }
    //查询问题答案
    @RequestMapping(value = "get_check_answer.do",method = RequestMethod.GET)
    @ResponseBody

    public ServerResponse<String> forgetCheckAnswer(String username,String question,String answer){
return iUserService.checkAnswer(username,question,answer);
    }

    //忘记密码
    @RequestMapping(value = "forget_reset_password.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> forgetResetPassword(String username,String password,String forgetToken){
            return iUserService.forgetResetPassword(username,password,forgetToken);

    }
}
