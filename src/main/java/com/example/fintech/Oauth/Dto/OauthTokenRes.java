package com.example.fintech.Oauth.Dto;

import com.example.fintech.Oauth.Entity.OauthToken;
import lombok.Data;

@Data
public class OauthTokenRes {

    String access_token;
    String token_type;
    String expires_in;
    String refresh_token;
    String scope;
    Long user_seq_no;

    public OauthToken toEntity() {
        return OauthToken.builder()
                .accessToken(access_token)
                .tokenType(token_type)
                .expiresIn(expires_in)
                .refreshToken(refresh_token)
                .scope(scope)
                .userSeqNo(user_seq_no)
                .build();
    }

}
