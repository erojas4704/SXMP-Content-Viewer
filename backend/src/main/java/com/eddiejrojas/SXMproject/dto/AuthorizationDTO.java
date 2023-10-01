package com.eddiejrojas.sxmproject.dto;

import java.util.Date;
import lombok.*;

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
