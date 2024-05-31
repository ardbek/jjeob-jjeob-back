package com.fmap.config.auth;

import com.fmap.user.entity.User;
import com.fmap.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * PrincipalDetails 객체를 Authentication 객체로 변환하는 Service
 */
@Service
@AllArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUserId(userId);
        if (!user.isPresent()) {
            return new PrincipalDetails(user);
        }
        return null;
    }

}
