package me.wangcai.gypaybukkit.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.wangcai.gypaybukkit.enums.PayType;

@Data
@AllArgsConstructor
public class Record {
    private int id;
    private String orderId;
    private String time;
    private PayType type;
    private boolean isShip;
}
