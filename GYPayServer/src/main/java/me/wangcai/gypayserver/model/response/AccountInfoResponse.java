package me.wangcai.gypayserver.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class AccountInfoResponse {
    private LocalDateTime start;
    private LocalDateTime end;
    private double total;
    private int amount;

}
