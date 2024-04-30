package com.fmap.config.auth.provider;

import java.util.Map;

public class FaceBookUserInfo implements OAuth2UserInfo{

    private Map<String, Object> attribute;

    public FaceBookUserInfo(Map<String, Object> attribute) {
        this.attribute = attribute;
    }

    @Override
    public String getProvideId() {
        return (String) attribute.get("id");
    }

    @Override
    public String getProvider() {
        return "facebook";
    }

    @Override
    public String getEmail() {
        return (String) attribute.get("email");
    }

    @Override
    public String getName() {
        return (String) attribute.get("name");
    }
}
