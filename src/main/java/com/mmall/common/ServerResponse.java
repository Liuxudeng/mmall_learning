package com.mmall.common;

import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class ServerResponse<T> implements Serializable {
    //声明变量，注意有一个状态变量
    private int status;
    private String msg;
    private T data;






}
