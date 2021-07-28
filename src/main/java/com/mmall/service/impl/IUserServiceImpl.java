package com.mmall.service.impl;
import com.mmall.common.Const;
import com.mmall.common.TokenCache;
import com.mmall.util.MD5Util;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.dao.UserMapper;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public class IUserServiceImpl implements IUserService {

    //把mapper注入进来
    @Autowired
    private UserMapper userMapper;



    @Override
    public ServerResponse<User> login(String username, String password) {
        int resultCount = userMapper.checkUsername(username);

        if(resultCount==0){
            return ServerResponse.createByErrorMessage("用户名不存在");
        }
        //密码md5登录
        String md5Password = MD5Util.MD5EncodeUtf8(password);
        User user  = userMapper.selectLogin(username,md5Password);
        if(user == null){
            return ServerResponse.createByErrorMessage("密码错误");
        }

        user.setPassword(org.apache.commons.lang3.StringUtils.EMPTY);
        return ServerResponse.createBySuccess("登录成功",user);

    }

    @Override
    public ServerResponse<String> register(User user){
        //注册时校验用户名是否存在

//        int resultCount = userMapper.checkUsername(user.getUsername());
//        if(resultCount>0){
//            return ServerResponse.createByErrorMessage("用户名已经存在");
//        }


        ServerResponse validResponse = this.checkValid(user.getUsername(),Const.USERNAME);
        if(!validResponse.isSuccess()){
            return  validResponse;
        }
         validResponse = this.checkValid(user.getUsername(),Const.EMAIL);
        if(!validResponse.isSuccess()){
            return  validResponse;
        }

        //验证邮箱是否存在
//        resultCount = userMapper.checkEmail(user.getEmail());
//        if(resultCount>0){
//            return ServerResponse.createByErrorMessage("email已经存在");
//        }

        user.setRole(Const.Role.ROLE_CUSTOMER);

        //MD5加密
        user.setPassword(MD5Util.MD5EncodeUtf8(user.getPassword()));

   int     resultCount = userMapper.insert(user);

        if(resultCount==0){
            return ServerResponse.createByErrorMessage("注册失败");

        }

        return ServerResponse.createBySuccessMessage("注册成功");


    }

@Override
    public ServerResponse<String> checkValid(String str,String type){
        //如果type不为空，才开始校验
        if(org.apache.commons.lang3.StringUtils.isNoneEmpty(type)){
            //开始校验
            //先名字
            if(Const.USERNAME.equals(type)){
                int   resultCount = userMapper.checkUsername(str);
                if(resultCount>0){
                    return ServerResponse.createByErrorMessage("用户名已经存在");

                }
            }
            //再邮箱
            if(Const.EMAIL.equals(type)){
                int   resultCount = userMapper.checkUsername(type);
                if(resultCount>0){
                    return ServerResponse.createByErrorMessage("email已经存在");

                }
            }

        }else{
            return  ServerResponse.createByErrorMessage("参数错误");
        }

        return ServerResponse.createBySuccessMessage("校验成功");
    }

@Override
    public ServerResponse selectQuestion(String username){
        ServerResponse validResponse = this.checkValid(username,Const.USERNAME);
    if(validResponse.isSuccess()){
        //条件成立时，在checkvalid里用户名不存在，能够校验，但是在selectQuestion中用户名不存在时是失败
        return ServerResponse.createByErrorMessage("用户不存在");


    }

    String question = userMapper.selectQuestionByUsername(username);
    if(org.apache.commons.lang3.StringUtils.isNotBlank(question)){
        //如果不是空格，则说明存在找回密码的问题

        return ServerResponse.createBySuccess(question);
    }
    return ServerResponse.createByErrorMessage("找回密码为空");

    }

    @Override
    public  ServerResponse<String> checkAnswer(String username,String question,String answer){
        int resultCount = userMapper.checkAnswer(username,question,answer );{
            if(resultCount>0){
                String forgetToken = UUID.randomUUID().toString();

                TokenCache.setKey(TokenCache.TOKEN_PREFIX+username,forgetToken);
                return ServerResponse.createBySuccess(forgetToken);

            }

            return ServerResponse.createByErrorMessage("问题回答错误");
        }

    }




    @Override
    public ServerResponse<String> forgetResetPassword(String username,String passwordNew,String forgetToken) {
        if (org.apache.commons.lang3.StringUtils.isNoneEmpty(forgetToken)) {
            return ServerResponse.createByErrorMessage("参数错误，token需要传递");

        }

        ServerResponse validResponse = this.checkValid(username, Const.USERNAME);
        if (validResponse.isSuccess()) {
            return ServerResponse.createByErrorMessage("用户不存在");

        }

        String token = TokenCache.getKey(TokenCache.TOKEN_PREFIX + username);
        if (org.apache.commons.lang3.StringUtils.isBlank(token)) {
            return ServerResponse.createByErrorMessage("token无效或者过期");


        }

        if (org.apache.commons.lang3.StringUtils.equals(forgetToken, token)) {
            String md5Password = MD5Util.MD5EncodeUtf8(passwordNew);
            int rowCount = userMapper.updatePasswordByUsername(username, md5Password);


            if (rowCount > 0) {
                return ServerResponse.createByErrorMessage("修改密码成功");

            }
            } else {
                return ServerResponse.createByErrorMessage("token错误，请重新获取重置密码的token");

            }
            return ServerResponse.createByErrorMessage("修改密码失败");


        }












}
