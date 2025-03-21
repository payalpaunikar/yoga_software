package com.yoga.dto.response;

import com.yoga.component.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginResponse {
    private Long userId;
    private String email;
    private Role role;
    private String token;

    private Long branchId;
}
