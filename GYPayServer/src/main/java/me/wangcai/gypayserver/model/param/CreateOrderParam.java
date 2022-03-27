package me.wangcai.gypayserver.model.param;

import lombok.Data;
import me.wangcai.gypayserver.enums.PayType;

@Data
public class CreateOrderParam {
    private String username;
    private double price;
    private String name;
    private String description;
    private PayType type;
}
