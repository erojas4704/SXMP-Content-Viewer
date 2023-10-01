package com.eddiejrojas.sxmproject.security;

import com.eddiejrojas.sxmproject.model.User;
import com.eddiejrojas.sxmproject.repository.UserRepository;

import lombok.AllArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@AllArgsConstructor
public class UserArgumentResolver implements HandlerMethodArgumentResolver {
    private JWTUtils jwtUtils;
    private UserRepository userRepository;

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String token = webRequest.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer "))
            return null;

        User user = userRepository.findByUsername(jwtUtils.getUsernameFromToken(token.substring(7)))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return user;
    }

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType().equals(User.class);
    }
}
