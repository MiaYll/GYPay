package me.wangcai.gypayserver.model.param;

import lombok.Data;

@Data
public class AccountVerifyParam {
    private String username;
    private String password;
}
