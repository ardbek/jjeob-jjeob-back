package com.fmap.config.oauth;

import com.fmap.config.auth.PrincipalDetails;
import com.fmap.config.auth.provider.*;
import com.fmap.user.entity.User;
import com.fmap.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    public PrincipalOauth2UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.debug("PrincipalOauth2UserService :: getClientRegistration : {}", userRequest.getClientRegistration());
        log.debug("PrincipalOauth2UserService :: getAccessToken : {}", userRequest.getAccessToken().getTokenValue());
        log.debug("PrincipalOauth2UserService :: userInfo(getAttributes) : {}", oAuth2User.getAttributes());

        OAuth2UserInfo oAuth2UserInfo = null;

        if ("google".equals(userRequest.getClientRegistration().getRegistrationId())) {
            log.debug("google 로그인 요청");
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        } else if ("facebook".equals(userRequest.getClientRegistration().getRegistrationId())) {
            log.debug("facebook 로그인 요청");
            oAuth2UserInfo = new FaceBookUserInfo(oAuth2User.getAttributes());
        } else if ("naver".equals(userRequest.getClientRegistration().getRegistrationId())) {
            log.debug("naver 로그인 요청");
            oAuth2UserInfo = new NaverUserInfo((Map<String, Object>) oAuth2User.getAttributes().get("response"));
        } else if ("kakao".equals(userRequest.getClientRegistration().getRegistrationId())) {
            log.debug("kakao 로그인 요청");
            oAuth2UserInfo = new KakaoUserInfo((Map<String, Object>) oAuth2User.getAttributes().get("id"));
        } else {
            log.info("미지원 소셜로그인 플랫폼");
        }

        // 회원가입 강제 진행
        String provider = oAuth2UserInfo.getProvider(); // 제공자
        String providerId = oAuth2UserInfo.getProvideId(); // key
        String username = provider + "_" + providerId; // 아이디
        String password = "tempPassword";
        String email = oAuth2UserInfo.getEmail();

        User user = userRepository.findByUserId(username);
        if (user == null) {
            log.debug("PrincipalOauth2UserService :: 미가입 사용자");
            user = User.builder()
                    .provider(provider)
                    .providerId(providerId)
                    .name(username)
                    .password(password)
                    .email(email)
                    .build();
            userRepository.save(user);
        }

        return new PrincipalDetails(user, oAuth2User.getAttributes());

    }
}
