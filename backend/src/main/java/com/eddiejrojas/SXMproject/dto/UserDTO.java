package com.eddiejrojas.sxmproject.dto;

import lombok.Builder;
import lombok.Data;

/**
 * Provides a layer of user data that we can transmit, without sending sensitive user data.
 */
@Data
@Builder
public class UserDTO {
    private String username;
    private String avatarURL;
}
