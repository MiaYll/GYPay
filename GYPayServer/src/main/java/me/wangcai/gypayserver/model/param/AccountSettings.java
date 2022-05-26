package me.wangcai.gypayserver.model.param;

import lombok.Data;

@Data
public class AccountSettings {
    private String name;
    private String password;
    private int rate;
}
