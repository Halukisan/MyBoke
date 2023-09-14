package com.Myboke.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdUserDto {
    private String userName;
    private String email;
    private String nickName;
    private String password;
    private String phonenumber;
    private String sex;
    private String status;
    private List<String> roleIds;

}
