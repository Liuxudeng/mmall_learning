package com.mmall.common;

import com.sun.net.httpserver.Authenticator;
import com.sun.org.apache.regexp.internal.RE;
import lombok.Data;


public enum ResponseCode {
    Success(0,"SUCCESS"),

Error(1,"ERROR"),

   NEED_LOGIN(10,"NEED_LOGIN"),
   ILLEAGL_ARGUMENT(2,"ILLEGAL_ARGUMENT");


   private  final int code;

   private final String desc;



   ResponseCode(int code,String desc){
       this.code = code;
       this.desc = desc;
   }


   public int getCode(){
       return code;
   }

   public String getDesc(){
       return desc;
   }



}
