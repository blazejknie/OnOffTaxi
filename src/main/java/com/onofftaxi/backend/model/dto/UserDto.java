package com.onofftaxi.backend.model.dto;

import com.onofftaxi.backend.model.UserType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;

@Getter
@Setter
public class UserDto {
    @Id
    private Long id;
    private String login;
    private String password;
    private Boolean logged;
    private UserType type = UserType.GUEST;

}
