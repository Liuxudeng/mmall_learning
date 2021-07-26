package com.mmall.common;

import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.Serializable;



//把数据序列化
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ServerResponse<T> implements Serializable {
    //声明变量，注意有一个状态变量
    private int status;
    private String msg;
    private T data;

    private  ServerResponse(int status){
        this.status = status;
    }
//当第二个参数为非String的时候就调用下面这个构造方法
    private ServerResponse(int status,T data){
        this.status = status;
        this.data = data;
    }

    private ServerResponse(int status,String msg,T data){
        this.status = status;
        this.msg = msg;
        this.data= data;
    }


//当第二个参数为
    private ServerResponse(int status,String msg){
        this.status = status;
        this.msg = msg;
    }



    //使该字段不出现在json中
    @JsonIgnore
    public boolean isSuccess(){
        return this.status == ResponseCode.Success.getCode();
    }


    //继续开放三个方法
    public int getStatus(){
        return status;
    }

    public T getData(){
        return data;
    }

    public String getMsg(){
        return  msg;
    }



//成功的返回信息

    public static<T>ServerResponse<T> createBySuccess(){
        return new ServerResponse<T>(ResponseCode.Success.getCode());
    }


    public static <T> ServerResponse<T> createBySuccessMessage(String msg){
        return new ServerResponse<T>(ResponseCode.Success.getCode(),msg);
    }


    public static <T> ServerResponse<T> createBySuccess(T data){
        return new ServerResponse<T>(ResponseCode.Success.getCode(),data);

    }


    public static<T> ServerResponse<T> createBySuccess(String msg, T data){
        return new ServerResponse<T>(ResponseCode.Success.getCode(),msg,data);
    }






//失败的返回信息
    public static <T> ServerResponse<T> createByError(){
        return new ServerResponse<T>(ResponseCode.Error.getCode(),ResponseCode.Error.getDesc());
    }

    public static <T> ServerResponse<T> createByErrorMessage(String errorMeaasge){
        return new ServerResponse<T>(ResponseCode.Error.getCode(),errorMeaasge);
    }


    public static <T> ServerResponse<T> createByErrorCodeMessage(int errorCode,String errorMessage){
        return new ServerResponse<T>(errorCode,errorMessage);
    }




}
