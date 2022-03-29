package me.wangcai.gypaybukkit.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseInfo<T> {
    private long code;
    private String message;
    private T obj;
    public static ResponseInfo success(String message)
    {
        return new ResponseInfo(200,message,null);

    }
    public static ResponseInfo success(String message, Object obj){
        return new ResponseInfo(200,message,obj);
    }
    public static ResponseInfo error(String message) {
        return new ResponseInfo(500, message, null);
    }
    /**
     * 失败返回结果
     * @param message
     * @param obj
     * @return
     */
    public static ResponseInfo error(String message, Object obj) {
        return new ResponseInfo(401, message, obj);
    }
}

