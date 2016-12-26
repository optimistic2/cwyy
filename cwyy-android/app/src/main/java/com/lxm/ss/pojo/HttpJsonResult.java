package com.lxm.ss.pojo;

/**
 * Created by lxm on 2016/12/26.
 */

public class HttpJsonResult {

    private String code ;
    private String msg ;
    private String data ; //返回数据


    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "HttpJsonResult{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
