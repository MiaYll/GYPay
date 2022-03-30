package me.wangcai.gypaybukkit.model;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ResponseInfo<T> implements Serializable {
    private long code;
    private String message;
    private T obj;

}

