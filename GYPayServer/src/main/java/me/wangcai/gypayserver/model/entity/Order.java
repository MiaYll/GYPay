package me.wangcai.gypayserver.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.wangcai.gypayserver.enums.PayType;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("PayOrder")
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @TableId(value = "id",type = IdType.AUTO)
    private int id;
    private String orderId;
    private String itemName;
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    private double price;
    private String username;
    private PayType type;
}
