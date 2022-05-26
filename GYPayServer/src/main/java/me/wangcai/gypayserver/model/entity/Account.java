package me.wangcai.gypayserver.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("account")
public class Account {
    @TableId
    private int id;
    private String name;
    private String password;
    private boolean isAdmin;
    private int rate;
}
