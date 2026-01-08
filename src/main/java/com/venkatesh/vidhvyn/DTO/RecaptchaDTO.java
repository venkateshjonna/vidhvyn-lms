package com.venkatesh.vidhvyn.DTO;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecaptchaDTO {
    private boolean success;
    private String challenge_ts;
    private String hostname;
}
