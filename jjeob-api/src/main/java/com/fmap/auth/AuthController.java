package com.fmap.auth;

import com.fmap.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/test/oauth/login")
    public @ResponseBody String testOauthLogin(Authentication authentication,
                                               @AuthenticationPrincipal OAuth2User oauth) { // DI(의존성 주입)
        log.info("/test/oauth/login =========================");
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        log.info("oAuth2User = {}", oAuth2User.getAttributes());

        log.info("oauth = {}", oauth.getAttributes());

        return "OAuth 세션 정보 확인하기";
    }

    /**
     * 인덱스 페이지
     *
     * @return
     */
    @GetMapping({"", "/"})
    public String index() {
        //머스테치 기본 폴더 src/main/resources/
        // viewResolver : templates(prefix), .mustache (suffix) 생략가능
        return "index"; // src/main/resources/templates/index.mustache
    }


    /**
     * 관리자
     *
     * @return
     */
    @GetMapping("/admin")
    public @ResponseBody String admin() {
        return "admin";
    }

    /**
     * 매니저
     *
     * @return
     */
    @GetMapping("/manager")
    public @ResponseBody String manager() {
        return "manager";
    }

    /**
     * 로그인 (spring security가 기본 설정된 페이지로 이동키기 때문에 SecurityConfig에서 설정)
     *
     * @return
     */
    @GetMapping("/loginForm")
    public String loginForm() {
        return "loginForm";
    }

    /**
     * 회원가입 form
     *
     * @return
     */
    @GetMapping("/joinForm")
    public String joinForm() {
        return "joinForm";
    }

}
