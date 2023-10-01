package com.eddiejrojas.sxmproject;

import com.eddiejrojas.sxmproject.repository.ContentRepository;
import com.eddiejrojas.sxmproject.repository.UserRepository;
import com.eddiejrojas.sxmproject.security.JWTUtils;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetailsService;

public class ControllerTest {
    @MockBean protected JWTUtils jwtUtils;
    @MockBean protected UserRepository userRepository;
    @MockBean protected UserDetailsService userDetailsService;
    @MockBean protected ContentRepository contentRepository;
}
