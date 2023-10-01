package com.eddiejrojas.sxmproject.dto;

import lombok.*;
import java.util.Date;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthorizationDTO {
    private String token;
    private String username;
    private Date expires;
}
