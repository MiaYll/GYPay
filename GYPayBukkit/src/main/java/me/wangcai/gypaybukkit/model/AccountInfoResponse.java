package me.wangcai.gypaybukkit.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AccountInfoResponse {
    private String start;
    private String end;
    private double total;
    private int amount;

}
