package me.wangcai.gypaybukkit.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.wangcai.gypaybukkit.enums.PayType;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private int id;
    private String orderId;
    private String itemName;
    private String description;
    private String createTime;
    private double price;
    private String username;
    private PayType type;
    private String accountName;
}
