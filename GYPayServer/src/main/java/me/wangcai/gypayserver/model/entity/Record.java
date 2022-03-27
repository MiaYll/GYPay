package me.wangcai.gypayserver.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import me.wangcai.gypayserver.enums.PayType;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@TableName("PayRecord")
public class Record {
    @TableId
    private int id;
    private String orderId;
    private LocalDateTime time;
    private PayType type;
}
