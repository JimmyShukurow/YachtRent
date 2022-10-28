package com.example.yachtRent.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserRoleRequest {
    private Long userId;
    private Long roleId;
}
