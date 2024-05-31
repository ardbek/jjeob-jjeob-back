package com.fmap.config.auth;

import com.fmap.user.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public class PrincipalDetails implements UserDetails, OAuth2User {

    private Optional<User> user;
    private Map<String, Object> attributes;
    
    // 일반로그인
    public PrincipalDetails(Optional<User> user) {
        this.user = user;
    }

    // OAuth2 로그인
    public PrincipalDetails(Optional<User> user, Map<String, Object> attributes) {
        this.user = user;
        this.attributes = attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }


    @Override
    public String getName() {
        return null;
    }


    @Override
    public String getPassword() {
        return user.get().getPassword();
    }

    @Override
    public String getUsername() {
        return user.get().getNickname();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    // Oauth2User ========================
    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

}
