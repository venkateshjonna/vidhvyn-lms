package com.venkatesh.vidhvyn.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Data
@Getter @Setter
@RequiredArgsConstructor
public class LoginRequestDTO {
    @NotBlank @Email
    private String email;
    @NotBlank
    private String password;
}
