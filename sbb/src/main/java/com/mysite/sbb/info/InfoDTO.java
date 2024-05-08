package com.mysite.sbb.info;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InfoDTO {
    private String username;
    private String address;
    private String nickname;
    private String phoneNumber;
    private String photo;
}
