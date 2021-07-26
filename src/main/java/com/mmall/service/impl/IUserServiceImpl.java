package com.mmall.service.impl;
import com.mmall.util.MD5Util;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.dao.UserMapper;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;

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
}
