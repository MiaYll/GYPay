package me.wangcai.gypaybukkit.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.wangcai.gypaybukkit.enums.PayType;

@Data
@AllArgsConstructor
public class CreateOrderParam {
    private String username;
    private double price;
    private String name;
    private String description;
    private PayType type;
}
